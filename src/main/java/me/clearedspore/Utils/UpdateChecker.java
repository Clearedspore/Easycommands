package me.clearedspore.Utils;

import me.clearedspore.easycommands;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {

    private easycommands plugin;
    private int resourceId;

    public UpdateChecker (easycommands plugin, int resourceId){
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getLatestVersion(Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).openStream();
                 Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                } else {
                    plugin.getLogger().warning("No data retrieved from the update API.");
                }
            } catch (IOException exception) {
                plugin.getLogger().severe("Update checker is not working: " + exception.getMessage());
            }
        });
    }

}
