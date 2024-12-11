package me.clearedspore.Commands.WarpSection;

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
        if(!(sender instanceof Player p)){
            sender.sendMessage("You can only remove a warp ingame!");
            return true;
        }

        if(args.length != 1){
            p.sendMessage(ChatColor.RED + "ERROR: Usage: /delwarp (name)");
            return true;
        }

        String warpName = args[0];
        warpManager.removeWarp(warpName);
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9Removed &f" + warpName + " &9warp"));
        return true;
    }
}
