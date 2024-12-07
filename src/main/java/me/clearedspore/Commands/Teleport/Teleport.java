package me.clearedspore.Commands.Teleport;

import me.clearedspore.Files.Messages;
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
            if (args.length == 0) {
                p.sendMessage(ChatColor.RED + "Please provide a player name!");
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);

                p.teleport(target.getLocation());

                String Teleport = Messages.get().getString("Teleport");
                Teleport = Teleport.replace("%target%", target.getDisplayName());
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Teleport));

                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online.hasPermission("easycommands.logs"))
                        online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " teleported to " + target.getDisplayName() + "]");
                }
            } else if (args.length == 2) {

                    if(p.hasPermission("easycommands.teleport.other")){
                        Player playertosend = Bukkit.getPlayer(args[0]);
                        Player target = Bukkit.getPlayer(args[1]);

                        playertosend.teleport(target.getLocation());

                        String Teleport = Messages.get().getString("TeleportOthers");
                        Teleport = Teleport.replace("%playertosend%", playertosend.getDisplayName());
                        Teleport = Teleport.replace("%target%", target.getDisplayName());
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Teleport));

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