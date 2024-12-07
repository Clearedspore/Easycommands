package me.clearedspore.Listeners;

import me.clearedspore.Files.Messages;
import me.clearedspore.easycommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener {

    public JoinLeaveListener(easycommands easycommands) {
    }

    @EventHandler
    public void OnJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(p.hasPermission("easycommands.staff")) {
            for (Player online : Bukkit.getOnlinePlayers()) {

                if (online.hasPermission("easycommands.staff")) {
                    String joinMessage = Messages.get().getString("StaffJoin");
                    joinMessage = joinMessage.replace("%player%", e.getPlayer().getDisplayName());
                    online.sendMessage(ChatColor.translateAlternateColorCodes('&', joinMessage));
                }
            }
        } else return;
    }
    @EventHandler
    public void OnLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if(p.hasPermission("easycommands.staff")) {
            for (Player online : Bukkit.getOnlinePlayers()) {

                if (online.hasPermission("easycommands.staff")) {
                    String leaveMessage = Messages.get().getString("StaffLeave");
                    leaveMessage = leaveMessage.replace("%player%", e.getPlayer().getDisplayName());
                    online.sendMessage(ChatColor.translateAlternateColorCodes('&', leaveMessage));
                }
            }
        } else return;
    }
}
