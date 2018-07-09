package io.github.nucleuspowered.proton.listener;

import io.github.nucleuspowered.proton.ProfessorProton;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        ProfessorProton.getInstance().getGuildMessageCache(event.getGuild()).put(event.getMessageId(), event.getMessage());
    }
}
