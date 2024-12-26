package me.clearedspore.Features.WarpSection;

import me.clearedspore.Features.Logs.LogManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Delwarp implements CommandExecutor {
    private final WarpManager warpManager;

    public Delwarp(WarpManager warpManager) {
        this.warpManager = warpManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("You can only remove a warp ingame!");
            return true;
        }

        if (args.length != 1) {
            p.sendMessage(ChatColor.RED + "ERROR: Usage: /delwarp (name)");
            return true;
        }

        String warpName = args[0];
        LogManager.log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has deleted the " + warpName + " warp");
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission("easycommands.logs")) {
                online.sendMessage(ChatColor.GRAY + "[" + p.getName() + " has deleted the " + warpName + " warp]");
            }
            warpManager.removeWarp(warpName);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9Removed &f" + warpName + " &9warp"));
        }
        return true;
    }
}
