package me.clearedspore.Features.Logs;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GetLogsCommand implements CommandExecutor, TabCompleter {
    private static final int LOGS_PER_PAGE = 4;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Usage: /getlogs <player> [page]");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found.");
            return true;
        }

        UUID targetUUID = target.getUniqueId();
        List<LogManager.LogEntry> logs = LogManager.getInstance().getLogs(targetUUID);

        if (logs.isEmpty()) {
            sender.sendMessage("No logs found for " + target.getName());
            return true;
        }

        int page = 1;
        if (args.length > 1) {
            try {
                page = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage("Invalid page number.");
                return true;
            }
        }

        int totalPages = (int) Math.ceil((double) logs.size() / LOGS_PER_PAGE);
        if (page > totalPages || page < 1) {
            sender.sendMessage("Invalid page number. Total pages: " + totalPages);
            return true;
        }

        sender.sendMessage("§a----------------------");
        sender.sendMessage("§aLogs of " + target.getName() + " (Page " + page + " of " + totalPages + ")");
        sender.sendMessage("§a----------------------");

        // Reverse ordering: Latest logs are displayed first
        int startIndex = logs.size() - (page * LOGS_PER_PAGE);
        int endIndex = Math.min(startIndex + LOGS_PER_PAGE, logs.size());
        if (startIndex < 0) startIndex = 0;

        for (int i = endIndex - 1; i >= startIndex; i--) {
            LogManager.LogEntry entry = logs.get(i);
            String timeAgo = TimeUtils.formatTimeAgo(entry.getTimestamp());
            sender.sendMessage("§7(" + timeAgo + ") §a| §f" + entry.getMessage());
        }

        sender.sendMessage("§a----------------------");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {

            for (Player player : Bukkit.getOnlinePlayers()) {
                suggestions.add(player.getName());
            }
        } else if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                UUID targetUUID = target.getUniqueId();
                List<LogManager.LogEntry> logs = LogManager.getInstance().getLogs(targetUUID);

                if (!logs.isEmpty()) {
                    int totalPages = (int) Math.ceil((double) logs.size() / LOGS_PER_PAGE);
                    for (int i = 1; i <= totalPages; i++) {
                        suggestions.add(String.valueOf(i));
                    }
                }
            }
        }

        return suggestions;
    }
}