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

public class Invsee implements CommandExecutor {
    private final SettingsManager settingsManager;

    public Invsee(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){

            String playername = args[0];

            Player target = Bukkit.getServer().getPlayerExact(playername);

            p.openInventory(target.getInventory());

            String invSee = Messages.get().getString("Invsee");
            String Prefix = Messages.get().getString("Prefix");
            invSee = invSee.replace("%prefix%", Prefix);
            invSee = invSee.replace("%target%", target.getDisplayName());
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', invSee));

            LogManager.getInstance().log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " is looking in " + target.getDisplayName() + "'s inventory");
            for (Player online : Bukkit.getOnlinePlayers()) {
                if(settingsManager.isAdminLogsEnabled(online)) {
                    if (online.hasPermission("easycommands.logs.admin")) {
                        online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " is looking in " + target.getDisplayName() + "'s inventory]");
                    }
                }
        }
    }
        return true;
    }
}
