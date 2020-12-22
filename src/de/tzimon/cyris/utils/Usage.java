package de.tzimon.cyris.utils;

import de.tzimon.cyris.Cyris;
import org.bukkit.command.CommandSender;

public enum Usage {

    TRAVEL;

    private Cyris plugin;

    Usage() {
        plugin = Cyris.getPlugin();
    }

    public void sendUsage(CommandSender sender) {
        sendTitle(sender);

        switch (this) {
            case TRAVEL:
                sendLine(sender, "travel <X> <Z>", "Zeigt den direkten Weg zum Ziel");
                break;
        }
    }

    private void sendTitle(CommandSender sender) {
        sender.sendMessage(plugin.prefix + "");
    }

    private void sendLine(CommandSender sender, String usage, String description) {
        sender.sendMessage(plugin.prefix + "§8- §b/" + usage + " §8- §7" + description);
    }

}
