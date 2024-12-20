package me.clearedspore.Listeners;

import me.clearedspore.Utils.UpdateChecker;
import me.clearedspore.easycommands;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerJoinListener implements Listener {
    private final UpdateChecker updateChecker;
    private final boolean updateCheckerEnabled;


    public PlayerJoinListener(UpdateChecker updateChecker, JavaPlugin plugin) {
        this.updateChecker = updateChecker;
        this.updateCheckerEnabled = plugin.getConfig().getBoolean("updatechecker", true);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (updateCheckerEnabled) {
            if (updateChecker.isUpdateAvailable()) {
                if (event.getPlayer().hasPermission("easycommands.update")) {
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lEasyCommands WARNING!"));
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&aA new version of the plugin is available: §e" + updateChecker.getLatestVersion()));
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "§aDownload it at: §ehttps://www.spigotmc.org/resources/" + updateChecker.getResourceId()));
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
                    return;
                }
            }
        }
    }
}

