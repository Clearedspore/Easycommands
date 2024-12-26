package me.clearedspore.Features.WarpSection;

import me.clearedspore.easycommands;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WarpManager {
    private final easycommands plugin;
    private final Map<String, Location> warps = new HashMap<>();
    private File warpsFile;
    private FileConfiguration warpsConfig;

    public WarpManager(easycommands plugin) {
        this.plugin = plugin;
        loadWarps();
    }

    private void createWarpsFile() {
        warpsFile = new File(plugin.getDataFolder(), "warps.yml");
        if (!warpsFile.exists()) {
            try {
                warpsFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Could not create warps.yml file!");
            }
        }
        warpsConfig = YamlConfiguration.loadConfiguration(warpsFile);
    }

    public void loadWarps() {
        createWarpsFile();
        warps.clear();

        if (warpsConfig.getConfigurationSection("warps") != null) {
            for (String name : warpsConfig.getConfigurationSection("warps").getKeys(false)) {
                Location loc = warpsConfig.getLocation("warps." + name);
                if (loc != null) {
                    warps.put(name, loc);
                }
            }
        }
    }

    public void saveWarps() {
        if (warpsConfig == null || warpsFile == null) return;

        warpsConfig.set("warps", null);
        for (Map.Entry<String, Location> entry : warps.entrySet()) {
            warpsConfig.set("warps." + entry.getKey(), entry.getValue());
        }

        try {
            warpsConfig.save(warpsFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save warps.yml file!");
        }
    }

    public void setWarp(String name, Location location) {
        warps.put(name, location);
        saveWarps();
    }

    public Location getWarp(String name) {
        return warps.get(name);
    }

    public void removeWarp(String name) {
        warps.remove(name);
        saveWarps();
    }

    public void clearAllWarps() {
        warps.clear();
        saveWarps();
    }

    public Set<String> getWarpNames() {
        return warps.keySet();
    }

    public void reloadWarps() {

        plugin.reloadConfig();
        FileConfiguration warpsConfig = YamlConfiguration.loadConfiguration(warpsFile);

        warps.clear();

        for (String warpName : warpsConfig.getKeys(false)) {
            Location location = warpsConfig.getLocation(warpName);
            if (location != null) {
                warps.put(warpName, location);
            }
        }
    }
}

