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

package com.type2labs.undersea.prospect.networking.model;


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

    void broadcastMembershipChanges(RaftProtos.ClusterMembersRequest request,
                                    FutureCallback<RaftProtos.Empty> callback);
}
