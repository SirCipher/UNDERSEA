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

package com.type2labs.undersea.missionplanner.manager;

import com.google.common.util.concurrent.*;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.consensus.ConsensusAlgorithm;
import com.type2labs.undersea.common.logger.model.LogEntry;
import com.type2labs.undersea.common.missions.PlannerException;
import com.type2labs.undersea.common.missions.planner.impl.AgentMissionImpl;
import com.type2labs.undersea.common.missions.planner.model.AgentMission;
import com.type2labs.undersea.common.missions.planner.model.GeneratedMission;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.missions.planner.model.MissionPlanner;
import com.type2labs.undersea.common.missions.task.model.Task;
import com.type2labs.undersea.common.missions.task.model.TaskExecutor;
import com.type2labs.undersea.common.missions.task.model.TaskStatus;
import com.type2labs.undersea.common.monitor.model.SubsystemMonitor;
import com.type2labs.undersea.common.service.AgentService;
import com.type2labs.undersea.common.service.ServiceManager;
import com.type2labs.undersea.common.service.transaction.LifecycleEvent;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.common.service.transaction.TransactionData;
import com.type2labs.undersea.missionplanner.task.executor.MeasureExecutor;
import com.type2labs.undersea.missionplanner.task.executor.MoosWaypointExecutor;
import com.type2labs.undersea.missionplanner.task.executor.SurveyExecutor;
import com.type2labs.undersea.missionplanner.task.executor.WaypointExecutor;
import com.type2labs.undersea.utilities.exception.NotSupportedException;
import com.type2labs.undersea.utilities.executor.ThrowableExecutor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Thomas Klapwijk on 2019-08-23.
 */
public class MoosMissionManagerImpl implements MissionManager {

    private static final Logger logger = LogManager.getLogger(MoosMissionManagerImpl.class);
    private final MissionPlanner missionPlanner;
    private final ListeningExecutorService taskExecutor;

    private Agent agent;
    private List<Task> assignedTasks = new ArrayList<>();
    private Task currentTask;
    private GeneratedMission globalMission;
    private AgentMission missionAssigned;

    public MoosMissionManagerImpl(MissionPlanner missionPlanner) {
        this.missionPlanner = missionPlanner;
        this.taskExecutor = MoreExecutors.listeningDecorator(ThrowableExecutor.newSingleThreadExecutor(parent(), logger));
    }

    private void runTask(Task task) {
        TaskExecutor taskExecutor;

        switch (task.getTaskType()) {
            case SURVEY:
                taskExecutor = new SurveyExecutor(task);
                break;
            case WAYPOINT:
                taskExecutor = new WaypointExecutor(task);
                break;
            case MEASURE:
                taskExecutor = new MeasureExecutor(task);
                break;
            default:
                throw new IllegalArgumentException(task.getTaskType() + " is not supported by " + MoosMissionManagerImpl.class.getSimpleName());
        }

        taskExecutor.initialise(agent);

        ListenableFuture<?> listenableFuture = this.taskExecutor.submit(taskExecutor);

        Futures.addCallback(listenableFuture, new FutureCallback<Object>() {
            @Override
            public void onFailure(Throwable t) {
                throw new RuntimeException(t);
            }

            @Override
            public void onSuccess(@Nullable Object result) {
                currentTask = task;
            }
        }, this.taskExecutor);
    }

    @Override
    public void addTasks(List<Task> tasks) {

    }

    @Override
    public void assignMission(GeneratedMission globalMission) {
        this.globalMission = globalMission;
        List<AgentMission> agentMissions =
                globalMission.subMissions().stream().filter(s -> s.peerId().equals(agent.peerId().toString())).collect(Collectors.toList());

        if (agentMissions.size() > 0) {
            this.missionAssigned = agentMissions.get(0);
        }

        for (AgentMission mission : agentMissions) {
            assignedTasks.addAll(mission.getTasks());
        }

        if (currentTask == null && assignedTasks.size() > 0) {
            /*
                Due to having to work around how MOOS has been handling sequential point updates, the points are
                having to be all sent at once and then the tasks updated afterwards.
             */
            for (AgentMission agentMission : agentMissions) {
                if (agentMission instanceof AgentMissionImpl) {
                    TaskExecutor taskExecutor = new MoosWaypointExecutor((AgentMissionImpl) agentMission);
                    taskExecutor.initialise(agent);

                    ListenableFuture<?> listenableFuture = this.taskExecutor.submit(taskExecutor);

                    Futures.addCallback(listenableFuture, new FutureCallback<Object>() {
                        @Override
                        public void onFailure(Throwable t) {
                            throw new RuntimeException(t);
                        }

                        @Override
                        public void onSuccess(@Nullable Object result) {

                        }
                    }, this.taskExecutor);
                } else {
                    runTask(assignedTasks.get(0));
                }
            }
        }

        updateMonitor();
    }

