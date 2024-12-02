package me.clearedspore.Commands.Teleport;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Teleport implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player p) {
            if(command.getName().equalsIgnoreCase("teleport"))
            if (args.length == 0) {
                p.sendMessage(ChatColor.RED + "Please provide a player name!");
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);

                p.teleport(target.getLocation());
                p.sendMessage(ChatColor.BLUE + "You have teleported been to " + ChatColor.WHITE + target.getDisplayName());

                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online.hasPermission("easycommands.logs"))
                        online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " teleported to " + target.getDisplayName() + "]");
                }
            } else if (args.length == 2) {

                    if(p.hasPermission("easycommands.teleport.here")){
                        Player playertosend = Bukkit.getPlayer(args[0]);
                        Player target = Bukkit.getPlayer(args[1]);

                        playertosend.teleport(target.getLocation());
                        p.sendMessage(ChatColor.BLUE + "You have teleported " + ChatColor.WHITE + playertosend.getDisplayName() + ChatColor.BLUE + " to " + ChatColor.WHITE + target.getDisplayName());

                        for (Player online : Bukkit.getOnlinePlayers()) {
                            if (online.hasPermission("easycommands.logs"))
                                online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has teleported " + playertosend.getDisplayName() + " to " + target.getDisplayName() + "]");
                        }
                    }else{
                        p.sendMessage(ChatColor.RED + "You don't have permission to teleport other players to you!");
                }
            }
            }
        return true;
    }
}