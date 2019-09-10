package com.type2labs.undersea.common.logger;

import com.type2labs.undersea.common.cluster.PeerId;

import java.io.Serializable;

public class VisualiserMessage implements Serializable {

    private static final long serialVersionUID = 9070444023024621142L;
    private final String peerId;
    private final Object message;

    public VisualiserMessage(PeerId peerId, Object message) {
        this.peerId = peerId.toString();
        this.message = message;
    }

    public Object getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "LogMessage{" +
                "peerId='" + peerId + '\'' +
                ", message=" + message +
                '}';
    }

    public String getPeerId() {
        return peerId;
    }
}
