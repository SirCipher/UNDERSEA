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

package com.type2labs.undersea.utilities.lang;

/**
 * A task which will be rerun if there is a failure that occurs
 */
public abstract class ReschedulableTask implements Runnable {

    public static int maxRunCount = 3;

    private int runCount;
    private long rerunTimeWindow = 1000L;

    public static ReschedulableTask wrap(Runnable runnable) {
        return new ReschedulableTask() {
            @Override
            public void innerRun() {
                runnable.run();
            }
        };
    }

    public int getRunCount() {
        return runCount;
    }

    public void incrementRunCount() {
        this.runCount++;
    }

    public long getRerunTimeWindow() {
        return rerunTimeWindow;
    }

    public void setRerunTimeWindow(long rerunTimeWindow) {
        this.rerunTimeWindow = rerunTimeWindow;
    }

    @Override
    public final void run() {
        this.innerRun();
    }

    public abstract void innerRun();

}
