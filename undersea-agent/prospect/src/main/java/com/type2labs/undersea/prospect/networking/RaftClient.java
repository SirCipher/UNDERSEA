package com.type2labs.undersea.prospect.networking;


import com.google.common.util.concurrent.FutureCallback;
import com.type2labs.undersea.common.cluster.Client;
import com.type2labs.undersea.prospect.RaftProtos;
import io.grpc.Deadline;
import io.grpc.StatusRuntimeException;

public interface RaftClient extends Client {

    /**
     * Acquires the status of the pool so that an agent can make informed decisions. Each request is performed as a
     * blocking operation on the stub.
     *
     * @param request  to perform
     * @param deadline to block for
     * @return an agent's status
     * @throws StatusRuntimeException if there is an issue performing the request. Or, as expected, if the deadline
     *                                is exceeded
     */
    RaftProtos.AcquireStatusResponse getStatus(RaftProtos.AcquireStatusRequest request,
                                               Deadline deadline) throws StatusRuntimeException;

    void appendEntry(RaftProtos.AppendEntryRequest request, FutureCallback<RaftProtos.AppendEntryResponse> callback);

    void requestVote(RaftProtos.VoteRequest request, FutureCallback<RaftProtos.VoteResponse> callback);

    void distributeMission(RaftProtos.DistributeMissionRequest request,
                           FutureCallback<RaftProtos.DisributeMissionResponse> callback);

    void alertLeavingCluster(RaftProtos.LeaveClusterRequest request, FutureCallback<RaftProtos.Empty> callback);
}
