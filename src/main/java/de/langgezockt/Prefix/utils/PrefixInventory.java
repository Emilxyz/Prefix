package de.langgezockt.Prefix.utils;

import de.langgezockt.Prefix.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author langgezockt (langgezockt@gmail.com)
 * 13.04.2019 / 06:43
 * Prefix / de.langgezockt.Prefix.utils
 */

public class PrefixInventory {


    private Prefix instance;
    private HashMap<Player, Integer> currentPage;
    private List<Inventory> inventories;
    private int neededInventories;

    public PrefixInventory(Prefix instance) {
        this.instance = instance;
        currentPage = new HashMap<>();
    }

    public void createInventories(Player player) {
        inventories = new ArrayList<>();
        int inventorySize = 27;
        int index = 0;
        int pageIndex = 0;
        int pageIndex2 = 1;
        List<ItemStack> items = instance.getPrefixManager().getItems(player);
        int a = items.size();
        int b = inventorySize;
        neededInventories = a / b + ((a % b == 0) ? 0 : 1);
        if (neededInventories == 0) {
            neededInventories = 1;
        }
        for (int i = 0; i < neededInventories; i++) {
            Inventory inventory = Bukkit.createInventory(null, 36, CC.RED + CC.BOLD + "Prefixes");
            inventories.add(inventory);
        }
        for (ItemStack itemStack : items) {
            Inventory inventory;
            if (pageIndex != neededInventories) {
                inventory = inventories.get(pageIndex);
                if (index <= 26) {
                    inventory.setItem(index, itemStack);
                    index++;
                } else {
                    inventory = inventories.get(pageIndex + 1);
                    pageIndex++;
                    index = 0;
                    inventory.setItem(index, itemStack);
                    index++;
                }
            }
        }
        for (Inventory inventory : inventories) {
            inventory.setItem(27, new ItemBuilder(Material.CARPET).setDisplayName(CC.DRED + "Previous Page").build());
            inventory.setItem(31, new ItemBuilder(Material.PAPER).setDisplayName(CC.DRED + "Page " +
                    CC.GRAY + "[" + pageIndex2+ "/" + neededInventories + "]").build());
            inventory.setItem(35, new ItemBuilder(Material.CARPET).setDisplayName(CC.DRED + "Next Page").build());
            pageIndex2++;
        }
    }

    public void openFirstPage(Player player) {
        player.closeInventory();
        currentPage.put(player, 0);
        player.openInventory(inventories.get(0));
    }

    public void openNextPage(Player player) {
        if(currentPage.get(player) >= neededInventories -1) {
            String message = instance.getMessages().get("Page.Last");
            player.sendMessage(message);
        } else {
            player.closeInventory();
            currentPage.put(player, currentPage.get(player)+1);
            player.openInventory(inventories.get(currentPage.get(player)));
        }
    }

    public void openPreviousPage(Player player) {
        if(currentPage.get(player) == 0) {
            String message = instance.getMessages().get("Page.First");
            player.sendMessage(message);
        } else {
            player.closeInventory();
            currentPage.put(player, currentPage.get(player)-1);
            player.openInventory(inventories.get(currentPage.get(player)));
        }
    }

}
