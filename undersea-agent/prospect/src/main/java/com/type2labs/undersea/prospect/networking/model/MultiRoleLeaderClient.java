package com.type2labs.undersea.prospect.networking.model;

import com.google.common.util.concurrent.FutureCallback;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.prospect.RaftProtos;

public interface MultiRoleLeaderClient extends Client {

    void notify(RaftProtos.NotificationRequest request, FutureCallback<RaftProtos.Empty> callback);

}
