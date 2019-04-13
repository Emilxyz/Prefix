package de.langgezockt.Prefix.commands;

import de.langgezockt.Prefix.Prefix;
import de.langgezockt.Prefix.utils.CC;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;


/**
 * @author langgezockt (langgezockt@gmail.com)
 * 13.04.2019 / 06:02
 * Prefix / de.langgezockt.Prefix.commands
 */

public class PrefixCommand implements CommandExecutor {

    private Prefix instance;

    public PrefixCommand(Prefix instance) {
        this.instance = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String USAGE = CC.createUsage(label, "[reload | add | remove] <name> <prefix>");

        if (!(sender instanceof Player)) {
            sender.sendMessage(instance.getMessages().get("Player-Only"));
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            instance.getPrefixManager().getPrefixInventory().createInventories(player);
            instance.getPrefixManager().getPrefixInventory().openFirstPage(player);
            return true;
        }

        if (args.length == 1) {
            if (!(args[0].equalsIgnoreCase("reload"))) {
                player.sendMessage(USAGE);
                return true;
            }

            if (!(player.hasPermission("prefix.reload"))) {
                player.sendMessage(instance.getMessages().get("No-Permissions"));
                return true;
            }

            instance.getPrefixManager().loadPrefixes();
            player.sendMessage(instance.getMessages().get("Prefix.Reload"));
        }

        if (args.length == 2) {
            if (!(args[0].equalsIgnoreCase("remove"))) {
                player.sendMessage(USAGE);
                return true;
            }

            if (!(player.hasPermission("prefix.remove"))) {
                player.sendMessage(instance.getMessages().get("No-Permissions"));
                return true;
            }

            String name = args[1];

            if(!(instance.getPrefixManager().getPrefixes().containsKey(name))) {
                player.sendMessage(instance.getMessages().get("Prefix.Not-Exists"));
                return true;
            }

            instance.getPrefixManager().removePrefix(name);
            player.sendMessage(instance.getMessages().get("Prefix.Remove").replaceAll("%name%", name));
            return true;
        }

        if (args.length < 3) {
            player.sendMessage(USAGE);
            return true;
        }

        if (!(args[0].equalsIgnoreCase("add"))) {
            player.sendMessage(USAGE);
            return true;
        }

        if (!(player.hasPermission("prefix.add"))) {
            player.sendMessage(instance.getMessages().get("No-Permissions"));
            return true;
        }

        String prefix = StringUtils.join(args, " ", 2, args.length);
        String name = args[1];

        if(instance.getPrefixManager().getPrefixes().containsKey(name)) {
            player.sendMessage(instance.getMessages().get("Prefix.Exists"));
            return true;
        }

        instance.getPrefixManager().addPrefix(name, prefix);
        player.sendMessage(instance.getMessages().get("Prefix.Add")
                .replaceAll("%prefix%", CC.translate(prefix))
                .replaceAll("%name%", name));

        return true;
    }
}