package undersea;

import org.apache.commons.io.IOUtils;
import undersea.auxiliary.Utility;
import undersea.uuv.dsl.model.UUV;
import undersea.uuv.properties.SimulationProperties;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

class EnvironmentBuilder {

    private static File missionIncludeDir = new File(".." + File.separator + "mission-includes");
    private static File missionDir;
    private static File buildDir;
    private static SimulationProperties simulationProperties = SimulationProperties.getInstance();

    static void build() {
        UUV shoreside = simulationProperties.getShoreside();

        System.out.println("Using build directory: " + buildDir.getAbsolutePath());
        System.out.println("Writing mission files to: " + missionDir.getPath() + "\n");

        System.out.println("-----------------------------------------");

        int vPort =
                Integer.parseInt(simulationProperties.getEnvironmentValue(SimulationProperties.EnvironmentValue.PORT_START));
        int shoreListen = vPort + 300;
        int shareListen = shoreListen + 1;

        List<String> nsPlugArgs = new ArrayList<>();

        nsPlugArgs.add("WARP=" +
                simulationProperties.getEnvironmentValue(SimulationProperties.EnvironmentValue.TIME_WINDOW));
        nsPlugArgs.add("VHOST=" + simulationProperties.getEnvironmentValue(SimulationProperties.EnvironmentValue.HOST));
        nsPlugArgs.add("VNAME=shoreside");
        nsPlugArgs.add("SHARE_LISTEN=" + String.valueOf(shoreListen));
        nsPlugArgs.add("VPORT=" + String.valueOf(vPort));

        nsplug(shoreside.getMetaFileName(), shoreside.getMetaFileName(), nsPlugArgs);

        for (Map.Entry<String, UUV> entry : simulationProperties.getAgents().entrySet()) {
            UUV uuv = entry.getValue();

            nsPlugArgs.clear();
            nsPlugArgs.add("WARP=" +
                    simulationProperties.getEnvironmentValue(SimulationProperties.EnvironmentValue.TIME_WINDOW));
            nsPlugArgs.add("VHOST=" +
                    simulationProperties.getEnvironmentValue(SimulationProperties.EnvironmentValue.HOST));
            nsPlugArgs.add("VNAME=" + uuv.getName());
            nsPlugArgs.add("VPORT=" + String.valueOf(++vPort));
            nsPlugArgs.add("SHARE_LISTEN=" + String.valueOf(++shareListen));
            nsPlugArgs.add("SHORE_LISTEN=" + String.valueOf(shoreListen));

            System.out.println("-----------------------------------------");
            nsplug(uuv.getMetaFileName(), uuv.getMetaFileName(), nsPlugArgs);
            nsplug("behaviour.bhv", "meta_" + uuv.getName() + ".bhv", nsPlugArgs);
        }

        cleanup();
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

    static void initDirectories(String missionDirPath, String buildDirPath) {
        missionDir = new File(missionDirPath);
        buildDir = new File(buildDirPath);

        if (!missionDir.exists()) {
            Utility.createFolder(missionDir);
        }

        if (buildDir.mkdirs()) {
            System.out.println("Created build and resources directories: " + buildDir.getPath());
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

            System.out.println("Running nsplug on " + destName);

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
            System.out.println(output);
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
