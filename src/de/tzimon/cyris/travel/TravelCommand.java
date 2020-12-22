package de.tzimon.cyris.travel;

import de.tzimon.cyris.Cyris;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TravelCommand implements CommandExecutor {

    private Cyris plugin;

    public TravelCommand() {
        plugin = Cyris.getPlugin();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.prefix + plugin.noPlayer);
            return true;
        }

        Player player = (Player) sender;

        return true;
    }

}
