package me.clearedspore.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.ServerTickManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Feed implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){

            if(args.length == 0){
                p.setFoodLevel(20);
                p.sendMessage(ChatColor.BLUE + "Your saturation has been set to max!");
            }else{

                String playername = args[0];

                Player target = Bukkit.getServer().getPlayerExact(playername);

                if(target == null) {
                    p.sendMessage(ChatColor.RED + "Player is not online!");
                }else{
                    if(p.hasPermission("easycommands.feed.other")) {

                        target.setFoodLevel(20);
                        target.sendMessage(ChatColor.BLUE + "Your saturation has been set to max! By " + ChatColor.WHITE + p.getDisplayName());
                        p.sendMessage(ChatColor.BLUE + "You have set " + ChatColor.WHITE + target.getDisplayName() + ChatColor.BLUE + " their saturation to max!");
                    }else{
                        if (p.hasPermission(String.valueOf("easycommands.feed.other" == null))){
                            p.sendMessage(ChatColor.RED + "You don't have permission to feed other people!");
                        }
                    }
                }
            }

        }
        return true;
    }
}
