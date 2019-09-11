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
 *
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

        ServiceManager serviceManager = agent.services();
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
