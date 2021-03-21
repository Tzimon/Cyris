package de.tzimon.cyris;

import de.tzimon.cyris.home.HomeManager;
import de.tzimon.cyris.home.commands.HomeCommand;
import de.tzimon.cyris.home.commands.SetHomeCommand;
import de.tzimon.cyris.utils.SqlManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Cyris extends JavaPlugin {

    private static Cyris plugin;

    public String prefix = "§cCyris §8| §r";
    public String noPlayer = "§cDu musst ein Spieler sein";
    public String noPermission = "§cDu hast keine Berechtigung das zu tun";
    public String invalidNumber(String s) { return "§c" + s + " ist keine gültige Zahl"; }

    private SqlManager sqlManager;
    private HomeManager homeManager;

    public Cyris() {
        plugin = this;
    }

    public void onEnable() {
        loadConfig();
        loadCommands();
        loadListeners();

        sqlManager = new SqlManager();
        homeManager = new HomeManager();
    }

    public void onDisable() {
        homeManager.save();
    }

    private void loadConfig() {
        getConfig().addDefault("sql.hostname", "localhost");
        getConfig().addDefault("sql.port", 3306);
        getConfig().addDefault("sql.username", "root");
        getConfig().addDefault("sql.password", "");

        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void loadCommands() {
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("sethome").setExecutor(new SetHomeCommand());
    }

    private void loadListeners() {
    }

    public static Cyris getPlugin() {
        return plugin;
    }

    public SqlManager getSqlManager() {
        return sqlManager;
    }

    public HomeManager getHomeManager() {
        return homeManager;
    }

}
