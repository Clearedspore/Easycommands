package me.clearedspore.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Nick implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){
            if(command.getName().equalsIgnoreCase("nick")){
                p.setDisplayName(args[0]);
                p.setPlayerListName(args[0]);
                p.setCustomName(args[0]);
                p.setCustomNameVisible(true);
                p.sendMessage(ChatColor.BLUE+ "You have nicked yourself as " + ChatColor.WHITE + args[0]);
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online.hasPermission("easycommands.logs"))
                        online.sendMessage(ChatColor.GRAY + "[Server: " + p.getName() + " has nicked themself as " + args[0] + "]");
                }

                return true;

            }else if(command.getName().equalsIgnoreCase("unnick")){
                p.setDisplayName(p.getName());
                p.setPlayerListName(p.getName());
                p.setCustomName(p.getName());
                p.setCustomNameVisible(false);
                p.sendMessage(ChatColor.BLUE + "You have unnicked yourself!");
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online.hasPermission("easycommands.logs"))
                        online.sendMessage(ChatColor.GRAY + "[Server: " + p.getDisplayName() + " has unnicked themself]");
                }
            }
        }
        return true;
    }
}
