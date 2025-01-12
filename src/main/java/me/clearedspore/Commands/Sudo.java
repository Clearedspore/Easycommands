package me.clearedspore.Commands;

import me.clearedspore.ConfigFiles.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Sudo implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Usage: /sudo <command|message> <player> <command/message args>");
            return true;
        }
        String Prefix = Messages.get().getString("Prefix");
        String action = args[0].toLowerCase();
        String playerName = args[1];
        Player target = Bukkit.getServer().getPlayerExact(playerName);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found: " + playerName);
            return true;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            builder.append(args[i]).append(" ");
        }
        String messageOrCommand = builder.toString().stripTrailing();

        switch (action) {
            case "message":
                target.chat(messageOrCommand);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Prefix + ChatColor.BLUE + "You have let " + target.getName() + " say: " + messageOrCommand));
                break;
            case "command":
                target.performCommand(messageOrCommand);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Prefix + ChatColor.BLUE + "You have let " + target.getName() + " run: " + messageOrCommand));
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Invalid action. Use 'command' or 'message'.");
                break;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("message", "command").stream()
                    .filter(option -> option.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        } else if (args.length == 2) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}