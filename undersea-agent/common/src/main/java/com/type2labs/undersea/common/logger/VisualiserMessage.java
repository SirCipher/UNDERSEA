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

package com.type2labs.undersea.common.logger;

import com.type2labs.undersea.common.cluster.PeerId;

import java.io.Serializable;

/**
 * Visualiser message data structure. The visualiser will match up the message by the peer ID
 */
public class VisualiserMessage implements Serializable {

    private static final long serialVersionUID = 9070444023024621142L;
    private final String peerId;
    private final Object message;
    private final boolean error;

    public VisualiserMessage(PeerId peerId, Object message, boolean error) {
        this.peerId = peerId.toString();
        this.message = message;
        this.error = error;
    }

    @Override
    public String toString() {
        return "VisualiserMessage{" +
                "peerId='" + peerId + '\'' +
                ", message=" + message +
                ", error=" + error +
                '}';
    }

    public boolean isError() {
        return error;
    }

    public Object getMessage() {
        return message;
    }

    public String getPeerId() {
        return peerId;
    }
}
