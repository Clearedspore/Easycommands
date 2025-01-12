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

public class GodMode implements CommandExecutor {

    private final SettingsManager settingsManager;

    public GodMode(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
                if (p.isInvulnerable()) {
                    p.setInvulnerable(false);

                    String GodEnable = Messages.get().getString("GodDisable");
                    String Prefix = Messages.get().getString("Prefix");
                    GodEnable = GodEnable.replace("%prefix%", Prefix);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', GodEnable));

                    for (Player online : Bukkit.getOnlinePlayers()) {
                            if (online.hasPermission("easycommands.logs")) {
                                if(settingsManager.isLogEnabled(online)) {
                                online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has disabled godmode for themself");
                            }
                        }
                            LogManager.getInstance().log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has disabled godmode for themself");
                    }
                } else if(!p.isInvulnerable()){
                    p.setInvulnerable(true);
                    String GodDisable = Messages.get().getString("GodEnable");
                    String Prefix = Messages.get().getString("Prefix");
                    GodDisable = GodDisable.replace("%prefix%", Prefix);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', GodDisable));

                    for (Player online : Bukkit.getOnlinePlayers()) {
                            if (online.hasPermission("easycommands.logs")){
                                if (settingsManager.isLogEnabled(online)) {
                                online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has enabled godmode for themself");
                            }
                                LogManager.getInstance().log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has enabled godmode for themself");
                        }
                    }
                }
        }
        return true;
    }
}
