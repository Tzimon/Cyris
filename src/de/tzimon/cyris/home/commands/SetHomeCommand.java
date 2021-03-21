package de.tzimon.cyris.home.commands;

import de.tzimon.cyris.Cyris;
import de.tzimon.cyris.home.Home;
import de.tzimon.cyris.home.HomeManager;
import de.tzimon.cyris.utils.Usage;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class SetHomeCommand implements CommandExecutor {

    private Cyris plugin;

    public SetHomeCommand() {
        plugin = Cyris.getPlugin();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.prefix + plugin.noPlayer);
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            Usage.SET_HOME.sendUsage(player);
            return true;
        }

        Set<Home> homes = plugin.getHomeManager().getHomes(player.getUniqueId());
        String homeName = args[0];

        if (homeName.length() > HomeManager.MAX_HOME_NAME_LENGTH) {
            player.sendMessage(plugin.prefix + "§cDer Name deines Homes darf maximal " + HomeManager.MAX_HOME_NAME_LENGTH
                    + " Zeichen lang sein");
            return true;
        }

        for (Home home : homes) {
            if (home.getName().equalsIgnoreCase(homeName))
                plugin.getHomeManager().deleteHome(home);
        }

        if (plugin.getHomeManager().getHomes(player.getUniqueId()).size() >= HomeManager.MAX_HOMES) {
            player.sendMessage(plugin.prefix + "§cDu kannst maximal " + HomeManager.MAX_HOMES + " Homes haben");
            return true;
        }

        Location location = player.getLocation();
        Home home = new Home(player.getUniqueId(), plugin.getHomeManager().getNextId(player.getUniqueId()), homeName, location);
        plugin.getHomeManager().createHome(home);

        player.sendMessage(plugin.prefix + "§7Das Home mit dem Namen §6" + homeName + " §8[§e" + home.getId() +
                "§8] §7wurde erstellt");
        return true;
    }

}
