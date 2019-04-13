package de.langgezockt.Prefix.utils;

import de.langgezockt.Prefix.Prefix;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * @author langgezockt (langgezockt@gmail.com)
 * 13.04.2019 / 06:03
 * Prefix / de.langgezockt.Prefix.utils
 */

public class Messages {

    private Prefix instance;
    private File messagesFile;
    private FileConfiguration messagesConfig;

    public Messages(Prefix instance) {
        this.instance = instance;
        this.messagesFile = new File(instance.getDataFolder(), "messages.yml");
        this.messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public void setDefaults() {
        this.messagesConfig.options().copyDefaults(true);
        this.messagesConfig.addDefault("No-Permissions", "&cYou don't have permissions to execute this command.");
        this.messagesConfig.addDefault("Player-Only", "&cThis is a player only command.");
        this.messagesConfig.addDefault("Prefix.Add", "&eYou &aadded &ethe prefix &b%name% &e(%prefix%&e)");
        save();
    }

    public String get(String path) {
        return CC.translate(this.messagesConfig.getString(path));
    }

    public void save() {
        try {
            this.messagesConfig.save(this.messagesFile);
        } catch (IOException e) {
            instance.getLogger().warning("Failed to save messages.yml stacktrace below: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
