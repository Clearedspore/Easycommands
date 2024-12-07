package me.clearedspore.Commands;

import me.clearedspore.Files.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.clearedspore.easycommands.InvLooking;

public class Invsee implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            String playername = args[0];

            Player target = Bukkit.getServer().getPlayerExact(playername);

            if (target == null) {
                p.sendMessage(ChatColor.RED + "Player is not online!");
            } else {
                p.openInventory(target.getInventory());

                String Invsee = Messages.get().getString("Invsee");
                Invsee = Invsee.replace("%target%", target.getDisplayName());
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Invsee));

                for (Player online : Bukkit.getOnlinePlayers()){
                    if(online.hasPermission("easycommands.logs.admin")){
                        online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has opened " + target.getDisplayName() + "'s Inventory]");
                    }
                }
            }
        }
        return true;
    }
}
