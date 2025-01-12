package me.clearedspore.Commands;

import me.clearedspore.ConfigFiles.Messages;
import me.clearedspore.easycommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.clearedspore.easycommands.plugin;

public class Broadcast implements CommandExecutor {
    private final easycommands plugin;

    public Broadcast(easycommands plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String Broadcast = plugin.getConfig().getString("broadcastprefix");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            builder.append(args[i]);
            builder.append(" ");
        }
        String MSG = builder.toString();
        MSG = MSG.stripTrailing();
        if(!(sender instanceof Player)){
        for(Player online : Bukkit.getOnlinePlayers()) {
            online.sendMessage(ChatColor.translateAlternateColorCodes('&', Broadcast + MSG));
        }
        } else {
            for (Player online : Bukkit.getOnlinePlayers()) {
                online.sendMessage(ChatColor.translateAlternateColorCodes('&', Broadcast + MSG));
            }
        }
        return true;
    }
}
