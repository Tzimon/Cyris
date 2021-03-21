package de.tzimon.cyris.home.commands;

import de.tzimon.cyris.Cyris;
import de.tzimon.cyris.home.Home;
import de.tzimon.cyris.utils.Usage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeleteHomeCommand implements CommandExecutor {

    private Cyris plugin;

    public DeleteHomeCommand() {
        plugin = Cyris.getPlugin();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.prefix + plugin.noPlayer);
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            Usage.DELETE_HOME.sendUsage(player);
            return true;
        }

        Home home = plugin.getHomeManager().getHome(player.getUniqueId(), args[0]);

        if (home == null) {
            player.sendMessage(plugin.prefix + "§cDu hast kein Home mit dem Namen oder der ID " + args[0]);
            return true;
        }

        plugin.getHomeManager().deleteHome(home);
        player.sendMessage(plugin.prefix + "§7Dein Home mit dem Namen §6" + home.getName() + " §8[§e" + home.getId() +
                "§8] §7wurde gelöscht");
        return true;
    }

}
