package me.clearedspore.Commands.Teleport;

import me.clearedspore.ConfigFiles.Messages;
import me.clearedspore.Features.Logs.LogManager;
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

            String players = String.valueOf(Bukkit.getOnlinePlayers().size());

            for(Player online : Bukkit.getOnlinePlayers()){

                online.teleport(p.getLocation());
                String Teleportall = Messages.get().getString("TeleportAll");
                String Prefix = Messages.get().getString("Prefix");
                Teleportall = Teleportall.replace("%prefix%", Prefix);
                Teleportall = Teleportall.replace("%online%", players);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Teleportall));
            }

            LogManager.log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " Has teleported all players to themself");
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (online.hasPermission("easycommands.logs"))
                    online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has teleported all players to themself]");
            }
        }
        return true;
    }
}
