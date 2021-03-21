package de.tzimon.cyris.utils;

import org.bukkit.Bukkit;

public class Logger {

    public static void info(String log) {
        Bukkit.getConsoleSender().sendMessage(log);
    }

    public static void error(String error) {
        info("§c" + error);
    }

}
