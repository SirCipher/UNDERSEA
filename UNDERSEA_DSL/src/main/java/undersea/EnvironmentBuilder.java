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
    private static File missionDir;
    private static File buildDir;
    private static SimulationProperties simulationProperties = SimulationProperties.getInstance();

    static void build() {
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

    private static void cleanup() {
        for (File includeFile : Objects.requireNonNull(missionIncludeDir.listFiles())) {
            for (File missionFile :
                    Objects.requireNonNull(missionDir.listFiles(f -> !f.getName().equals(".DS_Store")))) {
                if (!includeFile.isDirectory() && includeFile.getName().equals(missionFile.getName())) {
                    if (!missionFile.delete()) {
                        throw new RuntimeException("Cleaning up mission directory. Unable to delete file: " + missionFile.getName());
                    }
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
                    buildDir.getCanonicalPath() + File.separator + fileName};
            System.out.println("Running nsplug on " + fileName);

            ProcessBuilder proc = new ProcessBuilder(args);
            proc.directory(missionDir);
            Process process = proc.start();

            // Overwrite files
            OutputStream os = process.getOutputStream();
            PrintWriter writer = new PrintWriter(os);
            writer.write("y\n");
            writer.flush();

            String output = IOUtils.toString(process.getInputStream(), "UTF-8");
            System.out.println(output);
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
