package me.clearedspore.Commands.Teleport;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Tpaccept implements CommandExecutor {
    private final TeleportManager teleportManager;
    private final JavaPlugin plugin;

    public Tpaccept(TeleportManager teleportManager, JavaPlugin plugin) {
        this.teleportManager = teleportManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        Player target = (Player) sender;
        TeleportRequest request = teleportManager.getRequest(target.getUniqueId());

        if (request == null || request.isExpired()) {
            target.sendMessage(ChatColor.RED + "No valid teleport request found.");
            return true;
        }

        Player requester = Bukkit.getPlayer(request.getRequesterId());
        if (requester == null || !requester.isOnline()) {
            target.sendMessage(ChatColor.RED + "The player who requested the teleport is no longer online.");
            teleportManager.removeRequest(target.getUniqueId());
            return true;
        }

        teleportManager.removeRequest(target.getUniqueId());
    target.sendMessage(ChatColor.BLUE + "Teleport request accepted. Teleporting " + requester.getName() + " to you.");
        requester.sendMessage(ChatColor.BLUE + "Your teleport request has been accepted. Teleporting to " + target.getName() + ".");

        // Start the teleport countdown
        TeleportListener teleportListener = new TeleportListener(teleportManager, plugin);
        teleportListener.startTeleportCountdown(requester, target);

        return true;
    }
}
