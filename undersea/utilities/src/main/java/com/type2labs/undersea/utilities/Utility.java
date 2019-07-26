package com.type2labs.undersea.utilities;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

public class Utility {

    private Utility() {

    }

    public static void copyFile(File source, File dest) {
        try {
            Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to copy " + source.getName() + " to " + dest.getPath(), e);
        }
    }

    /**
     * Creates the given file
     *
     * @param source file to create
     * @throws RuntimeException if unable to create the given file
     */
    public static void createFile(File source) {
        try {
            if (!source.createNewFile()) {
                throw new IOException();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create file: " + source.getAbsolutePath(), e);
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
            throw new RuntimeException("Unable to write " + inputFileName, e);
        }
    }

    public static void createFolder(File folder) {
        if (!folder.mkdirs()) {
            throw new RuntimeException("Failed to create folder: " + folder.getAbsolutePath());
        }
    }

    public static void exportToFile(String fileName, String output, boolean append) {
        try {
            FileWriter writer = new FileWriter(fileName, append);
            writer.append(output);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Unable to write " + fileName, e);
        }
    }

    public static void fileCreate(String fileName, boolean isDirectory) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            if (isDirectory)
                Files.createDirectory(Paths.get(fileName));
            else
                Files.createFile(Paths.get(fileName));
        }
    }

    public static boolean fileExists(String fileName, boolean isFolder, boolean create) throws FileNotFoundException {
        File f = new File(fileName);
        if (!f.exists()) {
            if (create) {
                Utility.createFolder(new File(fileName));
                return true;
            }

            throw new FileNotFoundException((isFolder ? "Folder '" : "File '") + fileName + "' does not exist!. " +
                    "Please fix this error.\n");
        }

        return true;
    }


    public static Properties getPropertiesByName(String name) {
        try {
            Properties moosProperties = new Properties();
            moosProperties.load(new FileInputStream(name));
            return moosProperties;
        } catch (IOException e) {
            throw new RuntimeException("Unable to load or find " + name, e);
        }
    }

    public static String getProperty(Properties properties, String key) {
        String result = properties.getProperty(key);
        if (result == null) {
            throw new IllegalArgumentException(key + " name not found");
        }
        return result;
    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static String readFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists())
            throw new FileNotFoundException("File " + fileName + " does not exist!. Please fix this error.\n");

        StringBuilder model = new StringBuilder(100);
        BufferedReader bfr = null;

        try {
            bfr = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = bfr.readLine()) != null) {
                model.append(line + "\n");
            }
            return model.toString();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

}
