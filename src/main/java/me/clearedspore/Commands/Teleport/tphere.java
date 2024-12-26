package me.clearedspore.Commands.Teleport;

import me.clearedspore.ConfigFiles.Messages;
import me.clearedspore.Features.Logs.LogManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class tphere implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){

            if(args.length == 0){
                p.sendMessage(ChatColor.RED+ "Please provide a player name \n Example: /tphere (player)");

            }else if(args.length == 1){
                Player target = Bukkit.getPlayer(args[0]);

                target.teleport(p.getLocation());
                String Teleport = Messages.get().getString("Teleport");
                String Prefix = Messages.get().getString("Prefix");
                Teleport = Teleport.replace("%prefix%", Prefix);
                Teleport = Teleport.replace("%target%", target.getDisplayName());
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Teleport));


                LogManager.log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has teleported " + target.getDisplayName() + " to themself");
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online.hasPermission("easycommands.logs"))
                        online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has teleported " + target.getDisplayName() + " to themself]");
                }
            }

        }
        return true;
    }
}
