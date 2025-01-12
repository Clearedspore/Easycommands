package me.clearedspore.Commands;

import me.clearedspore.Commands.settings.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Msg implements CommandExecutor {
    private final SettingsManager settingsManager;

    public Msg(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String playername = args[0];
        Player target = Bukkit.getServer().getPlayerExact(playername);
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            builder.append(args[i]);
            builder.append(" ");
        }
        String MSG = builder.toString();
        MSG = MSG.stripTrailing();
        if (!(sender instanceof Player)) {
            if (args.length == 0) {
                sender.sendMessage("Please provide a name");
                return true;
            }
            if (args.length == 1) {
                sender.sendMessage("Please provide a message");
                return true;
            }
            if (args.length >= 2) {
                if (target.getName() == null) {
                    sender.sendMessage("Player is not online");
                } else {
                    if (settingsManager.ispmsgssettings(target)) {
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9From &fConsole > " + MSG));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9To &f" + target.getName() + " > " + MSG));
                    } else {
                        sender.sendMessage("Target has private messages turned off!");
                        return true;
                    }
                }
            }
        } else {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Please provide a name");
                return true;
            }
            if (args.length == 1) {
                sender.sendMessage("Please provide a message");
                return true;
            }
            if (args.length >= 2) {
                if (target.getName() == null) {
                    sender.sendMessage("Player is not online");
                } else {
                    if(settingsManager.ispmsgssettings(target)) {
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9From &f" + sender.getName() + " > " + MSG));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9To &f" + target.getName() + " > " + MSG));
                    } else {
                        sender.sendMessage(ChatColor.RED + "Target has private messages turned off!");
                        return true;
                    }
                }
            }
        }
        return true;
    }
}
