package me.clearedspore.Commands.Teleport;

import java.util.UUID;

public class TeleportRequest {
    private final UUID requesterId;
    private final UUID targetId;
    private final long expiryTime;

    public TeleportRequest(UUID requesterId, UUID targetId, long expiryTime) {
        this.requesterId = requesterId;
        this.targetId = targetId;
        this.expiryTime = expiryTime;
    }

    public UUID getRequesterId() {
        return requesterId;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}
