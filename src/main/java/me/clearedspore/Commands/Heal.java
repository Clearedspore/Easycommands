package me.clearedspore.Commands;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;

import java.awt.*;

public class Heal implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player p){

            if(args.length == 0){

                p.setHealth(20);
                p.setFoodLevel(20);
                p.sendMessage(ChatColor.BLUE + "You have been healed!");

                for (Player online : Bukkit.getOnlinePlayers()){

                    if(online.hasPermission("easycommands.logs"))
                        online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has healed himself]");
                }
            }else{

                String playername = args[0];

                Player target = Bukkit.getServer().getPlayerExact(playername);

                if (target == null) {
                    p.sendMessage(ChatColor.RED + "Player is not online!");
                }else{
                    if(p.hasPermission("easycommands.heal.other")) {

                        target.setHealth(20);
                        target.setFoodLevel(20);
                        target.sendMessage(ChatColor.BLUE + "You have been healed by " + ChatColor.WHITE + p.getDisplayName());
                        p.sendMessage(ChatColor.BLUE + "You have healed " + ChatColor.WHITE + target.getDisplayName());

                        for (Player online : Bukkit.getOnlinePlayers()) {

                            if (online.hasPermission("easycommands.logs")) {
                                online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has healed " + target.getDisplayName() + "]");
                            }
                        }
                        }else{
                            if (p.hasPermission(String.valueOf("easycommands.heal.other" == null))) {

                                p.sendMessage(ChatColor.RED + "You don't have permission to heal other people!");

                            }
                        }
                    }
                }
            }
        return true;
    }
}

