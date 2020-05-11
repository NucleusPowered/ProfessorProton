package io.github.nucleuspowered.proton.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class SupportConfig {

    @Setting
    private DialogflowConfig dialogflow = new DialogflowConfig();

    public DialogflowConfig getDialogflow() {
        return dialogflow;
    }
}
