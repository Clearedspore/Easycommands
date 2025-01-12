package me.clearedspore.Features.Logs;

import java.util.List;
import java.util.UUID;

public interface LogAPI {
    void log(UUID playerId, String message);
    List<LogManager.LogEntry> getLogs(UUID playerId);
}
