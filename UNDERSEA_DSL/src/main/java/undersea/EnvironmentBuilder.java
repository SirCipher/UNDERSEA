package undersea;

import org.apache.commons.io.IOUtils;
import undersea.auxiliary.Utility;
import undersea.uuv.dsl.model.UUV;
import undersea.uuv.properties.SimulationProperties;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

class EnvironmentBuilder {

    private static File missionIncludeDir = new File(".." + File.separator + "mission-includes");
    private static File controllerDir;
    private static File missionDir;
    private static File configFile;
    private static File sensorsFile;
    private static File buildDir = new File(".." + File.separator + "build" + File.separator + "resources");
    private static SimulationProperties simulationProperties = SimulationProperties.getInstance();

    static void build() {
        File controllerProperties =
                new File(controllerDir.getPath() + File.separator + "resources" + File.separator + "config.properties");

        if (controllerProperties.exists()) {
            try {
                File dest = new File(buildDir.getCanonicalPath() + File.separator + "config.properties");
                Utility.copyFile(controllerProperties, dest);
            } catch (IOException e) {
                throw new RuntimeException("Failed to copy " + controllerProperties.getName() + " to " + buildDir.getPath() + File.separator + "config.properties", e);
            }
        } else {
            System.out.println("Controller config.properties missing. Skipping");
        }

        generateTargetFiles();
    }

    private static void cleanup() {
        for (File includeFile : Objects.requireNonNull(missionIncludeDir.listFiles())) {
            for (File missionFile : Objects.requireNonNull(missionDir.listFiles())) {
                if (!includeFile.isDirectory() && includeFile.getName().equals(missionFile.getName())) {
                    if (!missionFile.delete()) {
                        throw new RuntimeException("Cleaning up mission directory. Unable to delete file: " + missionFile.getName());
                    }
                }
            }
        }
    }

    private static void generateTargetFiles() {
        UUV shoreside = simulationProperties.getShoreside();

        System.out.println("Using build directory: " + buildDir.getAbsolutePath());
        System.out.println("Writing mission files to: " + missionDir.getPath());

        nsplug(shoreside.getMetaFileName());

        for (Map.Entry<String, UUV> entry : simulationProperties.getAgents().entrySet()) {
            UUV uuv = entry.getValue();
            nsplug(uuv.getMetaFileName());
            nsplug(uuv.getBehaviourFileName());
        }

        cleanup();
    }

    public static void initDirectories(String controllerDirPath, String missionDirPath, String configFilePath,
                                       String sensorsFilePath) {
        controllerDir = new File(controllerDirPath);
        missionDir = new File(missionDirPath);
        configFile = new File(configFilePath);
        sensorsFile = new File(sensorsFilePath);

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
            missionIncludeDir = new File("mission-includes");

            if (!missionIncludeDir.exists()) {
                throw new RuntimeException("Missing mission-include directory or files. Expected: " + missionIncludeDir.getAbsolutePath());
            }
        }

        if (missionIncludeDir.listFiles() == null) {
            throw new RuntimeException("Missing mission-include files");
        }

        for (File file : Objects.requireNonNull(missionIncludeDir.listFiles())) {
            Utility.copyFile(file, new File(missionDir + File.separator + file.getName()));
        }
    }

    private static void nsplug(String fileName) {
        try {
            Properties properties = Utility.getProperties();
            Object moosivpLocation = properties.getProperty("moosivp");

            if (moosivpLocation == null) {
                throw new IllegalArgumentException("MOOS-IVP bin location not specified");
            }

            String[] args = new String[]{moosivpLocation + File.separator + "nsplug", fileName,
                    missionDir.getCanonicalPath() + File.separator + fileName};
            System.out.println("Running nsplug on " + fileName);

            ProcessBuilder proc = new ProcessBuilder(args);
            proc.directory(missionDir);
            Process process = proc.start();

            // Overwrite files
            OutputStream os = process.getOutputStream();
            PrintWriter writer = new PrintWriter(os);
            writer.write("y\n");
            writer.flush();

            String output = IOUtils.toString(process.getInputStream());
            System.out.println(output);
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
