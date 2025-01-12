package me.clearedspore.Features.Logs;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogManager implements LogAPI {
    private static final LogManager INSTANCE = new LogManager();
    private static final String LOG_FILE_PATH = "plugins/Easycommands/ingamelogs.txt";
    private Duration logRetentionDuration;

    public LogManager() {
        loadConfiguration();
        ensureLogFileExists();
    }

    public static LogManager getInstance() {
        return INSTANCE;
    }

    @Override
    public void log(UUID playerId, String message) {
        LogEntry logEntry = new LogEntry(playerId, message, Instant.now());
        appendLogToFile(logEntry);
    }

    @Override
    public List<LogEntry> getLogs(UUID playerId) {
        List<LogEntry> logs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LogEntry logEntry = LogEntry.fromString(line);
                if (logEntry != null && logEntry.getPlayerId().equals(playerId)) {
                    logs.add(logEntry);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logs;
    }

    public void deleteOldLogs() {
        Instant now = Instant.now();
        List<LogEntry> validLogs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LogEntry logEntry = LogEntry.fromString(line);
                if (logEntry != null && Duration.between(logEntry.getTimestamp(), now).compareTo(logRetentionDuration) <= 0) {
                    validLogs.add(logEntry);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH))) {
            for (LogEntry logEntry : validLogs) {
                writer.write(logEntry.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void appendLogToFile(LogEntry logEntry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write(logEntry.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ensureLogFileExists() {
        try {
            File logFile = new File(LOG_FILE_PATH);
            if (!logFile.exists()) {
                if (logFile.createNewFile()) {
                    System.out.println("Log file created: " + LOG_FILE_PATH);
                } else {
                    System.out.println("Failed to create log file: " + LOG_FILE_PATH);
                }
            } else {
                System.out.println("Log file already exists: " + LOG_FILE_PATH);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadConfiguration() {
        // Replace this with actual config reading logic
        String logDeleteConfig = "7 days"; // Example default value
        logRetentionDuration = parseDuration(logDeleteConfig);
    }

    private Duration parseDuration(String durationStr) {
        Pattern pattern = Pattern.compile("(\\d+)\\s*(second|minute|hour|day|week)s?");
        Matcher matcher = pattern.matcher(durationStr.toLowerCase());
        if (matcher.matches()) {
            int amount = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);
            switch (unit) {
                case "second":
                    return Duration.ofSeconds(amount);
                case "minute":
                    return Duration.ofMinutes(amount);
                case "hour":
                    return Duration.ofHours(amount);
                case "day":
                    return Duration.ofDays(amount);
                case "week":
                    return Duration.ofDays(amount * 7);
                default:
                    throw new IllegalArgumentException("Invalid time unit");
            }
        } else {
            throw new IllegalArgumentException("Invalid duration format");
        }
    }

    public static class LogEntry {
        private final UUID playerId;
        private final Instant timestamp;
        private final String message;

        public LogEntry(UUID playerId, String message, Instant timestamp) {
            this.playerId = playerId;
            this.timestamp = timestamp;
            this.message = message;
        }

        public UUID getPlayerId() {
            return playerId;
        }

        public String getMessage() {
            return message;
        }

        public Instant getTimestamp() {
            return timestamp;
        }

        @Override
        public String toString() {
            return playerId + "," + timestamp + "," + message;
        }

        public static LogEntry fromString(String logString) {
            String[] parts = logString.split(",", 3);
            if (parts.length == 3) {
                try {
                    UUID playerId = UUID.fromString(parts[0]);
                    Instant timestamp = Instant.parse(parts[1]);
                    String message = parts[2];
                    return new LogEntry(playerId, message, timestamp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}