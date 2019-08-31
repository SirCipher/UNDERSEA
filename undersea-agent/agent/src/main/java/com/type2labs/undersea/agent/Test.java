package com.type2labs.undersea.agent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Thomas Klapwijk on 2019-08-24.
 */
public class Test {

    public static void main(String[] args) throws InterruptedException, IOException {
        for (int i = 0; i < 10; i++) {
            try (Socket socket = new Socket("localhost", 11111)) {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out.println("keks");
                out.flush();
                System.out.println(in.readLine());

                Thread.sleep(100);
            }
        }
    }

}
