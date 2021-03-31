package de.tzimon.cyris.travel;

import de.tzimon.cyris.Cyris;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class TravelManager {

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

    }

}
