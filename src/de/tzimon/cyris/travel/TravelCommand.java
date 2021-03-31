package de.tzimon.cyris.travel;

import de.tzimon.cyris.Cyris;
import de.tzimon.cyris.utils.Usage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

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
        UUID uuid = player.getUniqueId();

        if (args.length == 0) {
            if (plugin.getTravelManager().cancel(uuid))
                player.sendMessage(plugin.prefix + "§7Dein Travelpunkt wurde gelöscht");
            else
                player.sendMessage(plugin.prefix + "§cDu hast keinen Travelpunkt");
        } else if (args.length == 2) {
            int x;
            int z;

            try {
                x = Integer.parseInt(args[0]);
            } catch (NumberFormatException ignored) {
                player.sendMessage(plugin.prefix + plugin.invalidNumber(args[0]));
                return true;
            }

            try {
                z = Integer.parseInt(args[1]);
            } catch (NumberFormatException ignored) {
                player.sendMessage(plugin.prefix + plugin.invalidNumber(args[1]));
                return true;
            }

            Destination destination = new Destination(x, z);
            plugin.getTravelManager().add(uuid, destination);
        } else
            Usage.TRAVEL.sendUsage(player);

        return true;
    }

}
