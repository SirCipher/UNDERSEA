package com.type2labs.undersea.common.missions.task.executor;

import com.type2labs.undersea.common.missions.task.model.TaskType;

/**
 * Created by Thomas Klapwijk on 2019-08-23.
 */
public interface TaskExecutor {

    void execute(TaskType taskType);

}
