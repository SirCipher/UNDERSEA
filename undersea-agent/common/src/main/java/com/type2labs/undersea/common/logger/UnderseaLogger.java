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

package com.type2labs.undersea.common.logger;


import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.monitor.model.SubsystemMonitor;
import com.type2labs.undersea.common.monitor.model.VisualiserClient;
import com.type2labs.undersea.common.service.ServiceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import java.io.IOException;
import java.io.Serializable;

/**
 * A custom logger that will pipe all log messages that contained an {@link Agent} object to the Visualiser if it is
 * running. To use, add the appender to the log4j2.xml/properties file
 */
@SuppressWarnings("unused")
@Plugin(
        name = "UnderseaLogger",
        category = Core.CATEGORY_NAME,
        elementType = Appender.ELEMENT_TYPE)
public class UnderseaLogger extends AbstractAppender {

    protected UnderseaLogger(String name, Filter filter, Layout<? extends Serializable> layout) {
        super(name, filter, layout);
    }

    @PluginFactory
    public static UnderseaLogger createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Filter") Filter filter,
            @PluginElement("Layout") Layout<? extends Serializable> layout) {
        return new UnderseaLogger(name, filter, layout);
    }

    public static void info(Logger logger, Agent agent, String message) {
        logger.info(agent.name() + ": " + message, agent);
    }

    public static void warn(Logger logger, Agent agent, String message) {
        logger.warn(agent.name() + ": " + message, agent);
    }

    @Override
    public void append(LogEvent event) {
        Object[] parameters = event.getMessage().getParameters();
        Agent agent;

        if (parameters.length == 1 && parameters[0] instanceof Agent) {
            agent = (Agent) parameters[0];
        } else {
            return;
        }

        ServiceManager serviceManager = agent.serviceManager();
        if (serviceManager == null) {
            return;
        }

        /*
            TODO: This will currently prevent all logging during the service manager's initialisation. Or if any
             service has crashed
         */
        if (!serviceManager.isHealthy()) {
            return;
        }

        SubsystemMonitor subsystemMonitor = serviceManager.getService(SubsystemMonitor.class);
        if (subsystemMonitor == null) {
            return;
        }

        String message = new String(getLayout().toByteArray(event));
        VisualiserMessage visualiserMessage = new VisualiserMessage(agent.peerId(), message,
                event.getLevel() == Level.ERROR);
        VisualiserClient visualiserClient = subsystemMonitor.visualiser();

        try {
            visualiserClient.write(visualiserMessage);
        } catch (IOException e) {
            throw new RuntimeException("Failed to send message: " + visualiserMessage, e);
        }

    }
}
