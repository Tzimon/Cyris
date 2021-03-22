package de.tzimon.cyris.home.commands;

import de.tzimon.cyris.Cyris;
import de.tzimon.cyris.home.Home;
import de.tzimon.cyris.home.HomeManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class HomesCommand implements CommandExecutor {

    private Cyris plugin;

    public HomesCommand() {
        plugin = Cyris.getPlugin();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.prefix + plugin.noPlayer);
            return true;
        }

        Player player = (Player) sender;
        List<Home> homes = plugin.getHomeManager().getHomesSorted(player.getUniqueId());

        if (homes.isEmpty()) {
            player.sendMessage(plugin.prefix + "§cDu hast keine Homes");
            return true;
        }

        player.sendMessage(plugin.prefix + "§6§lHomes §8(§6" + homes.size() + "§7/§6" + HomeManager.MAX_HOMES + "§8)§6§l:");

        homes.forEach(home -> {
            Location location = home.getLocation();
            player.sendMessage(plugin.prefix + "§8- §b" + home.getName() + " §8[§e" + home.getId() + "§8] (§7" +
                    location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ() + "§8)");
        });

        return true;
    }

}
