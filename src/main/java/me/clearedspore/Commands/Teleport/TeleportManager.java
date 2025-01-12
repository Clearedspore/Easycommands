package me.clearedspore.Commands.Teleport;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeleportManager {
    private final Map<UUID, TeleportRequest> teleportRequests = new HashMap<>();
    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public void addRequest(UUID requesterId, UUID targetId) {
        teleportRequests.put(targetId, new TeleportRequest(requesterId, targetId, System.currentTimeMillis() + 60000));
    }

    public TeleportRequest getRequest(UUID targetId) {
        return teleportRequests.get(targetId);
    }

    public void removeRequest(UUID targetId) {
        teleportRequests.remove(targetId);
    }

    public boolean isOnCooldown(UUID requesterId) {
        return cooldowns.containsKey(requesterId) && System.currentTimeMillis() < cooldowns.get(requesterId);
    }

    public void setCooldown(UUID requesterId) {
        cooldowns.put(requesterId, System.currentTimeMillis() + 120000);
    }
}
