package de.langgezockt.Prefix;

import de.langgezockt.Prefix.commands.PrefixCommand;
import de.langgezockt.Prefix.listener.PrefixListener;
import de.langgezockt.Prefix.utils.ConfigurationService;
import de.langgezockt.Prefix.utils.Messages;
import de.langgezockt.Prefix.utils.manager.PrefixManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author langgezockt (langgezockt@gmail.com)
 * 13.04.2019 / 05:33
 * Prefix / de.langgezockt.Prefix
 */

public class Prefix extends JavaPlugin {

    @Getter private static Prefix instance;
    @Getter private ConfigurationService configurationService;
    @Getter private Messages messages;
    @Getter private PrefixManager prefixManager;

    @Override
    public void onEnable() {
        instance = this;

        loadFiles();

        loadMySQL();

        registerCommands();
        registerListener(Bukkit.getPluginManager());

    }

    private void registerCommands() {
        getCommand("prefix").setExecutor(new PrefixCommand(this));
    }

    private void registerListener(PluginManager pluginManager) {
        pluginManager.registerEvents(new PrefixListener(this), this);
    }

    private void loadMySQL() {

    }

    private void loadFiles() {
        configurationService = new ConfigurationService(this);
        configurationService.setDefaults();
        configurationService.load();

        messages = new Messages(this);
        messages.setDefaults();

        prefixManager = new PrefixManager(this);
        prefixManager.setDefaults();
        prefixManager.loadPrefixes();
    }

}
