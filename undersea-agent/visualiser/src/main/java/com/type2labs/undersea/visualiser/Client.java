package com.type2labs.undersea.visualiser;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        new Client();
    }

    public Client() throws IOException, InterruptedException {
        SocketChannel channel = SocketChannel.open();

        // we open this channel in non blocking mode
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress("localhost", 5050));

        while (!channel.finishConnect()) {
            // System.out.println("still connecting");
        }
        int i = 0;

        while (true) {
            ByteBuffer bufferA = ByteBuffer.allocate(20);
            String message = "";
            while (channel.read(bufferA) > 0) {
                // Flip the buffer to start reading
                bufferA.flip();
                message += Charset.defaultCharset().decode(bufferA);
            }

            if (message.length() > 0) {
                System.out.println(message);
                // Write some data into the channel
                CharBuffer buffer = CharBuffer.wrap("Hello Server: "+i++);
                while (buffer.hasRemaining()) {
                    channel.write(Charset.defaultCharset().encode(buffer));
                }
                message = "";
            }

            Thread.sleep(500);
        }


    }

}
