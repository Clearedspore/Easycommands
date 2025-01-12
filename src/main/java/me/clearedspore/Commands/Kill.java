package me.clearedspore.Commands;

import me.clearedspore.Commands.settings.SettingsManager;
import me.clearedspore.ConfigFiles.Messages;
import me.clearedspore.Features.Logs.LogManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Kill implements CommandExecutor, TabCompleter {
    private final SettingsManager settingsManager;
    private final LogManager logManager;

    public Kill(SettingsManager settingsManager, LogManager logManager) {
        this.settingsManager = settingsManager;
        this.logManager = logManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (args.length < 2) {
                p.sendMessage(ChatColor.RED + "Usage: /kill (mob|player) <name|*>");
                return true;
            }

            String type = args[0];
            String targetName = args[1];

            if (type.equalsIgnoreCase("player")) {
                if (targetName.equals("*")) {
                    if (p.hasPermission("easycommands.kill.all") && p.hasPermission("easycommands.kill.players")) {
                        for (Player target : Bukkit.getOnlinePlayers()) {
                            target.setHealth(0);
                        }
                        logManager.log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has killed all online players");
                        for (Player online : Bukkit.getOnlinePlayers()){
                            if(settingsManager.isLogEnabled(online)){
                                if(online.hasPermission("easycommands.logs")){
                                    online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has killed all online players!]");
                                }
                            }
                        }
                        String Prefix = Messages.get().getString("Prefix");
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Prefix + ChatColor.BLUE + " All players have been killed." ));
                    } else {
                        p.sendMessage(ChatColor.RED + "You don't have permission to kill all players!");
                    }
                } else {
                    Player target = Bukkit.getPlayerExact(targetName);
                    if (target != null) {
                        if (p.hasPermission("easycommands.kill.players")) {
                            target.setHealth(0);

                            logManager.log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has killed " + target.getName());
                            for (Player online : Bukkit.getOnlinePlayers()){
                                if(settingsManager.isLogEnabled(online)){
                                    if(online.hasPermission("easycommands.logs")){
                                        online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has killed " + target.getName() + "]");
                                    }
                                }
                            }
                            String killother = Messages.get().getString("KillOther");
                            String Prefix = Messages.get().getString("Prefix");
                            killother = killother.replace("%target%", target.getName());
                            killother = killother.replace("%prefix%", Prefix);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', killother));
                        } else {
                            p.sendMessage(ChatColor.RED + "You don't have permission to kill players!");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "Player not found!");
                    }
                }
            } else if (type.equalsIgnoreCase("mob")) {
                if (targetName.equals("*")) {
                    if (p.hasPermission("easycommands.kill.all") && p.hasPermission("easycommands.kill.mobs")) {
                        List<LivingEntity> mobs = p.getWorld().getLivingEntities().stream()
                                .filter(entity -> entity.getLocation().distance(p.getLocation()) <= 500)
                                .collect(Collectors.toList());
                        int mobsKilled = 0;
                        for (LivingEntity mob : mobs) {
                            if (!(mob instanceof Player)) {
                                mob.setHealth(0);
                                mobsKilled++;
                            }
                        }
                        logManager.log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has killed " + mobsKilled + " mobs.");

                        for (Player online : Bukkit.getOnlinePlayers()){
                        if(settingsManager.isLogEnabled(online)){
                            if(online.hasPermission("easycommands.logs")){
                                online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has killed " + mobsKilled + " mobs]");
                            }
                        }
                        }
                        String Prefix = Messages.get().getString("Prefix");
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Prefix + ChatColor.BLUE + " " + String.valueOf(mobsKilled)+ " mobs have been killed."));
                    } else {
                        p.sendMessage(ChatColor.RED + "You don't have permission to kill all mobs!");
                    }
                } else {
                    try {
                        EntityType mobType = EntityType.valueOf(targetName.toUpperCase());
                        if (mobType.isAlive() && !mobType.equals(EntityType.PLAYER)) {
                            List<LivingEntity> mobs = p.getWorld().getLivingEntities().stream()
                                    .filter(entity -> entity.getType() == mobType && entity.getLocation().distance(p.getLocation()) <= 500)
                                    .collect(Collectors.toList());
                            int mobsKilled = 0;
                            for (LivingEntity mob : mobs) {
                                mob.setHealth(0);
                                mobsKilled++;
                            }
                            logManager.log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has killed " + mobsKilled + " " + mobType.name().toLowerCase() + "(s)");
                            for (Player online : Bukkit.getOnlinePlayers()){
                                if(settingsManager.isLogEnabled(online)){
                                    if(online.hasPermission("easycommands.logs")){
                                        online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has killed " + mobsKilled + " " + mobType.name().toLowerCase() + "(s)]");
                                    }
                                }
                            }
                            String Prefix = Messages.get().getString("Prefix");
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Prefix + " " + ChatColor.BLUE + String.valueOf(mobsKilled) + " " + mobType.name().toLowerCase() + "(s) have been killed."));
                        } else {
                            p.sendMessage(ChatColor.RED + "Invalid mob type!");
                        }
                    } catch (IllegalArgumentException e) {
                        p.sendMessage(ChatColor.RED + "Invalid mob type!");
                    }
                }
            } else {
                p.sendMessage(ChatColor.RED + "Invalid type! Use 'mob' or 'player'.");
            }
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("mob");
            completions.add("player");
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("player")) {
                completions.add("*");
                completions.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
            } else if (args[0].equalsIgnoreCase("mob")) {
                completions.add("*");
                for (EntityType entityType : EntityType.values()) {
                    if (entityType.isAlive() && !entityType.equals(EntityType.PLAYER)) {
                        completions.add(entityType.name().toLowerCase());
                    }
                }
            }
        }
        return completions;
    }
}
