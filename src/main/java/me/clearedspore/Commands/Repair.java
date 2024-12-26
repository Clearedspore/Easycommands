package me.clearedspore.Commands;

import me.clearedspore.ConfigFiles.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Repair implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player p){

            if(args.length == 0) {

                if (p.getInventory().getItemInHand() == null) {
                    p.sendMessage(ChatColor.RED + "You must be holding an item to run this command!");
                    return true;
                } else {
                    p.getInventory().getItemInMainHand().setDurability((short) 0);

                    String Repair = Messages.get().getString("Repair");
                    String Prefix = Messages.get().getString("Prefix");
                    Repair = Repair.replace("%prefix%", Prefix);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Repair));

                }
            }
        }
        return true;
    }
}
