package de.tzimon.cyris.home;

import java.util.UUID;

public class Home {

    private UUID owner;
    private int id;
    private String name;
    private int x;
    private int y;
    private int z;
    private UUID world;

    public Home(UUID owner, int id, String name, int x, int y, int z, UUID world) {
        this.owner = owner;
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public UUID getWorld() {
        return world;
    }

}
