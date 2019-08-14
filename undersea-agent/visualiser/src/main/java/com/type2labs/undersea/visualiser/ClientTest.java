package com.type2labs.undersea.visualiser;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class ClientTest {

    private SocketChannel channel;
    private ObjectOutputStream oos;

    public static void main(String[] args) {
        new ClientTest();
    }

    public ClientTest() {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void _write(Object data) throws IOException {
        System.out.println("Writing " + data);

        oos.writeObject(data);
        oos.flush();
        oos.reset();
    }

    private void init() throws IOException {
        InetSocketAddress visualiserAddress = new InetSocketAddress("localhost", 5050);
        channel = SocketChannel.open();

        channel.configureBlocking(true);
        channel.socket().setKeepAlive(true);
        channel.connect(visualiserAddress);

        oos = new ObjectOutputStream(channel.socket().getOutputStream());

        while (!channel.finishConnect()) {
        }

        _write("Hello server\n");

        for (int i = 0; i < 100; i++) {
            _write("wag: " + i + "\n");
        }

    }


}
