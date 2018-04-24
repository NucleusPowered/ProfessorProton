package io.github.nucleuspowered.proton;

import io.github.nucleuspowered.proton.config.BotConfig;
import io.github.nucleuspowered.proton.config.ConfigManager;
import io.github.nucleuspowered.proton.listener.DuplicateMessageListener;
import io.github.nucleuspowered.proton.listener.MentionListener;
import io.github.nucleuspowered.proton.listener.PrivateMessageListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;

import javax.security.auth.login.LoginException;

public class ProfessorProton {

    private static ProfessorProton instance;
    public static final Logger LOGGER = LogManager.getLogger(ProfessorProton.class);

    private ConfigurationLoader<CommentedConfigurationNode> loader;
    private ConfigManager configManager;
    private BotConfig config;

    private JDA jda;

    public static void main(String[] args) {
        instance = new ProfessorProton();
    }

    private ProfessorProton() {
        LOGGER.info("Professor Proton is now loading.");
        initConfig();
        try {
            initDiscordBot();
        } catch (Exception e) {
            LOGGER.error("Error initializing Discord bot instance.", e);
        }
    }

    private void initConfig() {
        config = new BotConfig();
        loader = HoconConfigurationLoader.builder()
                .setPath(Paths.get("./ProfessorProton.conf"))
                .build();
        configManager = new ConfigManager(config, loader);
        configManager.save();
    }

    private void initDiscordBot() throws LoginException, InterruptedException {
        jda = new JDABuilder(AccountType.BOT)
                .setToken(config.getDiscord().getToken())
                .addEventListener(new PrivateMessageListener())
                .addEventListener(new DuplicateMessageListener())
                .addEventListener(new MentionListener())
                .buildBlocking();

        LOGGER.info("Bot initialization complete.");
    }

    public static ProfessorProton getInstance() {
        return instance;
    }

    public JDA getBot() {
        return jda;
    }

    public BotConfig getConfig() {
        return config;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
