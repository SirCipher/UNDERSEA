package com.type2labs.undersea.missionplanner.manager;

import com.google.common.util.concurrent.*;
import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.missions.PlannerException;
import com.type2labs.undersea.common.missions.planner.model.GeneratedMission;
import com.type2labs.undersea.common.missions.planner.model.MissionManager;
import com.type2labs.undersea.common.missions.planner.model.MissionPlanner;
import com.type2labs.undersea.common.missions.task.model.Task;
import com.type2labs.undersea.common.missions.task.model.TaskExecutor;
import com.type2labs.undersea.common.service.transaction.Transaction;
import com.type2labs.undersea.common.service.transaction.TransactionStatusCode;
import com.type2labs.undersea.missionplanner.task.executor.MeasureExecutor;
import com.type2labs.undersea.missionplanner.task.executor.SurveyExecutor;
import com.type2labs.undersea.missionplanner.task.executor.WaypointExecutor;
import com.type2labs.undersea.utilities.exception.NotSupportedException;
import com.type2labs.undersea.utilities.executor.ThrowableExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Thomas Klapwijk on 2019-08-23.
 */
public class MissionManagerImpl implements MissionManager {

    private static final Logger logger = LogManager.getLogger(MissionManagerImpl.class);
    private final MissionPlanner missionPlanner;
    private final ListeningExecutorService singleThreadExecutor;
    private final Map<Task, ListenableFuture<?>> activeTasks = new HashMap<>();

    private Agent agent;
    private List<Task> tasks = new ArrayList<>();
    private GeneratedMission missionAssigned;
    private int currentTask = 0;

    public MissionManagerImpl(MissionPlanner missionPlanner) {
        this.missionPlanner = missionPlanner;
        this.singleThreadExecutor = MoreExecutors.listeningDecorator(ThrowableExecutor.newSingleThreadExecutor(logger));
    }

    @Override
    public void addTasks(List<Task> tasks) {
        this.tasks.addAll(tasks);

        for (Task t : tasks) {
            TaskExecutor taskExecutor;

            switch (t.getTaskType()) {
                case SURVEY:
                    taskExecutor = new SurveyExecutor(t);
                    break;
                case WAYPOINT:
                    taskExecutor = new WaypointExecutor(t);
                    break;
                case MEASURE:
                    taskExecutor = new MeasureExecutor(t);
                    break;
                default:
                    throw new IllegalArgumentException(t.getTaskType() + " is not supported by " + MissionManagerImpl.class.getSimpleName());
            }

            taskExecutor.initialise(agent);

            ListenableFuture<?> listenableFuture = singleThreadExecutor.submit(taskExecutor);
            activeTasks.put(t, listenableFuture);

            Futures.addCallback(listenableFuture, new FutureCallback<Object>() {
                @Override
                public void onSuccess(@Nullable Object result) {
                    activeTasks.remove(t);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    activeTasks.remove(t);
                    // TODO: 2019-08-24 Handle properly...
                    throw new RuntimeException(throwable);
                }
            }, singleThreadExecutor);
        }
    }

    @Override
    public void cancelAllTasks() {
        activeTasks.forEach((key, value) -> value.cancel(true));
    }

    @Override
    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public boolean missionHasBeenAssigned() {
        return tasks != null && tasks.size() > 0;
    }

    @Override
    public MissionPlanner missionPlanner() {
        return missionPlanner;
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
        activeTasks.forEach((key, value) -> value.cancel(false));
    }

    /**
     * Execues a given {@link Transaction} on the mission planner. Supported {@link TransactionStatusCode}s are:
     * {@link TransactionStatusCode#ELECTED_LEADER} - which will generate a mission in accordance with the current
     * cluster state.
     *
     * @param transaction to execute on the mission planner
     * @return a {@link ListenableFuture} returned by the provided {@link java.util.concurrent.ExecutorService}
     */
    @Override
    public ListenableFuture<?> executeTransaction(Transaction transaction) {
        logger.info("Received transaction: " + transaction);
        TransactionStatusCode statusCode = (TransactionStatusCode) transaction.getStatusCode();

        if (statusCode == TransactionStatusCode.ELECTED_LEADER) {
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
            throw new NotSupportedException(MissionManagerImpl.class.getSimpleName()
                    + " does not support status code: " + statusCode);
        }
    }
}
