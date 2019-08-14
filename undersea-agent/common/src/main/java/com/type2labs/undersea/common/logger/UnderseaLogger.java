package com.type2labs.undersea.common.logger;


import com.type2labs.undersea.common.agent.Agent;
import com.type2labs.undersea.common.monitor.VisualiserClient;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import java.io.IOException;
import java.io.Serializable;

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

    @Override
    public void append(LogEvent event) {
        Object[] parameters = event.getMessage().getParameters();
        Agent agent;

        if (parameters.length == 1 && parameters[0] instanceof Agent) {
            agent = (Agent) parameters[0];
        } else {
            return;
        }

        String message = new String(getLayout().toByteArray(event));
        LogMessage logMessage = new LogMessage(agent.name(), message);

        VisualiserClient visualiserClient = agent.getMonitor().visualiser();

        try {
            visualiserClient.write(logMessage);
        } catch (IOException e) {
            // Logger cannot be used here
            throw new RuntimeException("Failed to send message: " + logMessage, e);
        }

    }
}
