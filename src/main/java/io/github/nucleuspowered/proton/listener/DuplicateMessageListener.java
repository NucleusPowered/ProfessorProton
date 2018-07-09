package io.github.nucleuspowered.proton.listener;

import io.github.nucleuspowered.proton.ProfessorProton;
import io.github.nucleuspowered.proton.config.DuplicateMessageConfig;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class DuplicateMessageListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        // Only check members without a role, when enabled
        if (!event.getAuthor().isBot() && ProfessorProton.getInstance().getConfig().getDuplicateMessage().isEnabled()
            /*&& event.getMember().getRoles().isEmpty()*/) {
            Thread t = new Thread(new DuplicateMessageThread(event), "deduplicate");
            t.start();
        }
    }

    private static class DuplicateMessageThread implements Runnable {

        private GuildMessageReceivedEvent event;

        public DuplicateMessageThread(GuildMessageReceivedEvent event) {
            this.event = event;
        }

        @Override
        public void run() {
            StopWatch sw = new StopWatch();
            sw.start();

            DuplicateMessageConfig config = ProfessorProton.getInstance().getConfig().getDuplicateMessage();
            ProfessorProton.LOGGER.debug("Checking {}'s message for duplicates.", event.getMember().getEffectiveName());
            List<Message> messages = ProfessorProton.getInstance().getGuildMessageCache(event.getGuild()).asMap().values().stream()
                    .filter(m -> m.getAuthor().getId().equals(event.getAuthor().getId()))
                    .collect(Collectors.toList());
            ProfessorProton.LOGGER.debug("Found {} recent message from {}.", messages.size(), event.getMember().getEffectiveName());

            // Count messages that exceed the min length and match requirement
            int duplicates = (int) messages.stream()
                    .filter(m -> !event.getMessageId().equals(m.getId()))
                    .filter(m -> event.getMessage().getContentStripped().length() >= config.getMinLength() &&
                            StringUtils.getJaroWinklerDistance(m.getContentStripped(), event.getMessage().getContentStripped()) >= config.getMatch())
                    .count();

            ProfessorProton.LOGGER.debug("Found {} similar messages.", duplicates);

            if (duplicates >= config.getWarnThreshold()) {
                event.getChannel().sendMessage(config.getMessage().replace("{{user}}", event.getMember().getAsMention())).queue();
                ProfessorProton.LOGGER.info("{} warned for sending {} duplicate messages.", event.getMember().getEffectiveName(), duplicates);
            }

            sw.stop();
            ProfessorProton.LOGGER.debug("Checking for duplicate messages completed in {}ms", sw.getTime(TimeUnit.MILLISECONDS));
        }
    }
}
