package me.clearedspore.Features.ChatChannels;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatChannel {
    private final List<String> commands;
    private final String prefix;
    private final String permission;
    private final Set<Player> activePlayers = new HashSet<>();

    public ChatChannel(List<String> commands, String prefix, String permission) {
        this.commands = commands;
        this.prefix = prefix;
        this.permission = permission;
    }

    public List<String> getCommands() {
        return commands;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getPermission() {
        return permission;
    }

    public boolean isActive(Player player) {
        return activePlayers.contains(player);
    }

    public void togglePlayer(Player player, ChatChannelManager channelManager) {
        if (isActive(player)) {
            activePlayers.remove(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.BLUE + "You have left the " + getPrefix() + " channel."));
        } else {
            for (ChatChannel channel : channelManager.getChannels().values()) {
                if (channel.isActive(player)) {
                    channel.activePlayers.remove(player);
                }
            }
            activePlayers.add(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.BLUE + "You have joined the " + getPrefix() + " channel."));
        }
    }

    public Set<Player> getActivePlayers() {
        return activePlayers;
    }
}