package undersea.auxiliary;

import undersea.ParserEngine;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

public class Utility {

    private static Properties properties = new Properties();

    private Utility() {

    }

    public static void setProperties() {
        try {
            properties.load(new FileInputStream(ParserEngine.propertiesFile));
        } catch (IOException e) {
            System.out.println("Could not find config.properties file");
        }
    }

    public static void copyFile(File source, File dest) {
        try {
            Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Copied " + source.getName() + " to " + dest.getCanonicalFile());
        } catch (IOException e) {
            throw new RuntimeException("Failed to copy " + source.getName() + " to " + dest.getPath(), e);
        }
    }

    public static void createFileAndExport(String inputFileName, String outputFileName, String outputStr) {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;

        try {
            File input = new File(inputFileName);
            File output = new File(outputFileName);

            inputChannel = new FileInputStream(input).getChannel();
            outputChannel = new FileOutputStream(output).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());

            inputChannel.close();
            outputChannel.close();

            exportToFile(outputFileName, outputStr, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportToFile(String fileName, String output, boolean append) {
        try {
            FileWriter writer = new FileWriter(fileName, append);
            writer.append(output);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean fileExists(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        if (!f.exists()) {
            throw new FileNotFoundException("File " + fileName + " does not exist!. Please fix this error.\n");
        }

        return true;
    }

    public static Properties getProperties() {
        return properties;
    }

    public static String getProperty(String key) {
        String result = properties.getProperty(key);
        if (result == null)
            throw new IllegalArgumentException(key.toUpperCase() + " name not found!");
        return result;
    }

}
