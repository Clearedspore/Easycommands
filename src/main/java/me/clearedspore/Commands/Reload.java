package me.clearedspore.Commands;

import me.clearedspore.Logs.LogManager;
import me.clearedspore.WarpSection.WarpManager;
import me.clearedspore.ConfigFiles.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Reload implements TabExecutor {

    private final WarpManager warpManager;
    private final JavaPlugin plugin;

    public Reload(WarpManager warpManager, JavaPlugin plugin) {
        this.warpManager = warpManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 0) {
                p.sendMessage(ChatColor.RED + "Please provide an argument");
                return true;
            }
            if (args.length > 0) {
                switch (args[0]) {
                    case "reload":
                        Messages.reload();
                        warpManager.reloadWarps();
                        plugin.reloadConfig();
                        String Reload = Messages.get().getString("Reload");
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Reload));

                        LogManager.log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has reloaded the plugin");
                        for (Player online : Bukkit.getOnlinePlayers()) {
                            if (online.hasPermission("easycommands.logs.admin")) {
                                online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " is reloading the plugin]");
                            }
                        }
                        break;
                }
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> arguments = new ArrayList<>();
        if (args.length == 1) {
            if (sender.hasPermission("easycommands.easycommands.reload")) {
                arguments.add("reload");
            }
            return arguments;
        }
        return null;
    }
}
