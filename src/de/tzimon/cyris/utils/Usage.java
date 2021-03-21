package de.tzimon.cyris.utils;

import de.tzimon.cyris.Cyris;
import org.bukkit.command.CommandSender;

public enum Usage {

    HOME,
    SET_HOME;

    private Cyris plugin;

    Usage() {
        plugin = Cyris.getPlugin();
    }

    public void sendUsage(CommandSender sender) {
        sendTitle(sender);

        switch (this) {
            case HOME:
                sendLine(sender, "home <HomeName|HomeID>", "Teleports you to one of your homes");
                break;
            case SET_HOME:
                sendLine(sender, "sethome <HomeName>", "Creates a home or replaces an existing one");
                break;
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
