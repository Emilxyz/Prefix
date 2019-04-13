package de.langgezockt.Prefix.utils.manager;

import de.langgezockt.Prefix.Prefix;
import de.langgezockt.Prefix.utils.CC;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author langgezockt (langgezockt@gmail.com)
 * 13.04.2019 / 05:40
 * Prefix / de.langgezockt.Prefix.utils.manager
 */

public class PrefixManager {

    private Prefix instance;
    private File prefixFile;
    private FileConfiguration prefixConfig;
    private Map<String, String> prefixes;

    public PrefixManager(Prefix instance) {
        this.instance = instance;
        prefixFile = new File(instance.getDataFolder(), "prefix.yml");
        prefixConfig = YamlConfiguration.loadConfiguration(prefixFile);
        prefixes = new HashMap<>();
    }

    public void setDefaults() {
        this.prefixConfig.options().copyDefaults(true);
        this.prefixConfig.addDefault("Prefixes.Name.Prefix", "&7[&cExample&7]");
        this.prefixConfig.addDefault("Prefixes.Name.Permission", "prefix.example");
        save();
    }

    public void addPrefix(String name, String prefix) {
        this.prefixConfig.set("Prefixes." + name + ".Prefix", prefix);
        this.prefixConfig.set("Prefixes." + name + ".Permission", "prefix." + name);
        prefixes.put(name, CC.translate(prefix));
        save();
    }

    public void loadPrefixes() {
        ConfigurationSection configurationSection = prefixConfig.getConfigurationSection("Prefixes");
        configurationSection.getValues(false).forEach((k, v) -> {
            prefixes.put(k, CC.translate(prefixConfig.getString("Prefixes." + k + ".Prefix")));
        });
    }

    public void save() {
        try {
            prefixConfig.save(prefixFile);
        } catch (IOException e) {
            instance.getLogger().warning("Failed to save prefix.yml stacktrace below: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
