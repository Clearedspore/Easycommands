package me.clearedspore.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;

import java.util.concurrent.Executor;

public class GodMode implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
                if (p.isInvulnerable()) {
                    p.setInvulnerable(false);
                    p.sendMessage(ChatColor.RED + "GodMode disabled!");
                    for (Player online : Bukkit.getOnlinePlayers()){

                        if(online.hasPermission("easycommands.logs"))
                            online.sendMessage(ChatColor.GRAY + "[" + ChatColor.GRAY + p.getDisplayName() + ChatColor.GRAY + " has disabled godmode for themself]");
                    }
                } else {
                    p.setInvulnerable(true);
                    p.sendMessage(ChatColor.GREEN + "GodMode enabled");
                    for (Player online : Bukkit.getOnlinePlayers()){

                        if(online.hasPermission("easycommands.logs"))
                            online.sendMessage(ChatColor.GRAY + "[" + ChatColor.GRAY + p.getDisplayName() + ChatColor.GRAY + " enabled godmode for themself]");
                    }
                }
        }
        return true;
    }
}
