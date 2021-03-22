package de.tzimon.cyris.home;

import de.tzimon.cyris.Cyris;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class HomeManager {

    public static final int MAX_HOMES = 20;
    public static final int MAX_HOME_NAME_LENGTH = 50;

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
                        "`owner_uuid` VARCHAR(36) NOT NULL, " +
                        "`id` INT(2) UNSIGNED NOT NULL, " +
                        "`name` VARCHAR(" + MAX_HOME_NAME_LENGTH + ") NULL, " +
                        "`world_uuid` VARCHAR(36) NOT NULL, " +
                        "`x` DOUBLE NOT NULL, " +
                        "`y` DOUBLE NOT NULL, " +
                        "`z` DOUBLE NOT NULL, " +
                        "`yaw` FLOAT NOT NULL, " +
                        "`pitch` FLOAT NOT NULL, " +
                        "PRIMARY KEY(`owner_uuid`, `id`));");

        ResultSet homeSet = plugin.getSqlManager().executeQuery(connection, "SELECT * FROM `cyris`.`home`;");

        if (homeSet == null)
            return;

        try {
            while (homeSet.next()) {
                Home home = new Home(UUID.fromString(homeSet.getString("owner_uuid")), homeSet.getInt("id"),
                        homeSet.getString("name"), new Location(Bukkit.getWorld(UUID.fromString(homeSet.getString("world_uuid"))),
                        homeSet.getDouble("x"), homeSet.getDouble("y"), homeSet.getDouble("z"), homeSet.getFloat("yaw"),
                        homeSet.getFloat("pitch")));

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

        homes.forEach(home -> plugin.getSqlManager().executeUpdate(connection, "INSERT INTO `cyris`.`home` VALUES ('"
                + home.getOwner().toString() + "', " + home.getId() + ", '" + home.getName() + "', '" + home.getLocation()
            .getWorld().getUID() + "', " + home.getLocation().getX() + ", " + home.getLocation().getY() + ", " +
            home.getLocation().getZ() + ", " + home.getLocation().getYaw() + ", " + home.getLocation().getPitch() +
            ");"));
    }

    public Set<Home> getHomes(UUID uuid) {
        Set<Home> homes = new HashSet<>();

        this.homes.forEach(home -> {
            if (home.getOwner().equals(uuid))
                homes.add(home);
        });

        return homes;
    }

    public List<Home> getHomesSorted(UUID uuid) {
        return getHomes(uuid).stream().sorted(Comparator.comparingInt(Home::getId)).collect(Collectors.toList());
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
        int id = 1;

        for (Home home : getHomesSorted(uuid)) {
            if (home.getId() > id)
                break;

            id++;
        }

        return id;
    }

}
