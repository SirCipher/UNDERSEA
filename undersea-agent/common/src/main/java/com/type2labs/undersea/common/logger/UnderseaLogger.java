package com.type2labs.undersea.common.logger;


import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

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

    }
}
