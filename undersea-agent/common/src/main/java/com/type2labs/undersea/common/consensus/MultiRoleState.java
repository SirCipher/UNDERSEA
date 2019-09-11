package com.type2labs.undersea.common.consensus;

import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.common.cluster.PeerId;

import java.util.Map;

public interface MultiRoleState {

    void setLeader(Client client);

    void setStatus(MultiRoleStatus status);

    MultiRoleStatus status();

    boolean isLeader();

    void handleFailure(Client client);

    void setGeneratedMission(Client client, String generatedMissionJson);

    Map<PeerId, Client> remotePeers();

}
