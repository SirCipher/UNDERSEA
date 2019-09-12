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

package com.type2labs.undersea.dsl.uuv.model;

import com.type2labs.undersea.common.agent.AgentImpl;

/**
 * Proxy for holding the required state while parsing the DSL
 */
public class DslAgentProxy extends AgentImpl {

    private String metaFileName;
    private boolean active = true;

    public DslAgentProxy(String name) {
        super(name);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getMetaFileName() {
        return metaFileName;
    }

    public void setMetaFileName(String metaFileName) {
        this.metaFileName = metaFileName;
    }

}
