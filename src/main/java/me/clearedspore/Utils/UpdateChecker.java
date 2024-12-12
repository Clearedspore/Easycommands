package me.clearedspore.Utils;

import me.clearedspore.easycommands;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {
    private final easycommands plugin;
    private final int resourceId;
    private boolean updateAvailable = false;
    private String latestVersion;

    public UpdateChecker(easycommands plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void checkForUpdates() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(
                        "https://api.spigotmc.org/simple/0.1/index.php?action=getResource&id=" + resourceId
                ).openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.readLine();
                reader.close();

                // Check if response is valid
                if (response != null && !response.isEmpty()) {
                    // Parse the response JSON
                    JSONObject jsonResponse = new JSONObject(response);
                    latestVersion = jsonResponse.getString("current_version");

                    String currentVersion = plugin.getDescription().getVersion();

                    // Log the versions
                    plugin.getLogger().info("Current Version: " + currentVersion);
                    plugin.getLogger().info("Latest Version: " + latestVersion);

                    // Compare versions
                    if (isVersionNewer(latestVersion, currentVersion)) {
                        updateAvailable = true;
                    } else {
                        updateAvailable = false;
                    }
                } else {
                    plugin.getLogger().warning("No response received from SpigotMC.");
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Error checking for updates: " + e.getMessage());
            }
        });
    }

    private boolean isVersionNewer(String latest, String current) {
        try {
            String[] latestParts = latest.split("\\.");
            String[] currentParts = current.split("\\.");

            for (int i = 0; i < Math.max(latestParts.length, currentParts.length); i++) {
                int latestPart = i < latestParts.length ? Integer.parseInt(latestParts[i]) : 0;
                int currentPart = i < currentParts.length ? Integer.parseInt(currentParts[i]) : 0;

                if (latestPart > currentPart) return true;
                if (latestPart < currentPart) return false;
            }
        } catch (NumberFormatException e) {
            plugin.getLogger().warning("Invalid version format: " + latest + " or " + current);
        }
        return false;
    }

    public boolean isUpdateAvailable() {
        return updateAvailable;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public int getResourceId() {
        return resourceId;
    }

    public easycommands getPlugin() {
        return plugin;
    }
}