package com.type2labs.undersea.common.visualiser;

import com.type2labs.undersea.common.Agent;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class VisualiserClientImpl implements VisualiserClient {

    private Agent parent;
    private final InetSocketAddress visualiserAddress = new InetSocketAddress("localhost", 5050);
    private final SocketChannel channel;

    public VisualiserClientImpl() {
        try {
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress("localhost", 5050));

            while (!channel.finishConnect()) {

            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to connect to visualiser. Is it running?");
        }
    }

    public void setParent(Agent parent) {
        this.parent = parent;
    }

    @Override
    public void write(VisualiserData data) {
        try {
            SocketChannel client = SocketChannel.open(visualiserAddress);
            ByteBuffer buffer = ByteBuffer.allocate(256);

            client.write(buffer);
            buffer.clear();
            client.read(buffer);
            String response = new String(buffer.array()).trim();

            System.out.println("response=" + response);

            buffer.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openConnection() throws IOException {
        ByteBuffer bufferA = ByteBuffer.allocate(20);
        StringBuilder message = new StringBuilder();

        ByteBuffer buffer = ByteBuffer.wrap("Hello, server".getBytes());
        channel.write(buffer);
        buffer.clear();
    }

    @Override
    public void closeConnection() throws IOException {
        channel.close();
    }


}