    @Override
    public List<Task> getAssignedTasks() {
        if (missionAssigned == null) {
            return new ArrayList<>();
        }

        return missionAssigned.getTasks();
    }

    @Override
    public boolean missionHasBeenAssigned() {
        return missionAssigned != null;
    }

    @Override
    public MissionPlanner missionPlanner() {
        return missionPlanner;
    }

    @Override
    public void notify(String message) {
        if (message == null) {
            return;
        }

        if (message.contains("WPT_INDEX") || message.contains("completed")) {
            handleWaypointIndexUpdate(message);
        }

        updateMonitor();
    }

    private void updateMonitor() {
        agent.serviceManager().getService(SubsystemMonitor.class).update();
    }

    private void handleWaypointIndexUpdate(String message) {
        int index;

        if (message.contains("completed")) {
            index = assignedTasks.size() - 1;
        } else {
            index = Integer.parseInt(message.split("=")[1]);

            // If we're on our way to the first waypoint
            if (index == 0) {
                missionAssigned.setStarted(true);
                return;
            } else {
                index -= 1;
            }
        }

        if (true) {
            return;
        }

        Task task = assignedTasks.get(index);
        task.setTaskStatus(TaskStatus.COMPLETED);

        ServiceManager serviceManager = agent.serviceManager();
        ConsensusAlgorithm consensusAlgorithm = serviceManager.getService(ConsensusAlgorithm.class, true);

        agent.log(new LogEntry(consensusAlgorithm.leaderPeerId(), task.getUuid(), TaskStatus.COMPLETED,
                consensusAlgorithm.term(), this, true));

        if (index == assignedTasks.size() - 1) {
            logger.info(agent.name() + ": completed all tasks", agent);
        } else {
            logger.info(agent.name() + ": completed task " + (index + 1), agent);
        }
    }

    @Override
    public void initialise(Agent parentAgent) {
        this.agent = parentAgent;
        missionPlanner.initialise(parentAgent);
    }

    @Override
    public Agent parent() {
        return agent;
    }

    @Override
    public void run() {

    }

    /**
     * Execues a given {@link Transaction} on the mission planner. Supported {@link LifecycleEvent}s are:
     * {@link LifecycleEvent#ELECTED_LEADER} - which will generate a mission in accordance with the current
     * cluster state.
     *
     * @param transaction to execute on the mission planner
     * @return a {@link ListenableFuture} returned by the provided {@link java.util.concurrent.ExecutorService}
     */
    @Override
    public Object executeTransaction(Transaction transaction) {
        logger.info(agent.name() + ": received transaction: " + transaction, agent);
        LifecycleEvent statusCode = (LifecycleEvent) transaction.getStatusCode();

        if (statusCode == LifecycleEvent.ELECTED_LEADER) {
            return transaction.getExecutorService().submit(() -> {
                try {
                    GeneratedMission generatedMission = missionPlanner.generate();
                    missionPlanner.print(generatedMission);

                    return generatedMission;
                } catch (PlannerException e) {
                    throw new RuntimeException(e);
                }
            });
        } else if (statusCode == LifecycleEvent.APPEND_REQUEST) {
            handleAppendTransaction(transaction);
        }

        String message = MoosMissionManagerImpl.class.getSimpleName() + " does not support status code: " + statusCode +
                ". Called by: " + transaction.getCaller();

        throw new NotSupportedException(message);
    }

    private void handleAppendTransaction(Transaction transaction) {
        TransactionData<?> uuidTransactionData = transaction.getPrimaryTransactionData();
        String uuid = (String) uuidTransactionData.getData();

        if (!StringUtils.isEmpty(uuid)) {
            List<Task> subTasks = globalMission.allTasks();

            for (Task t : subTasks) {
                if (t.getUuid().toString().equals(uuid)) {
                    TransactionData<?> taskStatusTransactionData = transaction.getSecondaryTransactionData();
                    TaskStatus newTaskStatus = TaskStatus.valueOf(taskStatusTransactionData.getData().toString());

                    t.setTaskStatus(newTaskStatus);

                    int index = subTasks.indexOf(t);
                    subTasks.set(index, t);

                    logger.info(agent.name() + ": updated task: " + uuid + " to new status: " + newTaskStatus,
                            agent);

                    return;
                }
            }

            logger.warn(agent.name() + ": failed to find matching UUID: " + uuid, agent);
        }
    }

    @Override
    public Collection<Class<? extends AgentService>> requiredServices() {
        return Arrays.asList(ConsensusAlgorithm.class);
    }

}
