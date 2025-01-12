package me.clearedspore.Features.AutoPickup;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PickupCommand implements CommandExecutor, TabCompleter {
    private final PickupManager PickupManager;

    public PickupCommand(PickupManager autoPickupManager) {
        this.PickupManager = autoPickupManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                PickupManager.getGui().OpenGUI(p);
                return true;
            } else {
                sender.sendMessage("Only players can open the pickup GUI.");
                return true;
            }
        }
        String playername = args[0];
        Player target = Bukkit.getServer().getPlayerExact(playername);

        if (!(sender instanceof Player)) {
            if (args.length < 2) {
                sender.sendMessage("Usage: /autopickup <player> <AutoPickup/AutoSmelt>");
                return true;
            }
            if ("AutoPickup".equalsIgnoreCase(args[1])) {
                if (target == null) {
                    sender.sendMessage("Player not found.");
                    return true;
                }
                if(PickupManager.isAutoPickupEnabled(target)){
                    PickupManager.SetAutpickup(target, false);
                    sender.sendMessage("Autopickup disabled for " + target.getDisplayName());
                } else {
                    PickupManager.SetAutpickup(target, true);
                    sender.sendMessage("Autopickup enabled for " + target.getDisplayName());
                }
                return true;
            }
            if("AutoSmelt".equalsIgnoreCase(args[1])){
                if (target == null) {
                    sender.sendMessage("Player not found.");
                    return true;
                }
                if(PickupManager.isAutoSmeltEnabled(target)){
                    PickupManager.SetAutoSmelt(target, false);
                    sender.sendMessage("AutoSmelt disabled for " + target.getDisplayName());
                } else {
                    PickupManager.SetAutoSmelt(target, true);
                    sender.sendMessage("AutoSmelt enabled for " + target.getDisplayName());
                }
                return true;
            }

            sender.sendMessage("Invalid pickup type.");
            return true;
        }
        Player p = (Player) sender;

        if (args.length == 1) {
            p.sendMessage(ChatColor.RED + "Please provide a pickup type.");
            return true;
        }

    if(p.hasPermission("easycommands.pickup.other") || p.hasPermission("easycommands.pickup.*")) {
        if (p.hasPermission("easycommands.pickup.autopickup") && p.hasPermission("easycommands.pickup.other") || p.hasPermission("easycommands.pickup.*")) {
            if ("AutoPickup".equalsIgnoreCase(args[1])) {
                if (target == null) {
                    p.sendMessage(ChatColor.RED + "Player not found.");
                    return true;
                }
                if (PickupManager.isAutoPickupEnabled(target)) {
                    PickupManager.SetAutpickup(target, false);
                    p.sendMessage(ChatColor.GREEN + "Autopickup disabled for " + target.getDisplayName());
                } else {
                    PickupManager.SetAutpickup(target, true);
                    p.sendMessage(ChatColor.GREEN + "Autopickup enabled for " + target.getDisplayName());
                }
                return true;
            }
        } else {
            p.sendMessage(ChatColor.RED + "You don't have permision to toggle other players their AutoPickup");
            return true;
        }
        if (p.hasPermission("easycommands.pickup.autosmelt") && p.hasPermission("easycommands.pickup.other") || p.hasPermission("easycommands.pickup.*")) {
            if ("AutoSmelt".equalsIgnoreCase(args[1])) {
                if (target == null) {
                    sender.sendMessage("Player not found.");
                    return true;
                }
                if (PickupManager.isAutoSmeltEnabled(target)) {
                    PickupManager.SetAutoSmelt(target, false);
                    sender.sendMessage(ChatColor.GREEN + "AutoSmelt disabled for " + target.getDisplayName());
                } else {
                    PickupManager.SetAutoSmelt(target, true);
                    sender.sendMessage(ChatColor.GREEN + "AutoSmelt enabled for " + target.getDisplayName());
                }
                return true;
            }
        } else {
            p.sendMessage(ChatColor.RED + "You don't have permission to toggle other players their AutoSmelt");
            return true;
        }
    } else {
        p.sendMessage(ChatColor.RED + "You don't have permission to toggle other players their pickups!");
        }

        p.sendMessage(ChatColor.RED + "Invalid pickup type.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> suggestions = new ArrayList<>();
        Player p = (Player) sender;
        if(args.length == 1){
            for (Player player : Bukkit.getOnlinePlayers()) {
                suggestions.add(player.getName());
            }
        } else if(args.length == 2) {
            if (p.hasPermission("easycommands.pickup.other") || p.hasPermission("easycommands.pickup.*")) {
                if(p.hasPermission("easycommands.pickup.autopickup") && p.hasPermission("easycommands.pickup.other") || p.hasPermission("easycommands.pickup.*")) {
                    suggestions.add("AutoPickup");
                }
                if(p.hasPermission("easycommands.pickup.autosmelt") && p.hasPermission("easycommands.pickup.other") || p.hasPermission("easycommands.pickup.*")) {
                    suggestions.add("AutoSmelt");
                }
            }
        }
        return suggestions;
    }
}
