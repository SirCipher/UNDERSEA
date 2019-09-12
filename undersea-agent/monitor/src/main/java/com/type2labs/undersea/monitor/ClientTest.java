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

package com.type2labs.undersea.monitor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class ClientTest {

    private SocketChannel channel;
    private ObjectOutputStream oos;

    public ClientTest() {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ClientTest();
    }

    private void _write(Object data) throws IOException {
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
