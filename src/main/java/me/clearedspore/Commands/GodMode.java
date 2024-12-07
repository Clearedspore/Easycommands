package me.clearedspore.Commands;

import me.clearedspore.Files.Messages;
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

                    String GodEnable = Messages.get().getString("GodEnable");
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', GodEnable));

                    for (Player online : Bukkit.getOnlinePlayers()){

                        if(online.hasPermission("easycommands.logs"))
                            online.sendMessage(ChatColor.GRAY + "[" + ChatColor.GRAY + p.getDisplayName() + ChatColor.GRAY + " has disabled godmode for themself]");
                    }
                } else {
                    p.setInvulnerable(true);

                    String GodDisable = Messages.get().getString("GodDisable");
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', GodDisable));

                    for (Player online : Bukkit.getOnlinePlayers()){

                        if(online.hasPermission("easycommands.logs"))
                            online.sendMessage(ChatColor.GRAY + "[" + ChatColor.GRAY + p.getDisplayName() + ChatColor.GRAY + " enabled godmode for themself]");
                    }
                }
        }
        return true;
    }
}
