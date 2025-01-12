package me.clearedspore.Commands.Gamemodes;

import me.clearedspore.Commands.settings.SettingsManager;
import me.clearedspore.ConfigFiles.Messages;
import me.clearedspore.Features.Logs.LogManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Gamemode implements CommandExecutor, TabCompleter {

    private final SettingsManager settingsManager;

    public Gamemode(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /gamemode <mode> [player]");
            return true;
        }

        GameMode gameMode;
        try {
            gameMode = GameMode.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.sendMessage(ChatColor.RED + "Invalid gamemode!");
            return true;
        }

        if (args.length == 1) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (hasPermissionForGamemode(player, gameMode)) {
                    player.setGameMode(gameMode);
                    String GameMode = Messages.get().getString("Gamemode");
                    String Prefix = Messages.get().getString("Prefix");
                    GameMode = GameMode.replace("%prefix%", Prefix);
                    GameMode = GameMode.replace("%gamemode%",  gameMode.name().toLowerCase());
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', GameMode));

                    LogManager.getInstance().log(player.getUniqueId(),ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " Has changed their gamemode to " + gameMode.name());
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        if(settingsManager.isLogEnabled(online)) {
                            if (online.hasPermission("easycommands.logs"))
                                online.sendMessage(ChatColor.GRAY + "[" + ChatColor.GRAY + player.getDisplayName() + ChatColor.GRAY + " has changed their gamemode to " + gameMode.name() + "]");
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have permission to change to this gamemode!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Console must specify a player!");
            }
        } else {
            Player target = Bukkit.getPlayerExact(args[1]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Player not found!");
                return true;
            }

            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("easycommands.gamemode.other") && hasPermissionForGamemode(player, gameMode)) {
                    target.setGameMode(gameMode);
                    String GameModeT = Messages.get().getString("GamemodeTarget");
                    String Prefix = Messages.get().getString("Prefix");
                    GameModeT = GameModeT.replace("%prefix%", Prefix);
                    GameModeT = GameModeT.replace("%gamemode%", gameMode.name().toLowerCase());
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', GameModeT));
                    String GameMode = Messages.get().getString("GamemodeOther");
                    GameMode = GameMode.replace("%prefix%", Prefix);
                    GameMode = GameMode.replace("%gamemode%", gameMode.name().toLowerCase());
                    GameMode = GameMode.replace("%target%", target.getDisplayName());
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', GameMode));
                    LogManager.getInstance().log(player.getUniqueId(),ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " Changed " + target.getDisplayName() + "'s gamemode to " + gameMode.name().toLowerCase());
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        if(settingsManager.isLogEnabled(online)) {
                            if (online.hasPermission("easycommands.logs"))
                                online.sendMessage(ChatColor.GRAY + "[" + player.getDisplayName() + "has changed the gamemode for " + target.getDisplayName() + " to" + gameMode.name() + "]");
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have permission to change other players' gamemodes!");
                }
            } else {
                target.setGameMode(gameMode);
                String GameModeT = Messages.get().getString("GamemodeTarget");
                String Prefix = Messages.get().getString("Prefix");
                GameModeT = GameModeT.replace("%prefix%", Prefix);
                GameModeT = GameModeT.replace("%gamemode%", gameMode.name().toLowerCase());
                target.sendMessage(ChatColor.translateAlternateColorCodes('&', GameModeT));
                String GameMode = Messages.get().getString("GamemodeOther");
                GameMode = GameMode.replace("%prefix%", Prefix);
                GameMode = GameMode.replace("%gamemode%", gameMode.name().toLowerCase());
                GameMode = GameMode.replace("%target%", target.getDisplayName());
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', GameMode));
            }
        }
        return true;
    }

    private boolean hasPermissionForGamemode(Player player, GameMode gameMode) {
        switch (gameMode) {
            case CREATIVE:
                return player.hasPermission("easycommands.gamemode.creative");
            case SURVIVAL:
                return player.hasPermission("easycommands.gamemode.survival");
            case ADVENTURE:
                return player.hasPermission("easycommands.gamemode.adventure");
            case SPECTATOR:
                return player.hasPermission("easycommands.gamemode.spectator");
            default:
                return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            List<String> gamemodes = new ArrayList<>();
            if (sender.hasPermission("easycommands.gamemode.creative")) {
                gamemodes.add("creative");
            }
            if (sender.hasPermission("easycommands.gamemode.survival")) {
                gamemodes.add("survival");
            }
            if (sender.hasPermission("easycommands.gamemode.adventure")) {
                gamemodes.add("adventure");
            }
            if (sender.hasPermission("easycommands.gamemode.spectator")) {
                gamemodes.add("spectator");
            }
            return gamemodes;
        } else if (args.length == 2 && sender.hasPermission("easycommands.gamemode.other")) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
