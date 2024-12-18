package me.clearedspore.Commands;

import me.clearedspore.Files.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Nick implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){
            if(command.getName().equalsIgnoreCase("nick")){
                p.setDisplayName(args[0]);
                p.setPlayerListName(args[0]);
                p.setCustomName(args[0]);
                p.setCustomNameVisible(true);

                String Nick = Messages.get().getString("Nick");
                Nick = Nick.replace("%nickname%", args[0]);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Nick));

                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online.hasPermission("easycommands.logs"))
                        online.sendMessage(ChatColor.GRAY + "[Server: " + p.getName() + " has nicked themself as " + args[0] + "]");
                }

                return true;

            }else if(command.getName().equalsIgnoreCase("unnick")){
                p.setDisplayName(p.getName());
                p.setPlayerListName(p.getName());
                p.setCustomName(p.getName());
                p.setCustomNameVisible(false);

                String Unnick = Messages.get().getString("UnNick");
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Unnick));

                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online.hasPermission("easycommands.logs"))
                        online.sendMessage(ChatColor.GRAY + "[Server: " + p.getDisplayName() + " has unnicked themself]");
                }
            }
        }
        return true;
    }
}
