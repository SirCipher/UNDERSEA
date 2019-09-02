package com.type2labs.undersea.missionplanner.manager;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.missions.PlannerException;
import com.type2labs.undersea.common.missions.planner.impl.AgentMissionImpl;
import com.type2labs.undersea.common.missions.planner.model.AgentMission;
import com.type2labs.undersea.common.missions.planner.model.GeneratedMission;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.missions.planner.model.MissionPlanner;
import com.type2labs.undersea.common.missions.task.model.Task;
import com.type2labs.undersea.common.missions.task.model.TaskExecutor;
import com.type2labs.undersea.common.missions.task.model.TaskStatus;
import com.type2labs.undersea.common.monitor.model.Monitor;
import com.type2labs.undersea.common.service.transaction.LifecycleEvent;
import com.type2labs.undersea.common.service.transaction.ServiceCallback;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.missionplanner.task.executor.MeasureExecutor;
import com.type2labs.undersea.missionplanner.task.executor.MoosWaypointExecutor;
import com.type2labs.undersea.missionplanner.task.executor.SurveyExecutor;
import com.type2labs.undersea.missionplanner.task.executor.WaypointExecutor;
import com.type2labs.undersea.utilities.concurrent.SimpleFutureCallback;
import com.type2labs.undersea.utilities.exception.NotSupportedException;
import com.type2labs.undersea.utilities.executor.ThrowableExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

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
    private AgentMission missionAssigned;

    public MoosMissionManagerImpl(MissionPlanner missionPlanner) {
        this.missionPlanner = missionPlanner;
        this.taskExecutor = MoreExecutors.listeningDecorator(ThrowableExecutor.newSingleThreadExecutor(logger));
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

        Futures.addCallback(listenableFuture, new SimpleFutureCallback<Object>() {
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
    public void assignMission(AgentMission agentMission) {
        this.missionAssigned = agentMission;
        this.assignedTasks.addAll(agentMission.getTasks());

        if (currentTask == null && assignedTasks.size() > 0) {
            /*
                Due to having to work around how MOOS has been handling sequential point updates, the points are
                having to be all sent at once and then the tasks updated afterwards.
             */
            if (agentMission instanceof AgentMissionImpl) {
                TaskExecutor taskExecutor = new MoosWaypointExecutor((AgentMissionImpl) agentMission);
                taskExecutor.initialise(agent);

                ListenableFuture<?> listenableFuture = this.taskExecutor.submit(taskExecutor);

                Futures.addCallback(listenableFuture, new SimpleFutureCallback<Object>() {
                    @Override
                    public void onSuccess(@Nullable Object result) {
                    }
                }, this.taskExecutor);
            } else {
                runTask(assignedTasks.get(0));
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
        return assignedTasks != null && assignedTasks.size() > 0;
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

        if (message.contains("WPT_INDEX")) {
            handleWaypointIndexUpdate(message);
        }

        updateMonitor();
    }

    private void updateMonitor() {
        agent.services().getService(Monitor.class).update();
    }

    private void handleWaypointIndexUpdate(String message) {
        int index = Character.getNumericValue(message.charAt(message.length() - 1));

        // If we're on our way to the first waypoint
        if (index == 0) {
            return;
        }

        assignedTasks.get(index).setTaskStatus(TaskStatus.COMPLETED);

        logger.info(agent.name() + ": completed task " + index, agent);
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

    @Override
    public void shutdown() {

    }

    @Override
    public boolean started() {
        return true;
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
    public ListenableFuture<?> executeTransaction(Transaction transaction) {
        logger.info(agent.name() + ":received transaction: " + transaction, agent);
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
        } else {
            String message =
                    MoosMissionManagerImpl.class.getSimpleName() + " does not support status code: " + statusCode +
                            ". " +
                            "Called by: " + transaction.getCaller();
            logger.error(message);
            throw new NotSupportedException(message);
        }
    }

    @Override
    public void registerCallback(ServiceCallback serviceCallback) {

    }

}
