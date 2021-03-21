package de.tzimon.cyris.home;

import org.bukkit.Location;

import java.util.UUID;

public class Home {

    private UUID owner;
    private int id;
    private String name;
    private Location location;

    public Home(UUID owner, int id, String name, Location location) {
        this.owner = owner;
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public UUID getOwner() {
        return owner;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

}
