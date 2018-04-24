package io.github.nucleuspowered.proton.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class DuplicateMessageConfig {

    @Setting
    private int warnThreshold = 2;
    @Setting
    private double match = 0.90;
    @Setting
    private int minLength = 5;
    @Setting
    private String message = "{{user}} please be patient & refrain from sending duplicate messages!";

    public boolean isEnabled() {
        return warnThreshold > 0;
    }

    public int getWarnThreshold() {
        return warnThreshold;
    }

    public double getMatch() {
        return match;
    }

    public int getMinLength() {
        return minLength;
    }

    public String getMessage() {
        return message;
    }
}
