package me.clearedspore.Utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public abstract class PaginatedMenuHandler {
    protected int currentPage = 0;

    public abstract Inventory getMenu(Player p);

    public void nextPage() {
        currentPage++;
    }

    public void previousPage() {
        if (currentPage > 0) {
            currentPage--;
        }
    }

    protected ItemStack createMenuItem(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public abstract void handleMenuClick(InventoryClickEvent event);
}
