package io.github.nucleuspowered.proton.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class BotConfig {

    @Setting
    private DiscordConfig discord = new DiscordConfig();
    @Setting
    private CacheConfig cache = new CacheConfig();
    @Setting
    private MentionConfig mention = new MentionConfig();
    @Setting
    private SupportConfig support = new SupportConfig();
    @Setting
    private DuplicateMessageConfig duplicateMessage = new DuplicateMessageConfig();

    public DiscordConfig getDiscord() {
        return discord;
    }

    public CacheConfig getCache() {
        return cache;
    }

    public MentionConfig getMention() {
        return mention;
    }

    public SupportConfig getSupport() {
        return support;
    }

    public DuplicateMessageConfig getDuplicateMessage() {
        return duplicateMessage;
    }
}
