package io.github.nucleuspowered.proton;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;

@Plugin(name = "Discord", category = "Core", elementType = "appender", printObject = true)
public class DiscordAppender extends AbstractAppender {

    protected DiscordAppender(String name, Filter filter, Layout<? extends Serializable> layout, final boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
    }

    @Override
    public void append(LogEvent event) {
        if (ProfessorProton.getInstance() != null && ProfessorProton.getInstance().getBot() != null) {
            ProfessorProton.getInstance().getConsole().ifPresent(c -> c.sendMessage(event.getMessage().getFormattedMessage()).queue());
        }
    }

    @PluginFactory
    public static DiscordAppender createAppender(@PluginAttribute("name") String name, @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") final Filter filter, @PluginAttribute("otherAttribute") String otherAttribute) {
        if (name == null) {
            LOGGER.error("No name provided for DiscordAppender");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new DiscordAppender(name, filter, layout, true);
    }
}
