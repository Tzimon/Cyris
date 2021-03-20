package de.tzimon.cyris;

import org.bukkit.plugin.java.JavaPlugin;

public class Cyris extends JavaPlugin {

    private static Cyris plugin;

    public String prefix = "§cCyris §8| §r";
    public String noPlayer = "§cDu musst ein Spieler sein";
    public String noPermission = "§cDu hast keine Berechtigung das zu tun";
    public String invalidNumber(String s) { return "§c" + s + " ist keine gültige Zahl"; }

    public Cyris() {
        plugin = this;
    }

    public void onEnable() {
        loadCommands();
    }

    private void loadCommands() {

    }

    private void loadListeners() {

    }

    public static Cyris getPlugin() {
        return plugin;
    }

}
