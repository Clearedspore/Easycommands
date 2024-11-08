package me.clearedspore.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Repair implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player p){

            if(args.length == 0){

                p.getInventory().getItemInMainHand().setDurability((short) 0);
                p.sendMessage(ChatColor.BLUE + "Your item has been repaired!");
            }
        }
        return true;
    }
}
