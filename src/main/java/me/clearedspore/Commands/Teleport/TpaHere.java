package me.clearedspore.Commands.Teleport;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TpaHere implements CommandExecutor {
    private final TeleportManager teleportManager;
    private final JavaPlugin plugin;

    public TpaHere(TeleportManager teleportManager, JavaPlugin plugin) {
        this.teleportManager = teleportManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        Player requester = (Player) sender;
        if (args.length != 1) {
            requester.sendMessage(ChatColor.RED + "Usage: /tpahere <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            requester.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        if (teleportManager.isOnCooldown(requester.getUniqueId())) {
            requester.sendMessage(ChatColor.RED + "You must wait before sending another request.");
            return true;
        }

        teleportManager.addRequest(target.getUniqueId(), requester.getUniqueId());
        target.sendMessage(ChatColor.BLUE + requester.getName() + " has requested you to teleport to them. Type /tpaccept to accept.");
        requester.sendMessage(ChatColor.BLUE + "Teleport request sent to " + target.getName() + ".");

        new BukkitRunnable() {
            @Override
            public void run() {
                if (teleportManager.getRequest(target.getUniqueId()) != null &&
                        teleportManager.getRequest(target.getUniqueId()).isExpired()) {
                    teleportManager.removeRequest(target.getUniqueId());
                    requester.sendMessage(ChatColor.BLUE + "Your teleport request to " + target.getName() + " has expired.");
                }
            }
        }.runTaskLater(plugin, 1200);

        teleportManager.setCooldown(requester.getUniqueId());
        return true;
    }
}
