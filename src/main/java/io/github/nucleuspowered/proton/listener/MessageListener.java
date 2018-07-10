package io.github.nucleuspowered.proton.listener;

import io.github.nucleuspowered.proton.ProfessorProton;
import io.github.nucleuspowered.proton.task.DuplicateMessageCheck;
import io.github.nucleuspowered.proton.task.JustAskCheck;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        ProfessorProton.getInstance().getGuildMessageCache(event.getGuild()).put(event.getMessageId(), event.getMessage());

        // Check if the user has been recently warned
        Optional<Instant> lastWarning = ProfessorProton.getInstance().getLastWarning(event.getAuthor());
        boolean suppressWarnings = false;
        if (lastWarning.isPresent() && Duration.between(lastWarning.get(), Instant.now()).getSeconds()
                <= ProfessorProton.getInstance().getConfig().getWarningCooldown()) {
            // Ignore the user's message
            ProfessorProton.LOGGER.info("{}'s last warning: {} sec(s). Warnings suppressed on current message.",
                    event.getMember().getEffectiveName(),
                    Duration.between(lastWarning.get(), Instant.now()).getSeconds()
            );
            suppressWarnings = true;
        }

        // Duplicate Message Check
        // Only check members without a role, when enabled
        if (!event.getAuthor().isBot() && ProfessorProton.getInstance().getConfig().getDuplicateMessage().isEnabled()
                && event.getMember().getRoles().isEmpty()) {
            new Thread(new DuplicateMessageCheck(event, suppressWarnings), "deduplicate").start();
        }

        // Just Ask Check
        // Only check members without a role, when enabled
        if (!event.getAuthor().isBot() && ProfessorProton.getInstance().getConfig().getDuplicateMessage().isEnabled()
                && event.getMember().getRoles().isEmpty()) {
            new Thread(new JustAskCheck(event, suppressWarnings), "just-ask").start();
        }
    }
}
