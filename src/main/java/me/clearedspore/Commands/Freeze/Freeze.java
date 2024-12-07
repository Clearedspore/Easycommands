package me.clearedspore.Commands.Freeze;

import me.clearedspore.Files.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

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
                    String UnFreeze = Messages.get().getString("UnFreeze");
                    UnFreeze = UnFreeze.replace("%target%", target.getDisplayName());
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', UnFreeze));
                    for (Player online : Bukkit.getOnlinePlayers()){

                        if(online.hasPermission("easycommands.logs")){
                            online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has freezed" + target.getDisplayName() + "]");
                        }
                    }

                }else {
                    Frozen.add(target);
                    String Freeze = Messages.get().getString("Freeze");
                    Freeze = Freeze.replace("%target%", target.getDisplayName());
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Freeze));

                    String FrozenNotify = Messages.get().getString("FrozenNotify");
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', FrozenNotify));
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', FrozenNotify));
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', FrozenNotify));
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', FrozenNotify));
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', FrozenNotify));

                    for (Player online : Bukkit.getOnlinePlayers()){

                        if(online.hasPermission("easycommands.logs")){
                            online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has unfreezed" + target.getDisplayName() + "]");
                        }
                    }
                }

            } else {
            Player p = (Player) sender;
            if(args.length == 0){
                p.sendMessage(ChatColor.RED + "Please provide a player name!");
            }

            String playername = args[0];

            Player target = Bukkit.getServer().getPlayerExact(playername);

            if(Frozen.contains(target)){
                target.setInvulnerable(false);
                target.setGlowing(false);
                Frozen.remove(target);
                String UnFreeze = Messages.get().getString("UnFreeze");
                UnFreeze = UnFreeze.replace("%target%", target.getDisplayName());
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', UnFreeze));
                for (Player online : Bukkit.getOnlinePlayers()){

                    if(online.hasPermission("easycommands.logs")){
                        online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has freezed" + target.getDisplayName() + "]");
                    }
                }

            }else {
                Frozen.add(target);
                String Freeze = Messages.get().getString("Freeze");
                Freeze = Freeze.replace("%target%", target.getDisplayName());
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Freeze));

                String FrozenNotify = Messages.get().getString("FrozenNotify");
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', FrozenNotify));
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', FrozenNotify));
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', FrozenNotify));
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', FrozenNotify));
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', FrozenNotify));

                for (Player online : Bukkit.getOnlinePlayers()){

                    if(online.hasPermission("easycommands.logs")){
                        online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has unfreezed" + target.getDisplayName() + "]");
                    }
                }
            }
        }
        return true;
    }
}
