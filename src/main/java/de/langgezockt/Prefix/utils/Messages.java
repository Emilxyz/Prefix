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
        this.messagesConfig.addDefault("Page.First", "&cYou are already on the first page");
        this.messagesConfig.addDefault("Page.Last", "&cYou are already on the last page");
        this.messagesConfig.addDefault("Prefix.Add", "&eYou &aadded &ethe prefix &b%name%&e. (%prefix%&e)");
        this.messagesConfig.addDefault("Prefix.Remove", "&eYou &cremoved &ethe prefix &b%name%&e.");
        this.messagesConfig.addDefault("Prefix.Reload", "&eYou &asuccessfully &ereloaded all prefixes.");
        this.messagesConfig.addDefault("Prefix.Set", "&eYour prefix has been set to %prefix%");
        this.messagesConfig.addDefault("Prefix.Reset", "&eYour prefix has been reset.");
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
