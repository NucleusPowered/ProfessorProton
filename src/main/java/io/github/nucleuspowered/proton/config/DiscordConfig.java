package io.github.nucleuspowered.proton.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class DiscordConfig {

    @Setting
    private String token = "";
    @Setting
    private String owner = "";
    @Setting
    private String game = "";
    @Setting
    private String consoleChannel = "";

    public String getToken() {
        return token;
    }

    public String getOwner() {
        return owner;
    }

    public String getGame() {
        return game;
    }

    public String getConsoleChannelID() {
        return consoleChannel;
    }
}
