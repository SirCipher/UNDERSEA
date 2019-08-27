package com.type2labs.undersea.dsl;

import com.type2labs.undersea.dsl.uuv.model.DslAgentProxy;
import com.type2labs.undersea.utilities.exception.UnderseaException;
import com.type2labs.undersea.utilities.Utility;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

class EnvironmentBuilder {

    private static final Logger logger = LogManager.getLogger(EnvironmentBuilder.class);

    private static EnvironmentProperties environmentProperties;
    private static File missionIncludeDir;
    private static File buildDir;

    public static void setEnvironmentProperties(EnvironmentProperties environmentProperties) {
        EnvironmentBuilder.environmentProperties = environmentProperties;
    }

    static void build() {
        DslAgentProxy shoreside = environmentProperties.getShoreside();

        logger.info("Writing mission files to: " + buildDir.getPath());

        int vPort =
                Integer.parseInt(environmentProperties.getEnvironmentValue(EnvironmentProperties.EnvironmentValue.PORT_START));
        int shoreListen = vPort + 300;
        int shareListen = shoreListen + 1;

        List<String> nsPlugArgs = new ArrayList<>();

        nsPlugArgs.add("WARP=" +
                environmentProperties.getEnvironmentValue(EnvironmentProperties.EnvironmentValue.TIME_WINDOW));
        nsPlugArgs.add("VHOST=" + environmentProperties.getEnvironmentValue(EnvironmentProperties.EnvironmentValue.HOST));
        nsPlugArgs.add("VNAME=shoreside");
        nsPlugArgs.add("SHARE_LISTEN=" + shoreListen);
        nsPlugArgs.add("VPORT=" + vPort);

        nsplug(shoreside.getMetaFileName(), shoreside.getMetaFileName(), nsPlugArgs);

        vPort++;

        for (Map.Entry<String, DslAgentProxy> entry : environmentProperties.getAgents().entrySet()) {
            DslAgentProxy agent = entry.getValue();

            nsPlugArgs.clear();
            nsPlugArgs.add("WARP=" +
                    environmentProperties.getEnvironmentValue(EnvironmentProperties.EnvironmentValue.TIME_WINDOW));
            nsPlugArgs.add("VHOST=" +
                    environmentProperties.getEnvironmentValue(EnvironmentProperties.EnvironmentValue.HOST));
            nsPlugArgs.add("VNAME=" + agent.getName());
            nsPlugArgs.add("VPORT=" + vPort++);
            nsPlugArgs.add("SHARE_LISTEN=" + shareListen++);
            nsPlugArgs.add("SHORE_LISTEN=" + shoreListen);

            logger.info("-----------------------------------------");
            nsplug(agent.getMetaFileName(), agent.getMetaFileName(), nsPlugArgs);

            nsPlugArgs.add("POINTS=" + agent.getBehaviour());
            nsplug("behaviour.bhv", "meta_" + agent.getName() + ".bhv", nsPlugArgs);
        }

        cleanup();

        logger.info("Finished building environment");
        logger.info("-----------------------------------------");
    }

    /**
     * Cleans up the output mission directory so it only contains the files/folders required to run the mission.
     * Removing all mission include files required for the NSPLUG.
     */
    private static void cleanup() {
        List<String> ignoreList = new ArrayList<>(Arrays.asList("clean.sh", "pAntler-launch.sh", "undersea-launch.sh"
                , "agents.ports"));

        for (File includeFile : Objects.requireNonNull(missionIncludeDir.listFiles())) {
            for (File missionFile :
                    Objects.requireNonNull(buildDir.listFiles(f -> !f.getName().equals(".DS_Store")))) {
                if (!includeFile.isDirectory() && includeFile.getName().equals(missionFile.getName())) {
                    if (missionFile.delete()) {
                        continue;
                    } else {
                        throw new RuntimeException("Cleaning up mission directory. Unable to delete file: " + missionFile.getName());
                    }
                }

                // Whitelist all behaviour and ignore list files
                if (missionFile.getName().endsWith(".bhv") || ignoreList.stream().anyMatch(s -> s.equals(missionFile.getName()))) {
                    continue;
                }

                if (!missionFile.getName().startsWith("meta")) {
                    if (!missionFile.isDirectory() && !missionFile.delete()) {
                        throw new RuntimeException("Cleaning up mission directory. Unable to delete file: " + missionFile.getName());
                    }
                }
            }
        }
    }

    static void initDirectories(String buildDirPath, String missionIncludeDirPath) {
        buildDir = new File(buildDirPath);
        missionIncludeDir = new File(missionIncludeDirPath);

        if (buildDir.mkdirs()) {
            logger.info("Created build and resources directories: " + buildDir.getPath());
        }

        if (buildDir.listFiles() != null) {
            for (File file : Objects.requireNonNull(buildDir.listFiles())) {
                if (!file.isDirectory() && !file.delete()) {
                    throw new RuntimeException("Cleaning mission directory. Unable to delete file: " + file.getName());
                }
            }
        }

        if (!missionIncludeDir.exists()) {
            throw new RuntimeException("Missing mission-include directory or files. Expected: " + missionIncludeDir.getAbsolutePath());
        }

        if (missionIncludeDir.listFiles() == null) {
            throw new RuntimeException("Missing mission-include files");
        }

        for (File file : Objects.requireNonNull(missionIncludeDir.listFiles())) {
            Utility.copyFile(file, new File(buildDir + File.separator + file.getName()));
        }
    }

    private static void nsplug(String sourceName, String destName, List<String> nsplugArgs) {
        try {
            logger.info("Running nsplug on " + destName);

            // Existing elements will be right-shifted
            nsplugArgs.add(0, "-f");
            nsplugArgs.add(0, destName);
            nsplugArgs.add(0, sourceName);
            nsplugArgs.add(0, "nsplug");
            nsplugArgs.add("START_POS=x=0,y=-75");

            ProcessBuilder proc = new ProcessBuilder(nsplugArgs);
            proc.directory(buildDir);
            Process process = proc.start();

            // Overwrite files
            OutputStream os = process.getOutputStream();
            PrintWriter writer = new PrintWriter(os);
            writer.write("y");
            writer.flush();

            String output = IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);
            logger.info(output);
            process.destroy();
        } catch (IOException e) {
            throw new UnderseaException("Unable to run nsplug on: " + sourceName + ". For mission directory: " + buildDir.getAbsolutePath() + ". With arguments: " + nsplugArgs, e);
        }
    }


}
