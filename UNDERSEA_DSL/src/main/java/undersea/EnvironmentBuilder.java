package undersea;

import org.apache.commons.io.IOUtils;
import undersea.auxiliary.Utility;
import undersea.uuv.dsl.model.UUV;
import undersea.uuv.properties.SimulationProperties;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;

class EnvironmentBuilder {

    private static File controllerDir;
    private static File missionDir;
    private static File configFile;
    private static File sensorsFile;
    private static File buildDir = new File("../build/resources");


    static void build(String controllerDirPath, String missionDirPath, String configFilePath, String sensorsFilePath) {
        controllerDir = new File(controllerDirPath);
        missionDir = new File(missionDirPath);
        configFile = new File(configFilePath);
        sensorsFile = new File(sensorsFilePath);

        if (buildDir.mkdirs()) {
            System.out.println("Created build and resources directories: " + buildDir.getPath());
        }

        File controllerProperties = new File(controllerDir.getPath() + "/resources/config.properties");

        if (controllerProperties.exists()) {
            try {
                File dest = new File(buildDir.getCanonicalPath() + "/config.properties");
                Utility.copyFile(controllerProperties, dest);
            } catch (IOException e) {
                throw new RuntimeException("Failed to copy " + controllerProperties.getName() + " to " + buildDir.getPath() + "/config.properties", e);
            }
        } else {
            System.out.println("Controller config.properties missing. Skipping");
        }

        generateTargetFiles();
    }

    private static void nsplug(String fileName) {
        try {
            String target = fileName.replaceFirst("meta", "targ");
            String[] args = new String[]{"/home/tom/Desktop/PACS/moos-ivp/bin/nsplug", fileName, target};

            System.out.println("Running nsplug on " + fileName);

            ProcessBuilder proc = new ProcessBuilder(args);
            proc.directory(new File("missions"));
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


    private static void generateTargetFiles() {
        SimulationProperties simulationProperties = SimulationProperties.getInstance();

        UUV shoreside = simulationProperties.getShoreside();
        nsplug(shoreside.getMetaFileName());

        for (Map.Entry<String, UUV> entry : simulationProperties.getAgents().entrySet()) {
            UUV uuv = entry.getValue();
            nsplug(uuv.getMetaFileName());
            nsplug(uuv.getBehaviourFileName());
        }
    }

}
