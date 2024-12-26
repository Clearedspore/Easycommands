package me.clearedspore.Commands;

import com.google.common.io.FileBackedOutputStream;
import me.clearedspore.ConfigFiles.Messages;
import me.clearedspore.Features.Logs.LogManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Back implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 0) {
                if(p.getLastDeathLocation() == null){
                    p.sendMessage(ChatColor.RED + "You haven't died!");
                    return true;
                } else {
                    Location DeathLocation = p.getLastDeathLocation();
                    p.teleport(DeathLocation);

                    String Back = Messages.get().getString("Back");
                    String Prefix = Messages.get().getString("Prefix");
                    Back = Back.replace("%prefix%", Prefix);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Back));
                }
            } else if (args.length > 0) {
                String playername = args[0];

                Player target = Bukkit.getServer().getPlayerExact(playername);

                if (target == null) {
                    p.sendMessage(ChatColor.RED + "Player is not online!");
                } else{
                    if(p.hasPermission("easycommands.back.other")) {
                        if(target.getLastDeathLocation() == null){
                            p.sendMessage(ChatColor.RED + target.getDisplayName() + "hasn't died");
                            return true;
                        } else {
                            Location TargetLocation = target.getLastDeathLocation();
                            target.teleport(TargetLocation);

                            String backOther = Messages.get().getString("BackOther");
                            String Prefix = Messages.get().getString("Prefix");
                            backOther = backOther.replace("%prefix%", Prefix);
                            backOther = backOther.replace("%target%", target.getDisplayName());
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', backOther));

                            String backT = Messages.get().getString("BackTarget");
                            backT = backT.replace("%prefix%", Prefix);
                            target.sendMessage(ChatColor.translateAlternateColorCodes('&', backT));

                            LogManager.log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has teleported" + target.getName() + " back to their death location");
                            for (Player online : Bukkit.getOnlinePlayers()) {

                                if (online.hasPermission("easycommands.logs"))
                                    online.sendMessage(ChatColor.GRAY + "[" + ChatColor.GRAY + p.getDisplayName() + ChatColor.GRAY + " has teleported " + ChatColor.GRAY + target.getDisplayName() + ChatColor.GRAY + " to their last death location]");
                            }
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
