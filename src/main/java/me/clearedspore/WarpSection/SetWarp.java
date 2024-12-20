package me.clearedspore.WarpSection;

import me.clearedspore.ConfigFiles.Messages;
import me.clearedspore.Logs.LogManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarp implements CommandExecutor {

    private final WarpManager warpManager;

    public SetWarp(WarpManager warpManager) {
        this.warpManager = warpManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can set warps!");
            return true;
        }


        Player p = (Player) sender;
        if (args.length != 1) {
            p.sendMessage("Usage: /setwarp <name>");
            return true;
        }

        String warpName = args[0];
        LogManager.log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has created the " + warpName + " warp");
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission("easycommands.logs")) {
                online.sendMessage(ChatColor.GRAY + "[" + p.getName() + " has created the " + warpName + " warp]");
            }

            warpManager.setWarp(warpName, p.getLocation());
            String setwarp = Messages.get().getString("setwarp");
            setwarp = setwarp.replace("%warpname%", warpName);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', setwarp));
        }
        return true;
    }
}

