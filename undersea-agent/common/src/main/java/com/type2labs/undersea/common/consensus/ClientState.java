package com.type2labs.undersea.common.consensus;

import com.type2labs.undersea.common.cluster.Client;

public class ClientState {

    private final Client client;
    private String jsonMissionCoordinates;

    public ClientState(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public String getJsonMissionCoordinates() {
        return jsonMissionCoordinates;
    }

    public void setJsonMissionCoordinates(String jsonMissionCoordinates) {
        this.jsonMissionCoordinates = jsonMissionCoordinates;
    }
}
