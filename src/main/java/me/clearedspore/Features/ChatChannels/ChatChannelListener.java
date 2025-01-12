package me.clearedspore.Features.ChatChannels;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatChannelListener implements Listener {
    private final ChatChannelManager channelManager;

    public ChatChannelListener(ChatChannelManager channelManager) {
        this.channelManager = channelManager;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        ChatChannel currentChannel = channelManager.getPlayerChannel(player);

        if (currentChannel != null && currentChannel.isActive(player)) {
            event.setCancelled(true);
            String message = ChatColor.translateAlternateColorCodes('&', currentChannel.getPrefix() + " " + player.getName() + ": " + event.getMessage());
            for (Player p : currentChannel.getActivePlayers()) {
                p.sendMessage(message);
            }
        } else {
            event.setCancelled(false);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ChatChannel currentChannel = channelManager.getPlayerChannel(player);

        if (currentChannel != null) {
            currentChannel.togglePlayer(player, channelManager);
        }
    }
}