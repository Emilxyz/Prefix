package de.langgezockt.Prefix.utils;

import de.langgezockt.Prefix.Prefix;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * @author langgezockt (langgezockt@gmail.com)
 * 13.04.2019 / 05:38
 * Prefix / de.langgezockt.Prefix.utils
 */

public class ConfigurationService {


    private Prefix instance;
    private File configFile;
    private FileConfiguration config;

    public ConfigurationService(Prefix instance) {
        this.instance = instance;
        configFile = new File(instance.getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public static String STORE_URL;

    public void setDefaults() {
        this.config.options().copyDefaults(true);
        this.config.addDefault("Store-URL", "store.yourserver.com");
        save();
    }

    public void load() {
        STORE_URL = this.config.getString("Store-URL");
    }

    public void save() {
        try {
            this.config.save(this.configFile);
        } catch (IOException e) {
            instance.getLogger().warning("Failed to save config.yml stacktrace below: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
