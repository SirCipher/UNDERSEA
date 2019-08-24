package com.type2labs.undersea.agent;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Thomas Klapwijk on 2019-08-24.
 */
public class Test {

    public static void main(String[] args) throws InterruptedException, IOException {
        Runtime rt = Runtime.getRuntime();

        ProcessBuilder pb = new ProcessBuilder(new String[]{"/Volumes/MiniMudkipz/dev/PACS/moos-ivp/bin/pMarineViewer","meta_shoreside.moos"});
        pb.directory(new File("missions/test_01/"));
        pb.redirectErrorStream(true);

        Process process = pb.start();

        String line;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(process.getInputStream()) );
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }

    }

}
