package me.clearedspore.Commands;

import me.clearedspore.Files.Messages;
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

                String Heal = Messages.get().getString("Heal");
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Heal));

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

                        String HealOther = Messages.get().getString("HealOther");
                        HealOther = HealOther.replace("%target%", target.getDisplayName());
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', HealOther));

                        for (Player online : Bukkit.getOnlinePlayers()) {

                            if (online.hasPermission("easycommands.logs")) {
                                online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has healed " + target.getDisplayName() + "]");
                            }
                        }
                        }else{
                            if (!p.hasPermission(("easycommands.heal.other"))) {

                                p.sendMessage(ChatColor.RED + "You don't have permission to heal other people!");

                            }
                        }
                    }
                }
            }
        return true;
    }
}

