package com.type2labs.undersea.dsl;

import com.type2labs.undersea.dsl.uuv.model.UUV;
import com.type2labs.undersea.dsl.uuv.properties.EnvironmentProperties;
import com.type2labs.undersea.utility.Utility;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

class EnvironmentBuilder {

    private static final Logger logger = LogManager.getLogger(EnvironmentBuilder.class);

    private static final EnvironmentProperties ENVIRONMENT_PROPERTIES = EnvironmentProperties.getInstance();
    private static File missionIncludeDir;
    private static File missionDir;
    private static File buildDir;

    static void build() {
        UUV shoreside = ENVIRONMENT_PROPERTIES.getShoreside();

        logger.info("Using build directory: " + buildDir.getAbsolutePath());
        logger.info("Writing mission files to: " + missionDir.getPath());

        int vPort =
                Integer.parseInt(ENVIRONMENT_PROPERTIES.getEnvironmentValue(EnvironmentProperties.EnvironmentValue.PORT_START));
        int shoreListen = vPort + 300;
        int shareListen = shoreListen + 1;

        List<String> nsPlugArgs = new ArrayList<>();

        nsPlugArgs.add("WARP=" +
                ENVIRONMENT_PROPERTIES.getEnvironmentValue(EnvironmentProperties.EnvironmentValue.TIME_WINDOW));
        nsPlugArgs.add("VHOST=" + ENVIRONMENT_PROPERTIES.getEnvironmentValue(EnvironmentProperties.EnvironmentValue.HOST));
        nsPlugArgs.add("VNAME=shoreside");
        nsPlugArgs.add("SHARE_LISTEN=" + shoreListen);
        nsPlugArgs.add("VPORT=" + vPort);

        nsplug(shoreside.getMetaFileName(), shoreside.getMetaFileName(), nsPlugArgs);

        for (Map.Entry<String, UUV> entry : ENVIRONMENT_PROPERTIES.getAgents().entrySet()) {
            UUV uuv = entry.getValue();

            nsPlugArgs.clear();
            nsPlugArgs.add("WARP=" +
                    ENVIRONMENT_PROPERTIES.getEnvironmentValue(EnvironmentProperties.EnvironmentValue.TIME_WINDOW));
            nsPlugArgs.add("VHOST=" +
                    ENVIRONMENT_PROPERTIES.getEnvironmentValue(EnvironmentProperties.EnvironmentValue.HOST));
            nsPlugArgs.add("VNAME=" + uuv.getName());
            nsPlugArgs.add("VPORT=" + ++vPort);
            nsPlugArgs.add("SHARE_LISTEN=" + ++shareListen);
            nsPlugArgs.add("SHORE_LISTEN=" + shoreListen);

            logger.info("-----------------------------------------");
            nsplug(uuv.getMetaFileName(), uuv.getMetaFileName(), nsPlugArgs);
            nsplug("behaviour.bhv", "meta_" + uuv.getName() + ".bhv", nsPlugArgs);
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
        List<String> ignoreList = new ArrayList<>(Arrays.asList("clean.sh", "launch.sh"));

        for (File includeFile : Objects.requireNonNull(missionIncludeDir.listFiles())) {
            for (File missionFile :
                    Objects.requireNonNull(missionDir.listFiles(f -> !f.getName().equals(".DS_Store")))) {
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

    static void initDirectories(String missionDirPath, String buildDirPath, String missionIncludeDirPath) {
        missionDir = new File(missionDirPath);
        buildDir = new File(buildDirPath);
        missionIncludeDir = new File(missionIncludeDirPath);

        if (!missionDir.exists()) {
            Utility.createFolder(missionDir);
        }

        if (buildDir.mkdirs()) {
            logger.info("Created build and resources directories: " + buildDir.getPath());
        }

        if (missionDir.listFiles() != null) {
            for (File file : Objects.requireNonNull(missionDir.listFiles())) {
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
            Utility.copyFile(file, new File(missionDir + File.separator + file.getName()));
        }
    }

    private static void nsplug(String sourceName, String destName, List<String> nsplugArgs) {
        try {
            Properties properties = Utility.getMoosProperties();
            String moosivpLocation = properties.getProperty("moosivp");

            if (moosivpLocation == null) {
                throw new IllegalArgumentException("MOOS-IVP bin location not specified in resources/moos.properties");
            }

            moosivpLocation = moosivpLocation.endsWith("/") ? moosivpLocation + "nsplug" :
                    moosivpLocation + File.separator + "nsplug";

            logger.info("Running nsplug on " + destName);

            // Existing elements will be right-shifted
            nsplugArgs.add(0, "-f");
            nsplugArgs.add(0, destName);
            nsplugArgs.add(0, sourceName);
            nsplugArgs.add(0, moosivpLocation);
            nsplugArgs.add("START_POS=x=0,y=-75");

            ProcessBuilder proc = new ProcessBuilder(nsplugArgs);
            proc.directory(missionDir);
            Process process = proc.start();

            // Overwrite files
            OutputStream os = process.getOutputStream();
            PrintWriter writer = new PrintWriter(os);
            writer.write("y");
            writer.flush();

            String output = IOUtils.toString(process.getInputStream(), "UTF-8");
            logger.info(output);
            process.destroy();
        } catch (IOException e) {
            throw new DSLException("Unable to run nsplug on: " + sourceName + ". For mission directory: " + missionDir.getAbsolutePath() + ". With arguments: " + nsplugArgs, e);
        }
    }

}
