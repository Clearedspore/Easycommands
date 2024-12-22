package me.clearedspore.MaintenanceSection;

import me.clearedspore.easycommands;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class MaintenanceJoinListener implements Listener {

    private final easycommands plugin;
    private final MaintenanceManager maintenanceManager;

    public MaintenanceJoinListener(easycommands plugin, MaintenanceManager maintenanceManager) {
        this.plugin = plugin;
        this.maintenanceManager = maintenanceManager;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (!maintenanceManager.isMaintenanceEnabled()) return;

        String playerName = event.getPlayer().getName();
        if (!maintenanceManager.isWhitelisted(playerName)) {
            String deniedMessage = String.join("\n", plugin.getConfig().getStringList("join-denied-message"));
            deniedMessage = ChatColor.translateAlternateColorCodes('&', deniedMessage);
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, deniedMessage);
        }
    }
}
