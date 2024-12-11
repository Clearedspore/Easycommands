package me.clearedspore.Commands;

import me.clearedspore.Files.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Invsee implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){

            String playername = args[0];

            Player target = Bukkit.getServer().getPlayerExact(playername);

            p.openInventory(target.getInventory());

            String invSee = Messages.get().getString("Invsee");
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', invSee));

        }
        return true;
    }
}
