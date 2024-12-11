package me.clearedspore.Commands.WarpSection;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelWarpAll implements CommandExecutor {
    private WarpManager warpManager;

    public void DelwarpAll(WarpManager warpManager) {
        this.warpManager = warpManager;
    }

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if(!(sender instanceof Player p)){
                sender.sendMessage("All warps have been deleted!");
                warpManager.clearAllWarps();
            } else {
                sender.sendMessage(ChatColor.RED + "You can only run this command in console!");
            }
        return true;
    }
}

