package io.github.nucleuspowered.proton.task;

import com.github.benmanes.caffeine.cache.Cache;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

public class UpdateMessageCache implements Runnable {

    private final Guild guild;
    private final Cache<String, Message> cache;

    public UpdateMessageCache(Guild guild, Cache<String, Message> cache) {
        this.guild = guild;
        this.cache = cache;
    }

    @Override public void run() {
        for (TextChannel channel : guild.getTextChannels()) {
            for (Message message : channel.getHistory().retrievePast(100).complete()) {
                cache.put(message.getId(), message);
            }
        }
    }
}
