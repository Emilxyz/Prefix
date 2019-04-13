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

        String USAGE = CC.createUsage(label, "[add | remove] <name> <prefix>");

        if(!(sender instanceof Player)) {
            sender.sendMessage(instance.getMessages().get("Player-Only"));
            return true;
        }

        Player player = (Player) sender;

        if(args.length == 0) {
            //TODO: prefix inventory
            return true;
        }
        if(args.length <= 2) {
            player.sendMessage(USAGE);
            return true;
        }

        if(!(player.hasPermission("prefix.add"))) {
            player.sendMessage(instance.getMessages().get("No-Permissions"));
            return true;
        }

        String prefix = StringUtils.join(args, " ", 2, args.length);
        String name = args[1];

        instance.getPrefixManager().addPrefix(name, prefix);
        player.sendMessage(instance.getMessages().get("Prefix.Add")
                .replaceAll("%prefix%", CC.translate(prefix))
                .replaceAll("%name%", name));

        return true;
    }
}