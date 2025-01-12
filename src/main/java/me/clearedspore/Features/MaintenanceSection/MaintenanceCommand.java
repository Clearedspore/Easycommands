package me.clearedspore.Features.MaintenanceSection;

import me.clearedspore.ConfigFiles.Messages;
import me.clearedspore.Features.Logs.LogManager;
import me.clearedspore.easycommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MaintenanceCommand implements CommandExecutor, TabCompleter {

    private final easycommands plugin;
    private final MaintenanceManager maintenanceManager;

    public MaintenanceCommand(easycommands plugin, MaintenanceManager maintenanceManager) {
        this.plugin = plugin;
        this.maintenanceManager = maintenanceManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /maintenance <on|off|add|remove|kickall>");
            return true;
        }

        if (!(sender instanceof Player p)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        String Mon = Messages.get().getString("mon");
        String Moff = Messages.get().getString("moff");
        String Madd = Messages.get().getString("madd");
        String Mremove = Messages.get().getString("mremove");
        String Mkickall = Messages.get().getString("mkickall");

        String Prefix = Messages.get().getString("Prefix");
        Mon = Mon.replace("%prefix%", Prefix);
        Moff = Moff.replace("%prefix%", Prefix);
        Madd = Madd.replace("%prefix%", Prefix);
        Mremove = Mremove.replace("%prefix%", Prefix);
        Mkickall = Mkickall.replace("%prefix%", Prefix);

        switch (args[0].toLowerCase()) {
            case "on" -> {
                if (p.hasPermission("easycommands.maintenance.toggle") || p.hasPermission("easycommands.maintenance.*")) {
                    maintenanceManager.setMaintenanceEnabled(true);
                    LogManager.getInstance().log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has enabled the maintenance");
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Mon));
                } else {
                    p.sendMessage(ChatColor.RED + "You don't have permission to enable maintenance mode.");
                }
            }
            case "off" -> {
                if (p.hasPermission("easycommands.maintenance.toggle") || p.hasPermission("easycommands.maintenance.*")) {
                    maintenanceManager.setMaintenanceEnabled(false);
                    LogManager.getInstance().log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has disabled the maintenance");
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Moff));
                } else {
                    p.sendMessage(ChatColor.RED + "You don't have permission to disable maintenance mode.");
                }
            }
            case "add" -> {
                if (!p.hasPermission("easycommands.maintenance.add") && !p.hasPermission("easycommands.maintenance.*")) {
                    p.sendMessage(ChatColor.RED + "You don't have permission to add players to the maintenance whitelist.");
                    return true;
                }
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "Usage: /maintenance add <player>");
                    return true;
                }
                if (maintenanceManager.addToWhitelist(args[1])) {
                    LogManager.getInstance().log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has added " + args[1] + " to the whitelist.");
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Madd.replace("%target%", args[1])));
                } else {
                    sender.sendMessage(ChatColor.RED + args[1] + " is already on the whitelist.");
                }
            }
            case "remove" -> {
                if (!p.hasPermission("easycommands.maintenance.remove") && !p.hasPermission("easycommands.maintenance.*")) {
                    p.sendMessage(ChatColor.RED + "You don't have permission to remove players from the maintenance whitelist.");
                    return true;
                }
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "Usage: /maintenance remove <player>");
                    return true;
                }
                if (maintenanceManager.removeFromWhitelist(args[1])) {
                    LogManager.getInstance().log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has removed " + args[1] + " from the whitelist.");
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Mremove.replace("%target%", args[1])));
                } else {
                    sender.sendMessage(ChatColor.RED + args[1] + " is not on the whitelist.");
                }
            }
            case "kickall" -> {
                if (!p.hasPermission("easycommands.maintenance.kickall") && !p.hasPermission("easycommands.maintenance.*")) {
                    p.sendMessage(ChatColor.RED + "You don't have permission to kick all players.");
                    return true;
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!maintenanceManager.isWhitelisted(player.getName())) {
                        player.kickPlayer(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("kick-message", "&cMaintenance is enabled, you can't join!")));
                    }
                }
                LogManager.getInstance().log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has kicked all players from the server that are not whitelisted.");
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Mkickall));
            }
            default -> sender.sendMessage(ChatColor.RED + "Unknown command. Usage: /maintenance <on|off|add|remove|kickall>");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (!(sender instanceof Player p)) {
            return suggestions;
        }

        if (args.length == 1) {
            if (p.hasPermission("easycommands.maintenance.toggle")) {
                suggestions.add("on");
                suggestions.add("off");
            }
            if (p.hasPermission("easycommands.maintenance.add")) {
                suggestions.add("add");
            }
            if (p.hasPermission("easycommands.maintenance.remove")) {
                suggestions.add("remove");
            }
            if (p.hasPermission("easycommands.maintenance.kickall")) {
                suggestions.add("kickall");
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
                suggestions.addAll(maintenanceManager.getWhitelistedPlayers());
            }
        }

        return suggestions;
    }
}
