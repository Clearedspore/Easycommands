package me.clearedspore.Commands;

import me.clearedspore.ConfigFiles.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Feed implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){

            if(args.length == 0){
                p.setFoodLevel(20);

                String Feed = Messages.get().getString("Feed");
                String Prefix = Messages.get().getString("Prefix");
                Feed = Feed.replace("%prefix%", Prefix);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Feed));

            }else{

                String playername = args[0];

                Player target = Bukkit.getServer().getPlayerExact(playername);

                if(target == null) {
                    p.sendMessage(ChatColor.RED + "Player is not online!");
                }else{
                    if(p.hasPermission("easycommands.feed.other")) {

                        target.setFoodLevel(20);

                        String FeedOther = Messages.get().getString("FeedOther");
                        String Prefix = Messages.get().getString("Prefix");
                        FeedOther = FeedOther.replace("%prefix%", Prefix);
                        FeedOther = FeedOther.replace("%target%", target.getDisplayName());
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', FeedOther));

                    }else{
                        if (!p.hasPermission(("easycommands.feed.other"))){
                            p.sendMessage(ChatColor.RED + "You don't have permission to feed other people!");
                        }
                    }
                }
            }

        }
        return true;
    }
}
