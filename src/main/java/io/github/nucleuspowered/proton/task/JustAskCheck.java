package io.github.nucleuspowered.proton.task;

import io.github.nucleuspowered.proton.ProfessorProton;
import io.github.nucleuspowered.proton.config.JustAskConfig;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.TimeUnit;

public class JustAskCheck implements Runnable {

    private GuildMessageReceivedEvent event;

    public JustAskCheck(GuildMessageReceivedEvent event) {
        this.event = event;
    }

    @Override public void run() {
        StopWatch sw = new StopWatch();
        sw.start();

        JustAskConfig config = ProfessorProton.getInstance().getConfig().getJustAsk();
        ProfessorProton.LOGGER.debug("Testing {}'s message for asking to ask.", event.getMember().getEffectiveName());

        // Check if the message matches any of the predefined phrases
        if (config.getPhases().stream().anyMatch(s ->
                StringUtils.getJaroWinklerDistance(s.toLowerCase(), event.getMessage().getContentStripped().toLowerCase()) >= config.getMatch())) {
            event.getChannel().sendMessage(config.getMessage().replace("{{user}}", event.getMember().getAsMention())).queue();
        }

        sw.stop();
        ProfessorProton.LOGGER.debug("Testing for asking to ask completed in {}ms", sw.getTime(TimeUnit.MILLISECONDS));
    }
}
