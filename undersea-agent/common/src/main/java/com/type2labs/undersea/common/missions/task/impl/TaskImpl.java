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

package com.type2labs.undersea.common.missions.task.impl;

import com.type2labs.undersea.common.missions.task.model.Task;
import com.type2labs.undersea.common.missions.task.model.TaskStatus;
import com.type2labs.undersea.common.missions.task.model.TaskType;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class TaskImpl implements Task {

    private double[] coordinates;
    private double progress;
    private TaskType taskType;
    private TaskStatus taskStatus = TaskStatus.TODO;
    private UUID uuid;

    public TaskImpl() {

    }


    public TaskImpl(double[] coordinates, TaskType taskType) {
        this.coordinates = coordinates;
        this.taskType = taskType;
        this.uuid = UUID.randomUUID();
    }

    @Override
    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public double getProgress() {
        return progress;
    }

    @Override
    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    @Override
    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;

        if (taskStatus == TaskStatus.COMPLETED) {
            this.progress = 100;
        }
    }

    @Override
    public String toString() {
        return "TaskImpl{" +
                "coordinates=" + Arrays.toString(coordinates) +
                ", progress=" + progress +
                ", taskType=" + taskType +
                ", taskStatus=" + taskStatus +
                '}';
    }

    @Override
    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskImpl task = (TaskImpl) o;
        return Objects.equals(getUuid(), task.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }
}
