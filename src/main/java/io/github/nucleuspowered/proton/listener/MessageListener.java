package io.github.nucleuspowered.proton.listener;

import io.github.nucleuspowered.proton.ProfessorProton;
import io.github.nucleuspowered.proton.task.DuplicateMessageCheck;
import io.github.nucleuspowered.proton.task.JustAskCheck;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        ProfessorProton.getInstance().getGuildMessageCache(event.getGuild()).put(event.getMessageId(), event.getMessage());

        // Duplicate Message Check
        // Only check members without a role, when enabled
        if (!event.getAuthor().isBot() && ProfessorProton.getInstance().getConfig().getDuplicateMessage().isEnabled()
                && event.getMember().getRoles().isEmpty()) {
            new Thread(new DuplicateMessageCheck(event), "deduplicate").start();
        }

        // Just Ask Check
        // Only check members without a role, when enabled
        if (!event.getAuthor().isBot() && ProfessorProton.getInstance().getConfig().getDuplicateMessage().isEnabled()
                /*&& event.getMember().getRoles().isEmpty()*/) {
            new Thread(new JustAskCheck(event), "just-ask").start();
        }
    }
}
