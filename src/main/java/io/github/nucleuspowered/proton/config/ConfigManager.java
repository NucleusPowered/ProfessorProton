package io.github.nucleuspowered.proton.config;

import io.github.nucleuspowered.proton.ProfessorProton;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.commented.SimpleCommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMapper;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;

public class ConfigManager {

    private ObjectMapper<BotConfig>.BoundInstance configMapper;
    private ConfigurationLoader<CommentedConfigurationNode> loader;

    public ConfigManager(BotConfig config, ConfigurationLoader<CommentedConfigurationNode> loader) {
        this.loader = loader;
        try {
            this.configMapper = ObjectMapper.forObject(config);
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }

        this.load();
    }

    /**
     * Saves the serialized config to file
     */
    public void save() {
        try {
            SimpleCommentedConfigurationNode out = SimpleCommentedConfigurationNode.root();
            this.configMapper.serialize(out);
            this.loader.save(out);
        } catch (ObjectMappingException | IOException e) {
            ProfessorProton.LOGGER.error("Failed to save config.", e);
        }
    }

    /**
     * Loads the configs into serialized objects, for the configMapper
     */
    public void load() {
        try {
            this.configMapper.populate(this.loader.load());
        } catch (ObjectMappingException | IOException e) {
            ProfessorProton.LOGGER.error("Failed to load config.", e);
        }
    }
}