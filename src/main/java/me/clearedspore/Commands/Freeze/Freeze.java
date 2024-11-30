package me.clearedspore.Commands.Freeze;

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
                    target.removePotionEffect(PotionEffectType.BLINDNESS);
                    target.removePotionEffect(PotionEffectType.INVISIBILITY);
                    Frozen.remove(target);
                    p.sendMessage(ChatColor.BLUE + "You have unfrozen " + ChatColor.WHITE + target.getDisplayName());
                }else {
                    Frozen.add(target);
                    p.sendMessage(ChatColor.BLUE + "You have frozen " + ChatColor.WHITE + target.getDisplayName());
                }

            }
        return true;
    }
}
