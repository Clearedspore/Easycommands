package me.clearedspore.Features.Logs;

import java.time.Duration;
import java.time.Instant;

public class TimeUtils {

    public static String formatTimeAgo(Instant timestamp) {
        Duration duration = Duration.between(timestamp, Instant.now());

        long seconds = duration.getSeconds();
        if (seconds < 60) {
            return seconds + "s ago";
        }

        long minutes = seconds / 60;
        if (minutes < 60) {
            return minutes + "m ago";
        }

        long hours = minutes / 60;
        if (hours < 24) {
            return hours + "h ago";
        }

        long days = hours / 24;
        return days + "d ago";
    }
}
