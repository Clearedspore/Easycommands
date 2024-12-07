package me.clearedspore.Commands.Teleport;

import me.clearedspore.Files.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportAll implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){

            p.sendMessage(ChatColor.BLUE + "You have teleported every online player to you!");

            for(Player online : Bukkit.getOnlinePlayers()){

                online.teleport(p.getLocation());
                String Teleportall = Messages.get().getString("TeleportAll");
                Teleportall = Teleportall.replace((CharSequence) "%online%", (CharSequence) Bukkit.getServer().getOnlinePlayers());
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Teleportall));
            }
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (online.hasPermission("easycommands.logs"))
                    online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has teleported all players to him]");
            }
        }
        return true;
    }
}
