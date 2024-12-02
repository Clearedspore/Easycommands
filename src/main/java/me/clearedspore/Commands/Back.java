package me.clearedspore.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class Back implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {

            if (args.length == 0) {
                Location DeathLocation = p.getLastDeathLocation();
                p.teleport(DeathLocation);
                p.sendMessage(ChatColor.BLUE + "You have been teleported back to your last death location!");
            } else if (args.length > 0) {

                String playername = args[0];

                Player target = Bukkit.getServer().getPlayerExact(playername);

                if (target == null) {
                    p.sendMessage(ChatColor.RED + "Player is not online!");
                } else{
                    if(p.hasPermission("easycommands.back.others")) {
                        Location TargetLocation = target.getLastDeathLocation();
                        target.teleport(TargetLocation);
                        p.sendMessage(ChatColor.BLUE + "You have teleported " + ChatColor.WHITE + target.getDisplayName() + ChatColor.BLUE + " to their last death location!");
                        target.sendMessage(ChatColor.BLUE + "You have been teleported back to your last death location!");

                        for (Player online : Bukkit.getOnlinePlayers()){

                            if(online.hasPermission("easycommands.logs"))
                                online.sendMessage(ChatColor.GRAY + "[" + ChatColor.GRAY + p.getDisplayName() + ChatColor.GRAY + " has teleported " + ChatColor.GRAY + target.getDisplayName() + ChatColor.GRAY + " to their last death location]");
                        }
                    } else{
                        p.sendMessage(ChatColor.RED +"You don't have permission to teleport other people to their last death location!");
                    }
                }
            }
        }
    return true;
    }
}
