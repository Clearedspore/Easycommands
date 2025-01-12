package me.clearedspore.Commands.Teleport;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeleportListener implements Listener {
    private final TeleportManager teleportManager;
    private final JavaPlugin plugin;
    private final Map<UUID, BukkitRunnable> teleportTasks = new HashMap<>();

    public TeleportListener(TeleportManager teleportManager, JavaPlugin plugin) {
        this.teleportManager = teleportManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (teleportTasks.containsKey(playerId)) {
            if (event.getFrom().distance(event.getTo()) > 0) {
                teleportTasks.get(playerId).cancel();
                teleportTasks.remove(playerId);
                player.sendMessage(ChatColor.RED + "Teleportation cancelled due to movement.");
            }
        }
    }

    public void startTeleportCountdown(Player player, Player target) {
        UUID playerId = player.getUniqueId();

        BukkitRunnable task = new BukkitRunnable() {
            int countdown = 3;

            @Override
            public void run() {
                if (countdown > 0) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Teleporting in " + countdown + " seconds..."));
                    countdown--;
                } else {
                    player.teleport(target.getLocation());
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("Teleported!"));
                    teleportTasks.remove(playerId);
                    cancel();
                }
            }
        };

        teleportTasks.put(playerId, task);
        task.runTaskTimer(plugin, 0, 20); // Every second
    }
}
