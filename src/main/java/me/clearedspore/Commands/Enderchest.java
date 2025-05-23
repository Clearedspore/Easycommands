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

public class Enderchest implements CommandExecutor {
    private final SettingsManager settingsManager;

    public Enderchest(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {

            if (args.length == 0) {

                p.openInventory(p.getEnderChest());
            } else {

                String playername = args[0];

                Player target = Bukkit.getServer().getPlayerExact(playername);

                if (target == null) {
                    p.sendMessage(ChatColor.RED + "Player is not online!");
                } else if(p.hasPermission("easycommands.enderchest.other")){
                   p.openInventory(target.getEnderChest());

                    String EnderChestOther = Messages.get().getString("EnderChestOther");
                    String Prefix = Messages.get().getString("Prefix");
                    EnderChestOther = EnderChestOther.replace("%prefix%", Prefix);
                    EnderChestOther = EnderChestOther.replace("%target%", target.getDisplayName());
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', EnderChestOther));

                    LogManager.getInstance().log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has opened" + target.getDisplayName() + "'s enderchest");
                    for (Player online : Bukkit.getOnlinePlayers()){
                        if(settingsManager.isAdminLogsEnabled(online)) {
                            if (online.hasPermission("easycommands.logs.admin")) {
                                online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has opened " + target.getDisplayName() + "'s enderchest]");
                            }
                        }
                   }


                } else if(!p.hasPermission("easycommands.enderchest.other")){
                    p.sendMessage(ChatColor.RED + "You don't have permission to open other players their enderchest!");
                }
            }
        }
        return true;
    }
}
