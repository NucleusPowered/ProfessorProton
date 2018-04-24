package io.github.nucleuspowered.proton.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class MentionConfig {

    @Setting
    private boolean warnOnMention = true;
    @Setting
    private String message = "{{user}} Please do not tag the developers or support unless you are requested to do so!\n"
            + "Please review <#ID> for more details.";

    public boolean isWarnOnMention() {
        return warnOnMention;
    }

    public String getMessage() {
        return message;
    }
}
