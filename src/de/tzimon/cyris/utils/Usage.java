package de.tzimon.cyris.utils;

import de.tzimon.cyris.Cyris;
import org.bukkit.command.CommandSender;

public enum Usage {

    HOME,
    SET_HOME,
    DELETE_HOME,
    TRAVEL;

    private Cyris plugin;

    Usage() {
        plugin = Cyris.getPlugin();
    }

    public void sendUsage(CommandSender sender) {
        sendTitle(sender);

        switch (this) {
            case HOME:
                sendLine(sender, "home <HomeName | HomeID>", "Teleportiert dich zu einem deiner Homes");
                break;
            case SET_HOME:
                sendLine(sender, "sethome <HomeName>", "Erstellt oder ersetzt ein Home");
                break;
            case DELETE_HOME:
                sendLine(sender, "deletehome <HomeName>", "Löscht ein vorhandenes Home");
                break;
            case TRAVEL:
                sendLine(sender, "travel", "Löscht den aktuellen Travelpunkt");
                sendLine(sender, "travel <X> <Z>", "Erstellt einen neuen Travelpunkt");
                break;
            default:
                sender.sendMessage("§cKeine Hilfe verfügbar");
        }
    }

    private void sendTitle(CommandSender sender) {
        sender.sendMessage(plugin.prefix + "§6§lHilfe:");
    }

    private void sendLine(CommandSender sender, String usage, String description) {
        sender.sendMessage(plugin.prefix + "§8- §b/" + usage + " §8- §7" + description);
    }

}
