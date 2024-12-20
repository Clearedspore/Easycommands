package me.clearedspore.Commands.Freeze;

import me.clearedspore.ConfigFiles.Messages;
import me.clearedspore.Logs.LogManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.clearedspore.easycommands.Frozen;

public class Freeze implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){
                if(args.length == 0){
                    p.sendMessage(ChatColor.RED + "Please provide a player name!");
                }

                String playername = args[0];

                Player target = Bukkit.getServer().getPlayerExact(playername);

                if(Frozen.contains(target)){
                    target.setInvulnerable(false);
                    target.setGlowing(false);
                    Frozen.remove(target);

                    LogManager.log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has unfreezed " + target.getDisplayName());
                    String UnFreeze = Messages.get().getString("UnFreeze");
                    UnFreeze = UnFreeze.replace("%target%", target.getDisplayName());
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', UnFreeze));
                    for (Player online : Bukkit.getOnlinePlayers()){

                        if(online.hasPermission("easycommands.logs")){
                            online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has unfreezed " + target.getDisplayName() + "]");
                        }
                    }

                }else {
                    Frozen.add(target);
                    String Freeze = Messages.get().getString("Freeze");
                    Freeze = Freeze.replace("%target%", target.getDisplayName());


                    LogManager.log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has freezed " + target.getDisplayName());
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Freeze));
                    String FrozenNotify = Messages.get().getString("FrozenNotify");
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', FrozenNotify));
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', FrozenNotify));
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', FrozenNotify));
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', FrozenNotify));
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', FrozenNotify));

                    for (Player online : Bukkit.getOnlinePlayers()){

                        if(online.hasPermission("easycommands.logs")){
                            online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has freezed " + target.getDisplayName() + "]");
                        }
                    }
                }
            }
        return true;
    }
}
