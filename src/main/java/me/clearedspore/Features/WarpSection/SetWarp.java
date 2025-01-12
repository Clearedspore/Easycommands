package me.clearedspore.Features.WarpSection;

import me.clearedspore.Commands.settings.SettingsManager;
import me.clearedspore.ConfigFiles.Messages;
import me.clearedspore.Features.Logs.LogManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarp implements CommandExecutor {
    private final SettingsManager settingsManager;


    private final WarpManager warpManager;

    public SetWarp(SettingsManager settingsManager, WarpManager warpManager) {
        this.settingsManager = settingsManager;
        this.warpManager = warpManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can set warps!");
            return true;
        }


        Player p = (Player) sender;
        if (args.length != 1) {
            p.sendMessage("Usage: /setwarp <name>");
            return true;
        }

        String warpName = args[0];
        LogManager.getInstance().log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has created the " + warpName + " warp");
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.hasPermission("easycommands.logs")) {
                if (settingsManager.isLogEnabled(online)) {
                    online.sendMessage(ChatColor.GRAY + "[" + p.getName() + " has created the " + warpName + " warp]");
                }
            }

            warpManager.setWarp(warpName, p.getLocation());
            String setwarp = Messages.get().getString("setwarp");
            String Prefix = Messages.get().getString("Prefix");
            setwarp = setwarp.replace("%prefix%", Prefix);
            setwarp = setwarp.replace("%warpname%", warpName);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', setwarp));
        }
        return true;
    }
}

