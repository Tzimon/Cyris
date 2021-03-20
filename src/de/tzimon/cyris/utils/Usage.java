package de.tzimon.cyris.utils;

import de.tzimon.cyris.Cyris;
import org.bukkit.command.CommandSender;

public enum Usage {

    ;

    private Cyris plugin;

    Usage() {
        plugin = Cyris.getPlugin();
    }

    public void sendUsage(CommandSender sender) {
        sendTitle(sender);

        switch (this) {
            default:
                sender.sendMessage("§cKeine Hilfe verfügbar");
        }
    }

    private void sendTitle(CommandSender sender) {
        sender.sendMessage(plugin.prefix + "");
    }

    private void sendLine(CommandSender sender, String usage, String description) {
        sender.sendMessage(plugin.prefix + "§8- §b/" + usage + " §8- §7" + description);
    }

}
