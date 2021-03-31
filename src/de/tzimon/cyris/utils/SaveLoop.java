package de.tzimon.cyris.utils;

import de.tzimon.cyris.Cyris;
import org.bukkit.Bukkit;

import java.util.HashSet;
import java.util.Set;

public class SaveLoop {

    public static final int SAVE_FREQUENCY = 30;

    private final Set<Savable> savables;

    public SaveLoop() {
        savables = new HashSet<>();
    }

    public void start() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Cyris.getPlugin(), () -> new Thread(this::saveAll).start(),
                20 * 60 * SAVE_FREQUENCY, 20 * 60 * SAVE_FREQUENCY);
    }

    public void saveAll() {
        savables.forEach(Savable::save);
    }

    public void addSavable(Savable savable) {
        savables.add(savable);
    }

}
