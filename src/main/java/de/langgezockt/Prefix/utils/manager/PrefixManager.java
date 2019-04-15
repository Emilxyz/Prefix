package de.langgezockt.Prefix.utils.manager;

import de.langgezockt.Prefix.Prefix;
import de.langgezockt.Prefix.utils.CC;
import de.langgezockt.Prefix.utils.ConfigurationService;
import de.langgezockt.Prefix.utils.ItemBuilder;
import de.langgezockt.Prefix.utils.PrefixInventory;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @Getter private Map<String, String> prefixes;
    @Getter private PrefixInventory prefixInventory;

    public PrefixManager(Prefix instance) {
        this.instance = instance;
        prefixFile = new File(instance.getDataFolder(), "prefix.yml");
        prefixConfig = YamlConfiguration.loadConfiguration(prefixFile);
        prefixes = new HashMap<>();
        prefixInventory = new PrefixInventory(instance);
    }

    public void setDefaults() {
        this.prefixConfig.options().copyDefaults(true);
        save();
    }

    public void addPrefix(String name, String prefix) {
        this.prefixConfig.set("Prefixes." + name + ".Prefix", prefix);
        this.prefixConfig.set("Prefixes." + name + ".Permission", "prefix." + name);
        prefixes.put(name, prefix);
        save();
    }

    public void removePrefix(String name) {
        this.prefixConfig.set("Prefixes." + name, null);
        prefixes.remove(name);
        save();
    }

    public void loadPrefixes() {
        ConfigurationSection configurationSection = this.prefixConfig.getConfigurationSection("Prefixes");
        if(configurationSection == null) {
            return;
        }
        configurationSection.getValues(false).forEach((k, v) -> {
            prefixes.put(k, this.prefixConfig.getString("Prefixes." + k + ".Prefix"));
        });
    }

    public List<ItemStack> getItems(Player player) {
        List<ItemStack> list = new ArrayList<>();
        ConfigurationSection configurationSection = this.prefixConfig.getConfigurationSection("Prefixes");
        if(configurationSection == null) {
            return list;
        }
        configurationSection.getValues(false).forEach((k, v) -> {
            if(player.hasPermission(this.prefixConfig.getString("Prefixes." + k + ".Permission"))) {
                ItemStack itemStack = new ItemBuilder(Material.NAME_TAG).setDisplayName(CC.RED + k)
                        .setLore(CC.MENU_BAR,
                                CC.GRAY + "Click here to use the " + CC.RED + k + CC.GRAY + " prefix.",
                                " ",
                                CC.GRAY + "Displays as: " + CC.translate(this.prefixConfig.getString("Prefixes." + k + ".Prefix")),
                                CC.MENU_BAR).build();
                list.add(itemStack);
            } else {
                ItemStack itemStack = new ItemBuilder(Material.NAME_TAG).setDisplayName(CC.RED + k)
                        .setLore(CC.MENU_BAR,
                                CC.GRAY + "Buy this prefix at: " + CC.RED + ConfigurationService.STORE_URL + CC.GRAY + ".",
                                " ",
                                CC.GRAY + "Displays as: " + CC.translate(this.prefixConfig.getString("Prefixes." + k + ".Prefix")),
                                CC.MENU_BAR).build();
                list.add(itemStack);
            }
        });
        return list;
    }

    public String getPermission(String name) {
        return this.prefixConfig.getString("Prefixes." + name + ".Permission");
    }

    public void save() {
        try {
            this.prefixConfig.save(this.prefixFile);
        } catch (IOException e) {
            instance.getLogger().warning("Failed to save prefix.yml stacktrace below: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
