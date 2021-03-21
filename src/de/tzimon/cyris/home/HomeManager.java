package de.tzimon.cyris.home;

import de.tzimon.cyris.Cyris;
import de.tzimon.cyris.utils.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HomeManager {

    public static final int MAX_HOMES = 20;

    private Cyris plugin;

    private Set<Home> homes;

    public HomeManager() {
        plugin = Cyris.getPlugin();

        homes = new HashSet<>();

        Connection connection = plugin.getSqlManager().connect();

        if (connection == null)
            return;

        plugin.getSqlManager().executeUpdate(connection,
                "CREATE TABLE IF NOT EXISTS `cyris`.`home` (" +
                        "`id` INT(2) UNSIGNED NOT NULL, " +
                        "`owner_uuid` VARCHAR(36) NOT NULL, " +
                        "`name` VARCHAR(50) NULL, " +
                        "`x` INT NOT NULL, " +
                        "`y` INT NOT NULL, " +
                        "`z` INT NOT NULL, " +
                        "`world` VARCHAR(36) NOT NULL, " +
                        "PRIMARY KEY(`id`));");

        ResultSet homeSet = plugin.getSqlManager().executeQuery(connection, "SELECT * FROM `cyris`.`home`;");

        if (homeSet == null)
            return;

        try {
            while (homeSet.next()) {
                Home home = new Home(
                        UUID.fromString(homeSet.getString("owner_uuid")),
                        homeSet.getInt("id"),
                        homeSet.getString("name"),
                        homeSet.getInt("x"),
                        homeSet.getInt("y"),
                        homeSet.getInt("z"),
                        UUID.fromString(homeSet.getString("world")));

                homes.add(home);
            }

            connection.close();
        } catch (SQLException ignored) {}
    }

    public void save() {
        Connection connection = plugin.getSqlManager().connect();

        if (connection == null)
            return;

        plugin.getSqlManager().executeUpdate(connection, "TRUNCATE `cyris`.`home`;");

        homes.forEach(home -> plugin.getSqlManager().executeUpdate(connection, "INSERT INTO `cyris`.`home` VALUES (" +
                home.getId() + ", '" + home.getOwner().toString() + "', '" + home.getName() + "', " + home.getX() + ", " +
                home.getY() + ", " + home.getZ() + ", '" + home.getWorld().toString() + "');"));
    }

    public Set<Home> getHomes(UUID uuid) {
        Set<Home> homes = new HashSet<>();

        this.homes.forEach(home -> {
            if (home.getOwner().equals(uuid))
                homes.add(home);
        });

        return homes;
    }

    public Home getHome(UUID uuid, String homeName) {
        Set<Home> homes = getHomes(uuid);

        try {
            int id = Integer.parseInt(homeName);

            for (Home home : homes) {
                if (id == home.getId())
                    return home;
            }
        } catch (NumberFormatException ignored) {}

        for (Home home : homes) {
            if (homeName.equalsIgnoreCase(home.getName()))
                return home;
        }

        return null;
    }

    public void createHome(Home home) {
        homes.add(home);
    }

    public void deleteHome(Home home) {
        homes.remove(home);
    }

    public int getNextId(UUID uuid) {
        Stream<Home> homes = getHomes(uuid).stream().sorted(Comparator.comparingInt(Home::getId));
        int id = 0;

        for (Home home : homes.collect(Collectors.toSet())) {
            if (home.getId() > id)
                break;

            id++;
        }

        return id;
    }

}
