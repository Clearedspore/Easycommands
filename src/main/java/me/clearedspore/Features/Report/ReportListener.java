package me.clearedspore.Features.Report;

import me.clearedspore.easycommands;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class ReportListener implements Listener {
    private final ReportManager reportManager;
    private final easycommands plugin;

    public ReportListener(ReportManager reportManager) {
        this.reportManager = reportManager;
        this.plugin = reportManager.getPlugin();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        List<String> notifications = reportManager.getPendingNotifications(p.getName());
        for (String message : notifications) {
            p.sendMessage(message);
        }
        Bukkit.getScheduler().runTaskLater(reportManager.getPlugin(), () -> {
            for (String message : notifications) {
                p.sendMessage(message);
            }
            reportManager.clearPendingNotifications(p.getName());
        }, 100L);
    }
}
