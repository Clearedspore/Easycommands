package me.clearedspore.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChat implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            p.sendMessage(ChatColor.BLUE + "You have cleared the chat!");
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (online.hasPermission("easycommands.clearchat.bypass")) {
                    online.sendMessage(ChatColor.WHITE + p.getDisplayName() + ChatColor.BLUE + " has cleared the chat!");
                }else if (online.hasPermission(String.valueOf("easycommands.clearchat.bypass" == null))){
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage("");
                    online.sendMessage(ChatColor.WHITE + p.getDisplayName() + ChatColor.BLUE + " has cleared the chat!");
                    }
                }
            }
        return true;
    }
}
