package me.clearedspore.Listeners;

import me.clearedspore.easycommands;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdateJoin implements Listener {
    private final easycommands plugin;

    public UpdateJoin(easycommands plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String latestVersion = plugin.getLatestVersion();
       if(p.hasPermission("easycommands.updatechecker")) {
           if (latestVersion == null) {
               return;
           } else if (!plugin.getDescription().getVersion().equalsIgnoreCase(latestVersion)) {
               p.sendMessage(ChatColor.RED + "");
               p.sendMessage(ChatColor.RED + "=========================================");
               p.sendMessage(ChatColor.RED + "WARNING!");
               p.sendMessage(ChatColor.RED + "EasyCommands is not up to date!");
               p.sendMessage(ChatColor.RED + "Please download the latest version");
               p.sendMessage(ChatColor.RED + "in the latest versions are always updates/bug fixes");
               p.sendMessage(ChatColor.RED + "Latest version: " + latestVersion);
               p.sendMessage(ChatColor.RED + "=========================================");
               p.sendMessage(ChatColor.RED + "");
           }
           return;
       } else {
           return;
       }
    }
}
