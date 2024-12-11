package me.clearedspore.Commands;

import me.clearedspore.Files.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChat implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (online.hasPermission("easycommands.clearchat.bypass")) {
                    String ClearChat = Messages.get().getString("ClearChat");
                    ClearChat = ClearChat.replace("%player%", p.getDisplayName());
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', ClearChat));
                } else if (!online.hasPermission(("easycommands.clearchat.bypass"))) {

                    clearPlayerChat(online);

                    String ClearChat = Messages.get().getString("ClearChat");
                    ClearChat = ClearChat.replace("%player%", p.getDisplayName());
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', ClearChat));
                }
            }
        }
        return true;
    }

    private void clearPlayerChat(Player target) {
        for (int i = 0; i < 100; i++) {
            target.sendMessage("");
        }
    }
}
