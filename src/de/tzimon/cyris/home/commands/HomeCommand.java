package de.tzimon.cyris.home.commands;

import de.tzimon.cyris.Cyris;
import de.tzimon.cyris.home.Home;
import de.tzimon.cyris.utils.Usage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class HomeCommand implements CommandExecutor {

    private Cyris plugin;

    public HomeCommand() {
        plugin = Cyris.getPlugin();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.prefix + plugin.noPlayer);
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            Usage.HOME.sendUsage(player);
            return true;
        }

        Home home = plugin.getHomeManager().getHome(player.getUniqueId(), args[0]);

        if (home == null) {
            player.sendMessage("§cDu hast kein Home mit dem Namen oder der ID " + args[0]);
            return true;
        }

        player.teleport(new Location(Bukkit.getWorld(home.getWorld()), home.getX(), home.getY(), home.getZ()));
        return true;
    }

}
