package me.clearedspore.Logs;

import java.time.Instant;
import java.util.*;

public class LogManager {

    private static final Map<UUID, List<LogEntry>> playerLogs = new HashMap<>();

    public static void log(UUID playerId, String message) {
        playerLogs.putIfAbsent(playerId, new ArrayList<>());
        playerLogs.get(playerId).add(new LogEntry(message, Instant.now()));
    }

    public static List<LogEntry> getLogs(UUID playerId) {
        return playerLogs.getOrDefault(playerId, Collections.emptyList());
    }

    public static class LogEntry {
        private final Instant timestamp;
        private final String message;

        public LogEntry(String message, Instant timestamp) {
            this.timestamp = timestamp;
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public Instant getTimestamp() {
            return timestamp;
        }
    }
}
