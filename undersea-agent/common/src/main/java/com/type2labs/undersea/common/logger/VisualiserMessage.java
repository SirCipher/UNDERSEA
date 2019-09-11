package com.type2labs.undersea.common.logger;

import com.type2labs.undersea.common.cluster.PeerId;

import java.io.Serializable;

public class VisualiserMessage implements Serializable {

    private static final long serialVersionUID = 9070444023024621142L;
    private final String peerId;
    private final Object message;

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

    private final boolean error;

    public VisualiserMessage(PeerId peerId, Object message, boolean error) {
        this.peerId = peerId.toString();
        this.message = message;
        this.error = error;
    }

    public Object getMessage() {
        return message;
    }

    public String getPeerId() {
        return peerId;
    }
}
