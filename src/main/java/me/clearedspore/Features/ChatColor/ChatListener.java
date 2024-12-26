package me.clearedspore.Features.ChatColor;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        ChatSettings settings = ChatColorCommand.getPlayerChatSettings(player);

        String formattedMessage = settings.applyFormatting(event.getMessage());
        event.setMessage(formattedMessage);
    }
    public static class InventoryClickListener implements Listener {
        @EventHandler
        public void onInventoryClick(InventoryClickEvent event) {
            ChatColorCommand.handleGUIClick(event);
        }
    }
}