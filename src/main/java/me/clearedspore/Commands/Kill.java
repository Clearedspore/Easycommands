package me.clearedspore.Commands;

import me.clearedspore.Files.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Kill implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){

            if(args.length == 0){
                p.setHealth(0);

                String Kill = Messages.get().getString("Kill");
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Kill));

            }else{

                String playername = args[0];

                Player target = Bukkit.getServer().getPlayerExact(playername);

                if(target == null) {
                    p.sendMessage(ChatColor.RED + "Player is not online!");
                }else{
                    if(p.hasPermission("easycommands.kill.other")) {

                        target.setHealth(0);

                        String KillOther = Messages.get().getString("KillOther");
                        KillOther = KillOther.replace("%target%", target.getDisplayName());
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', KillOther));

                    }else{
                        if (!p.hasPermission(("easycommands.kill.other"))){
                            p.sendMessage(ChatColor.RED + "You don't have permission to feed other people!");
                        }
                    }
                }
            }

        }
        return true;
    }
}
