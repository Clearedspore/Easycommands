package me.clearedspore.Commands;

import me.clearedspore.Features.Logs.LogManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Kick implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String playername = args[0];
        Player target = Bukkit.getServer().getPlayerExact(playername);
        StringBuilder builder = new StringBuilder();
        for(int i = 1; i < args.length; i++) {
            builder.append(args[i]);
            builder.append(" ");
        }
        String Reason = builder.toString();
        Reason = Reason.stripTrailing();
        if(!(sender instanceof Player)){
            if(args.length == 0){
                sender.sendMessage("Please provide a player name!");
                return true;
            }
            if(args.length == 1){
                sender.sendMessage("Please provide a reason!");
                return true;
            }
            if(args.length >= 2){

                List<String> KickMessage = new ArrayList<>();
                KickMessage.add(ChatColor.RED + "You have been Kicked!");
                KickMessage.add(ChatColor.RED + "");
                KickMessage.add(ChatColor.WHITE + "Reason: " + ChatColor.YELLOW + Reason);
                KickMessage.add(ChatColor.WHITE + "");
                String kickMessage = String.join("\n", KickMessage);
                target.kickPlayer(kickMessage);
                sender.sendMessage("You have kicked " + target.getName());
                for(Player online : Bukkit.getOnlinePlayers()){
                    if(online.hasPermission("easycommands.logs")){
                        online.sendMessage(ChatColor.RED + "[Console] " + ChatColor.WHITE + "Has kicked " + ChatColor.BLUE + target.getName());
                        return true;
                    }
                }
            }
        } else {
            Player p = (Player) sender;
            if(args.length == 0){
                p.sendMessage(ChatColor.RED + "Please provide a player name!");
                return true;
            }
            if(args.length == 1){
                p.sendMessage(ChatColor.RED + "Please provide a reason!");
                return true;
            }
            if(args.length >= 2){

                List<String> KickMessage = new ArrayList<>();
                KickMessage.add(ChatColor.RED + "You have been Kicked!");
                KickMessage.add(ChatColor.RED + "");
                KickMessage.add(ChatColor.WHITE + "Reason: " + ChatColor.YELLOW + Reason);
                KickMessage.add(ChatColor.WHITE + "");
                String kickMessage = String.join("\n", KickMessage);
                target.kickPlayer(kickMessage);
                p.sendMessage(ChatColor.BLUE + "You have kicked " + ChatColor.WHITE + target.getName());

                LogManager.log(p.getUniqueId(),  ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " Has kicked " + target.getName());
                for(Player online : Bukkit.getOnlinePlayers()){
                    if(online.hasPermission("easycommands.logs")){
                        online.sendMessage(ChatColor.RED + p.getName() + ChatColor.WHITE + "Has kicked " + ChatColor.BLUE + target.getName());
                        return true;
                    }
                }
            }
        }
        return true;
    }
}
