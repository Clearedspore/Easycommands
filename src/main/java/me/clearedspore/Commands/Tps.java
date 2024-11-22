package me.clearedspore.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tps implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){
            String tps = String.valueOf(Bukkit.getServer().getServerTickManager().getTickRate());

            p.sendMessage(ChatColor.BLUE + "Tickets per second = " + ChatColor.WHITE + tps);

        }
        return true;
    }
}
