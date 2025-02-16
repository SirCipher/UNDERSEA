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

import com.type2labs.undersea.common.service.hardware.NetworkInterface;

public class Effector {

    /**
     * Communication handles
     */
    private NetworkInterface networkInterface;

    /**
     * Command to send to managed system
     */
    private String command;

    /**
     * Reply from managed system
     */
    private String reply;


    /**
     * Constructor: create a new sensor
     */
    public Effector(NetworkInterface networkInterface) {
        this.networkInterface = networkInterface;
    }

    public String getReply() {
        return this.reply;
    }

    public void run() {
        reply = networkInterface.write(command);
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
