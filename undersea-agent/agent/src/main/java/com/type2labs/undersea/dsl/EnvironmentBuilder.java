/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.type2labs.undersea.dsl;

import com.type2labs.undersea.common.agent.AgentMetaData;
import com.type2labs.undersea.dsl.uuv.model.DslAgentProxy;
import com.type2labs.undersea.utilities.Utility;
import com.type2labs.undersea.utilities.exception.UnderseaException;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Builds a representation of the environment from the mission.config file. This is then used to write the required
 * files for each of the MOOS agents. Running {@code nsplug} to populate them
 */
class EnvironmentBuilder {

    private static final Logger logger = LogManager.getLogger(EnvironmentBuilder.class);

    private static EnvironmentProperties environmentProperties;
    private static File missionIncludeDir;
    private static File buildDir;

    public static void setEnvironmentProperties(EnvironmentProperties environmentProperties) {
        EnvironmentBuilder.environmentProperties = environmentProperties;
    }

    /**
     * Build and write the respective {@code .moos} and {@code .bhv} files for each of the
     * {@link com.type2labs.undersea.common.agent.Agent}s
     */
    static void build() {
        DslAgentProxy shoreside = environmentProperties.getShoreside();

        logger.info("Writing mission files to: " + buildDir.getPath());

        int vPort =
                Integer.parseInt(environmentProperties.getEnvironmentValue(EnvironmentProperties.EnvironmentValue.PORT_START));
        int shoreListen = vPort;

        List<String> nsPlugArgs = new ArrayList<>();

        nsPlugArgs.add("WARP=" +
                environmentProperties.getEnvironmentValue(EnvironmentProperties.EnvironmentValue.TIME_WINDOW));
        nsPlugArgs.add("VHOST=" + environmentProperties.getEnvironmentValue(EnvironmentProperties.EnvironmentValue.HOST));
        nsPlugArgs.add("VNAME=shoreside");
        nsPlugArgs.add("SHARE_LISTEN=" + shoreListen);
        nsPlugArgs.add("VPORT=" + vPort++);
        nsPlugArgs.add("INBOUND_SUUV_PORT=" + vPort++);

        nsplug(shoreside.getMetaFileName(), shoreside.getMetaFileName(), nsPlugArgs);


        for (Map.Entry<String, DslAgentProxy> entry : environmentProperties.getAgents().entrySet()) {
            DslAgentProxy agent = entry.getValue();

            nsPlugArgs.clear();
            nsPlugArgs.add("WARP=" +
                    environmentProperties.getEnvironmentValue(EnvironmentProperties.EnvironmentValue.TIME_WINDOW));
            nsPlugArgs.add("VHOST=" +
                    environmentProperties.getEnvironmentValue(EnvironmentProperties.EnvironmentValue.HOST));
            nsPlugArgs.add("VNAME=" + agent.getName());
            nsPlugArgs.add("VPORT=" + (vPort += 10));
            nsPlugArgs.add("SHARE_LISTEN=" + (++vPort));
            nsPlugArgs.add("SHORE_LISTEN=" + shoreListen);

            int suuvPort = ++vPort;
            int inbound_uuvPort = ++vPort;

            nsPlugArgs.add("SUUV_PORT=" + suuvPort);
            nsPlugArgs.add("INBOUND_SUUV_PORT=" + inbound_uuvPort);

            agent.metadata().setProperty(AgentMetaData.PropertyKey.HARDWARE_PORT, suuvPort);
            agent.metadata().setProperty(AgentMetaData.PropertyKey.INBOUND_HARDWARE_PORT, inbound_uuvPort);

            logger.info("-----------------------------------------");
            nsplug(agent.getMetaFileName(), agent.getMetaFileName(), nsPlugArgs);

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
        List<String> ignoreList = new ArrayList<>(Arrays.asList("clean.sh"));

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
                String fileName = missionFile.getName();
                if (fileName.endsWith(".bhv") || fileName.startsWith("pAntler") || ignoreList.stream().anyMatch(s -> s.equals(missionFile.getName()))) {
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

    /**
     * Initialises the required directories. Emptying them of all but the configuration files required
     *
     * @param buildDirPath          to clean
     * @param missionIncludeDirPath to reference the directory against
     */
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

    /**
     * Runs {@code nsplug} using the arguments provided. This is the final write stage of the DSL parsing
     *
     * @param sourceName to run it on
     * @param destName   to output the file
     * @param nsplugArgs to provide when running {@code nsplug}. These should be the placeholders and values
     */
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
