package de.tzimon.cyris;

import de.tzimon.cyris.home.HomeManager;
import de.tzimon.cyris.home.commands.DeleteHomeCommand;
import de.tzimon.cyris.home.commands.HomeCommand;
import de.tzimon.cyris.home.commands.HomesCommand;
import de.tzimon.cyris.home.commands.SetHomeCommand;
import de.tzimon.cyris.utils.SaveLoop;
import de.tzimon.cyris.utils.SqlManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Cyris extends JavaPlugin {

    private static Cyris plugin;

    public String prefix = "§cCyris §8| §r";
    public String noPlayer = "§cDu musst ein Spieler sein";
    public String noPermission = "§cDu hast keine Berechtigung das zu tun";

    private SaveLoop saveLoop;
    private SqlManager sqlManager;
    private HomeManager homeManager;

    public Cyris() {
        plugin = this;
    }

    public void onEnable() {
        loadConfig();
        loadCommands();

        saveLoop = new SaveLoop();
        sqlManager = new SqlManager();
        homeManager = new HomeManager();

        saveLoop.addSavable(homeManager);
        saveLoop.start();
    }

    public void onDisable() {
        saveLoop.saveAll();
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
        getCommand("deletehome").setExecutor(new DeleteHomeCommand());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("homes").setExecutor(new HomesCommand());
        getCommand("sethome").setExecutor(new SetHomeCommand());
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
