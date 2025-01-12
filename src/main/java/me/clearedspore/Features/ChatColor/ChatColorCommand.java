package me.clearedspore.Features.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatColorCommand implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){
            openChatColorGUI(p);

        }
        return true;
    }
    private static final Map<Player, ChatSettings> playerChatSettings = new HashMap<>();

    public static void openChatColorGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, "Chat Colors");

        // Add color options
        ChatColor[] colors = {
                ChatColor.RED, ChatColor.GREEN, ChatColor.BLUE,
                ChatColor.YELLOW, ChatColor.AQUA, ChatColor.GOLD,
                ChatColor.LIGHT_PURPLE, ChatColor.DARK_GRAY, ChatColor.WHITE
        };

        DyeColor[] dyeColors = {
                DyeColor.RED, DyeColor.GREEN, DyeColor.BLUE,
                DyeColor.YELLOW, DyeColor.LIGHT_BLUE, DyeColor.ORANGE,
                DyeColor.MAGENTA, DyeColor.GRAY, DyeColor.WHITE
        };

        Material[] woolMaterials = {
                Material.RED_WOOL,
                Material.GREEN_WOOL,
                Material.BLUE_WOOL,
                Material.YELLOW_WOOL,
                Material.LIGHT_BLUE_WOOL,
                Material.ORANGE_WOOL,
                Material.MAGENTA_WOOL,
                Material.GRAY_WOOL,
                Material.WHITE_WOOL
        };

        for (int i = 0; i < colors.length; i++) {
            ChatColor color = colors[i];
            Material woolMaterial = woolMaterials[i];

            // Check permission
            if (!player.hasPermission("easycommands.chatcolor." + color.name().toLowerCase())) {
                continue;
            }

            ItemStack item = new ItemStack(woolMaterial, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(color + color.name().toLowerCase());
            item.setItemMeta(meta);
            gui.setItem(i, item);
        }

        ItemStack resetItem = createFormattingItem(Material.BARRIER, ChatColor.RED + "Reset");
        ItemStack Done = createFormattingItem(Material.GREEN_WOOL, ChatColor.GREEN + "Confirm");

        if (player.hasPermission("easycommands.chatcolor.bold")) {
            ItemStack boldItem = createFormattingItem(Material.ANVIL, ChatColor.BOLD + "Bold");
            gui.setItem(18, boldItem);
        }
        if (player.hasPermission("easycommands.chatcolor.italic")) {
            ItemStack italicItem = createFormattingItem(Material.FEATHER, ChatColor.ITALIC + "Italic");
            gui.setItem(19, italicItem);
        }
        if (player.hasPermission("easycommands.chatcolor.underline")) {
            ItemStack underlineItem = createFormattingItem(Material.STRING, ChatColor.UNDERLINE + "Underline");
            gui.setItem(20, underlineItem);
        }
        gui.setItem(26, resetItem);
        gui.setItem(25, Done);

        player.openInventory(gui);
    }

    private static ItemStack createFormattingItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public static void handleGUIClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Chat Colors")) {
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem == null || !clickedItem.hasItemMeta()) {
                return;
            }

            String displayName = clickedItem.getItemMeta().getDisplayName();

            ChatSettings settings = playerChatSettings.getOrDefault(player, new ChatSettings());

            if (displayName.contains("Bold")) {
                settings.setBold(!settings.isBold());
            } else if (displayName.contains("Italic")) {
                settings.setItalic(!settings.isItalic());
            } else if (displayName.contains("Underline")) {
                settings.setUnderline(!settings.isUnderline());
            } else if (displayName.contains("Reset")) {
                player.closeInventory();
                player.sendMessage(ChatColor.RED + "Your Chatcolor has been updated!");
                settings.reset();
            } else if(displayName.contains("Confirm")) {
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Chatcolor updated");
            } else {
                for (ChatColor color : ChatColor.values()) {
                    if (displayName.contains(color.name().toLowerCase())) {
                        if (!player.hasPermission("easycommands.chatcolor." + color.name().toLowerCase())) {
                            player.sendMessage(ChatColor.RED + "You do not have permission for this color.");
                            return;
                        }

                        settings.setColor(color);
                        break;
                    }
                }
            }

            playerChatSettings.put(player, settings);
        }
    }

    public static ChatSettings getPlayerChatSettings(Player player) {
        return playerChatSettings.getOrDefault(player, new ChatSettings());
    }
}
