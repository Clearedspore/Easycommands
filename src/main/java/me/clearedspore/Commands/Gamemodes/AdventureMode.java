package me.clearedspore.Commands.Gamemodes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.Executor;

public class AdventureMode implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){

            if(args.length == 0){
                p.setGameMode(GameMode.ADVENTURE);
                p.sendMessage(ChatColor.BLUE + "Set gamemode to" + ChatColor.WHITE + " Adventure");

                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online.hasPermission("easycommands.logs"))
                        online.sendMessage(ChatColor.GRAY + "[Server: " + ChatColor.GRAY + p.getDisplayName() + ChatColor.GRAY + " has changed their gamemode to Adventure]");
                }
            }else{

                String playername = args[0];

                Player target = Bukkit.getServer().getPlayerExact(playername);

                if(target == null){
                    p.sendMessage(ChatColor.RED + "Player is not online!");
                }else{
                    if(p.hasPermission("easycommands.adventure.other")) {

                        target.setGameMode(GameMode.CREATIVE);
                        target.sendMessage(ChatColor.BLUE + "Your gamemode has been changed by " + ChatColor.WHITE + p.getDisplayName() + ChatColor.BLUE + "to " + ChatColor.WHITE + "Adventure");
                        p.sendMessage(ChatColor.BLUE + "You have changed " + ChatColor.WHITE + target.getDisplayName() + "'s " +ChatColor.BLUE + "to "+ ChatColor.WHITE + "Adventure");

                        for(Player online : Bukkit.getOnlinePlayers()){
                            if(online.hasPermission("easycommands.logs"))
                                online.sendMessage(ChatColor.GRAY + "[Server: " + p.getDisplayName() + "has changed the gamemode for " + target.getDisplayName() + " to Adventure]");
                        }
                    }else{
                        if (p.hasPermission(String.valueOf("easycommands.adventure.other" == null))){

                            p.sendMessage(ChatColor.RED + "You don't have permission to change other players their gamemode!");
                        }
                    }
                }
            }
        }
        return true;
    }
}
