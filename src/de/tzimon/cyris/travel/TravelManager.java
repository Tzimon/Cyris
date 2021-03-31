package de.tzimon.cyris.travel;

import de.tzimon.cyris.Cyris;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;

public class TravelManager {

    private static final Particle PARTICLE_TYPE = Particle.CRIT_MAGIC;
    private static final float PARTICLE_INTERSPACE = .25f;
    private static final float PARTICLE_LENGTH = 3f;
    private static final float PARTICLE_HEIGHT_OFFSET = 1.5f;
    private static final int GOAL_THRESHOLD = 15;

    private final Map<UUID, Destination> destinations;

    public TravelManager() {
        destinations = new HashMap<>();
    }

    public void start() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Cyris.getPlugin(), () -> {
            Set<UUID> remove = new HashSet<>();

            destinations.forEach((uuid, destination) -> {
                Player player = Bukkit.getPlayer(uuid);

                if (player == null || !player.isOnline()) {
                    remove.add(uuid);
                    return;
                }

                display(player, destination);
            });

            remove.forEach(destinations::remove);
        }, 0, 1);
    }

    public void add(UUID uuid, Destination destination) {
        destinations.put(uuid, destination);
    }

    public boolean cancel(UUID uuid) {
        if (!destinations.containsKey(uuid))
            return false;

        destinations.remove(uuid);
        return true;
    }

    private void display(Player player, Destination destination) {
        int particleCount = (int) Math.floor(PARTICLE_LENGTH / PARTICLE_INTERSPACE);

        Location location = player.getLocation().clone();
        Vector difference = new Vector(destination.getX() - location.getBlockX(), 0, destination.getZ() - location.getBlockZ());
        Vector vector = difference.clone().normalize().multiply(PARTICLE_INTERSPACE).setY(PARTICLE_HEIGHT_OFFSET / particleCount);

        for (int i = 0; i < particleCount; i++) {
            player.spawnParticle(PARTICLE_TYPE, location.add(vector), 1, 0, 0, 0, 0);
        }

        player.sendTitle("", "§7Distanz zum Ziel: §6" + (int) Math.floor(difference.length()) + " §8(§6" +
                Math.abs(difference.getBlockX()) + " §7/ §6" + Math.abs(difference.getBlockZ()) + "§8)", 0, 20, 20);

        if (difference.lengthSquared() <= GOAL_THRESHOLD * GOAL_THRESHOLD) {
            cancel(player.getUniqueId());
            player.sendMessage(Cyris.getPlugin().prefix + "§7Du hast dein Ziel erreicht");
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
        }
    }

}
