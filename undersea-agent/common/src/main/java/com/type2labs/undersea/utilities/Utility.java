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

package com.type2labs.undersea.utilities;

import com.type2labs.undersea.utilities.exception.UnderseaException;
import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;

public class Utility {

    public static final String ARRAY_KEY = ";";
    private static final Logger logger = LogManager.getLogger(Utility.class);

    private Utility() {

    }

    /**
     * The Java process system does not kill child processes that have been launched and even though the
     * pMarineViewer is still open, the {@link Process#isAlive()} method returns false after the process has been
     * closed. Unfortunately, this results in a number of child processes remaining afterwards. So the only
     * alternative (at present) is to kill all processes matching {@code '.moos'}. This will forcibly kill all
     * processes matching the pattern. Depending on the {@code net.ipv4.tcp_fin_timeout} set, the port may linger
     * after the process has been killed before it can be reused again. Using
     * {@link java.net.ServerSocket#setReuseAddress(boolean)} (SO_REUSEADDR) will reduce this time on sockets.
     * <p>
     * See <a href="https://bugs.java.com/bugdatabase/view_bug.do?bug_id=4770092">Java bug database</a>
     * <p>
     * See
     * <a href="https://unix.stackexchange.com/questions/294616/unbind-port-of-crashed-program">For port release on Linux</a>"
     */
    public static void killMoos() {
        try {
            logger.info("Terminating processes matching .moos");
            Runtime.getRuntime().exec("pkill -f .moos -9");
            logger.info("Terminated processes matching .moos");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static double[][] stringTo2dDoubleArray(String s) {
        String[] values = s.split(Utility.ARRAY_KEY);
        double[][] result = new double[values.length][2];

        for (int i = 0; i < values.length; i++) {
            String[] dubs = values[i].trim().split(" ");
            try {
                result[i][0] = Double.parseDouble(dubs[0]);
                result[i][1] = Double.parseDouble(dubs[1]);
            } catch (NumberFormatException e) {
                logger.error("Failed to parse value: " + Arrays.toString(dubs) + ". Property file arrays should be in" +
                        " the format: environment.area=0 0; 10 10; ....");
                e.printStackTrace();
            }
        }

        return result;
    }

    public static double[][] propertyKeyTo2dDoubleArray(Properties properties, String key) {
        String prop = getProperty(properties, key);
        return stringTo2dDoubleArray(prop);
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

    public static void exportToFileWithPermissions(String fileName, String output, boolean append,
                                                   Collection<PosixFilePermission> permissions) {
        Utility.exportToFile(fileName, output, append);

        if (SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC) {
            try {
                Path path = new File(fileName).toPath();
                final Set<PosixFilePermission> posixFilePermissions = Files.getPosixFilePermissions(path);
                posixFilePermissions.addAll(permissions);
                Files.setPosixFilePermissions(path, posixFilePermissions);
            } catch (IOException e) {
                throw new UnderseaException("Failed to set executable permissions " + Arrays.toString(permissions.toArray()) +
                        " for file " + fileName);
            }
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
            Properties result = new Properties();
            result.load(new FileInputStream(name));
            return result;
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

