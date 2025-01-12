package me.clearedspore.Commands;

import me.clearedspore.Commands.settings.SettingsManager;
import me.clearedspore.ConfigFiles.Messages;
import me.clearedspore.Features.Logs.LogManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Nick implements CommandExecutor {
    private final SettingsManager settingsManager;

    public Nick(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){
            if(command.getName().equalsIgnoreCase("nick")){
                p.setDisplayName(args[0]);
                p.setPlayerListName(args[0]);
                p.setCustomName(args[0]);
                p.setCustomNameVisible(true);

                String Nick = Messages.get().getString("Nick");
                String Prefix = Messages.get().getString("Prefix");
                Nick = Nick.replace("%prefix%", Prefix);
                Nick = Nick.replace("%nickname%", args[0]);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Nick));

                LogManager.getInstance().log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has nicked themself as " + args[0]);
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online.hasPermission("easycommands.logs")) {
                        if (settingsManager.isLogEnabled(online)) {
                            online.sendMessage(ChatColor.GRAY + "[Server: " + p.getName() + " has nicked themself as " + args[0] + "]");
                        }
                    }
                }

                return true;

            }else if(command.getName().equalsIgnoreCase("unnick")){
                p.setDisplayName(p.getName());
                p.setPlayerListName(p.getName());
                p.setCustomName(p.getName());
                p.setCustomNameVisible(false);

                String Unnick = Messages.get().getString("UnNick");
                String Prefix = Messages.get().getString("Prefix");
                Unnick = Unnick.replace("%prefix%", Prefix);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Unnick));

                LogManager.getInstance().log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has unnicked themself");
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online.hasPermission("easycommands.logs")){
                        if (settingsManager.isLogEnabled(online)) {
                            online.sendMessage(ChatColor.GRAY + "[Server: " + p.getDisplayName() + " has unnicked themself]");
                        }
                    }
                }
            }
        }
        return true;
    }
}
