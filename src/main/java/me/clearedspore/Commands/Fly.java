package me.clearedspore.Commands;

import me.clearedspore.ConfigFiles.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Fly implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(sender instanceof Player p){
        if(args.length < 1){
            if(p.getAllowFlight() == true){
                p.setAllowFlight(false);
            p.sendMessage(ChatColor.BLUE + "Flight disabled");
            return true;
            }
            if(p.getAllowFlight() == false){
                p.setAllowFlight(true);
                p.sendMessage(ChatColor.BLUE + "Flight enabled");
                return true;
            }

            String playername = args[0];
            Player target = Bukkit.getServer().getPlayerExact(playername);

            if(args.length == 1){
                if(p.hasPermission("easycommands.flight.others")) {
                    if (target == null) {
                        p.sendMessage(ChatColor.RED + "Player is not online");
                        return true;
                    }
                    if(target.getAllowFlight() == true){
                        target.setAllowFlight(false);
                        String flightdisabled = Messages.get().getString("flightdisabled");
                        String Prefix = Messages.get().getString("Prefix");
                        flightdisabled = flightdisabled.replace("%prefix%", Prefix);
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', flightdisabled));
                        return true;
                    }
                     if(target.getAllowFlight() == false){
                         target.setAllowFlight(true);
                         String flightenabled = Messages.get().getString("flightenabled");
                         String Prefix = Messages.get().getString("Prefix");
                         flightenabled = flightenabled.replace("%prefix%", Prefix);
                         p.sendMessage(ChatColor.translateAlternateColorCodes('&', flightenabled));
                     }
                } else {
                    p.sendMessage(ChatColor.RED + "You don't have permission to toggle other people their flight!");
                }
            }
        }

    }
        return true;
    }
}
