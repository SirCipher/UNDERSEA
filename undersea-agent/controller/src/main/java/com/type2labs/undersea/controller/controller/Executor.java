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

package com.type2labs.undersea.controller.controller;

public abstract class Executor {

    private final Knowledge knowledge;
    protected String command;

    public Executor(Knowledge knowledge) {
        this.knowledge = knowledge;
    }

    public Knowledge getKnowledge() {
        return knowledge;
    }

    /**
     * Get the command:
     * it should be in the form: SPEED=xx.xx,SENSOR1=x,SENSOR2=x,....
     */
    public String getCommand() {
        return command;
    }

    /**
     * Construct command:
     * it should be in the form: SPEED=xx.xx,SENSOR1=x,SENSOR2=x,....
     */
    public abstract void run();
}
