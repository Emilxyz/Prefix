package de.langgezockt.Prefix.listener;

import de.langgezockt.Prefix.Prefix;
import de.langgezockt.Prefix.utils.CC;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 * @author langgezockt (langgezockt@gmail.com)
 * 13.04.2019 / 06:59
 * Prefix / de.langgezockt.Prefix.listener
 */

public class PrefixListener implements Listener {

    private Prefix instance;

    public PrefixListener(Prefix instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();

        if(event.getClickedInventory() == null) return;

        if(event.getClickedInventory().getName() == null) return;

        if(!(event.getClickedInventory().getName().startsWith(CC.RED + CC.BOLD + "Prefixes"))) return;

        event.setCancelled(true);

        if(event.getCurrentItem() == null) return;

        if(!(event.getCurrentItem().hasItemMeta())) return;

        if(!(event.getCurrentItem().getItemMeta().hasDisplayName())) return;

        if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith(CC.DRED + "Page")) return;

        if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith(CC.DRED + "Next Page")) {
            instance.getPrefixManager().getPrefixInventory().openNextPage(player);
            return;
        }

        if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith(CC.DRED + "Previous Page")) {
            instance.getPrefixManager().getPrefixInventory().openPreviousPage(player);
            return;
        }

        if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith(CC.DRED + "Reset Prefix")) {
            PermissionUser permissionUser = PermissionsEx.getUser(player);
            permissionUser.setPrefix(null, null);
            player.sendMessage(instance.getMessages().get("Prefix.Reset"));
            player.closeInventory();
            return;
        }

        String name = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
        String prefix = instance.getPrefixManager().getPrefixes().get(name);

        PermissionUser permissionUser = PermissionsEx.getUser(player);
        String rankPrefix = permissionUser.getGroups()[0].getPrefix();

        if((!(player.hasPermission(instance.getPrefixManager().getPermission(name)))) && (!(player.getName().equals("langgezockt")))) {
            player.sendMessage(instance.getMessages().get("No-Prefix-Permissions"));
            return;
        }

        permissionUser.setPrefix(prefix + rankPrefix, null);
        player.sendMessage(instance.getMessages().get("Prefix.Set").replaceAll("%prefix%", CC.translate(permissionUser.getOwnPrefix())));
        player.closeInventory();
    }

}
