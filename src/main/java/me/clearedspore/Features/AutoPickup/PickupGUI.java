package me.clearedspore.Features.AutoPickup;

import me.clearedspore.Utils.PaginatedMenuHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PickupGUI implements Listener {
    private final PickupManager PickupManager;

    public PickupGUI(PickupManager autoPickupManager) {
        this.PickupManager = autoPickupManager;
    }
    public void OpenGUI(Player p) {
        Inventory GUI = Bukkit.createInventory(null, 36, ChatColor.GREEN + "AutoPickups");

        ItemStack AutoPickup = new ItemStack(Material.DROPPER);
        ItemMeta AutoPickupMeta = AutoPickup.getItemMeta();


        if (PickupManager.isAutoPickupEnabled(p)) {
            AutoPickupMeta.setDisplayName(ChatColor.GREEN + "Autopickup");

            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColor.BLUE + "Autopickup: " + ChatColor.GREEN + "Enabled");
            lore.add("");
            AutoPickupMeta.setLore(lore);
        } else {
            AutoPickupMeta.setDisplayName(ChatColor.RED + "Autopickup");
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColor.BLUE + "Autopickup: " + ChatColor.RED + "Disabled");
            lore.add("");
            AutoPickupMeta.setLore(lore);
        }
        AutoPickup.setItemMeta(AutoPickupMeta);

        ItemStack AutoSmelt = new ItemStack(Material.FURNACE);
        ItemMeta AutoSmeltMeta = AutoSmelt.getItemMeta();

        if(PickupManager.isAutoSmeltEnabled(p)){
            AutoSmeltMeta.setDisplayName(ChatColor.GREEN + "AutoSmelt");

            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColor.BLUE + "AutoSmelt: " + ChatColor.GREEN + "Enabled");
            lore.add("");
            AutoSmeltMeta.setLore(lore);
        } else {
            AutoSmeltMeta.setDisplayName(ChatColor.RED + "AutoSmelt");
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColor.BLUE + "AutoSmelt: " + ChatColor.RED + "Disabled");
            lore.add("");
            AutoSmeltMeta.setLore(lore);
        }
        AutoSmelt.setItemMeta(AutoSmeltMeta);

        ItemStack Close = new ItemStack(Material.BARRIER);
        ItemMeta CloseMeta = Close.getItemMeta();

        CloseMeta.setDisplayName(ChatColor.RED + "Close GUI");

        Close.setItemMeta(CloseMeta);

        if(p.hasPermission("easycommands.pickup.autopickup")){
            GUI.setItem(0, AutoPickup);
        }
        if(p.hasPermission("easycommands.pickup.autosmelt")){
            GUI.setItem(1, AutoSmelt);
        }
        GUI.setItem(9, Close);

        p.openInventory(GUI);
    }

    public void updateItems(Player p) {
        if (p.getOpenInventory() != null && p.getOpenInventory().getTitle().equals(ChatColor.GREEN + "AutoPickups")) {
            Inventory GUI = p.getOpenInventory().getTopInventory();

            ItemStack AutoPickup = new ItemStack(Material.DROPPER);
            ItemMeta AutoPickupMeta = AutoPickup.getItemMeta();

            if (PickupManager.isAutoPickupEnabled(p)) {
                AutoPickupMeta.setDisplayName(ChatColor.GREEN + "Autopickup");

                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatColor.BLUE + "Autopickup: " + ChatColor.GREEN + "Enabled");
                lore.add("");
                AutoPickupMeta.setLore(lore);
            } else {
                AutoPickupMeta.setDisplayName(ChatColor.RED + "Autopickup");
                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatColor.BLUE + "Autopickup: " + ChatColor.RED + "Disabled");
                lore.add("");
                AutoPickupMeta.setLore(lore);
            }
            AutoPickup.setItemMeta(AutoPickupMeta);

            ItemStack AutoSmelt = new ItemStack(Material.FURNACE);
            ItemMeta AutoSmeltMeta = AutoSmelt.getItemMeta();

            if(PickupManager.isAutoSmeltEnabled(p)){
                AutoSmeltMeta.setDisplayName(ChatColor.GREEN + "AutoSmelt");

                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatColor.BLUE + "AutoSmelt: " + ChatColor.GREEN + "Enabled");
                lore.add("");
                AutoSmeltMeta.setLore(lore);
            } else {
                AutoSmeltMeta.setDisplayName(ChatColor.RED + "AutoSmelt");
                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatColor.BLUE + "AutoSmelt: " + ChatColor.RED + "Disabled");
                lore.add("");
                AutoSmeltMeta.setLore(lore);
            }
            AutoSmelt.setItemMeta(AutoSmeltMeta);

            if(p.hasPermission("easycommands.pickup.autopickup")){
                GUI.setItem(0, AutoPickup);
            }
            if(p.hasPermission("easycommands.pickup.autosmelt")){
                GUI.setItem(1, AutoSmelt);
            }
            p.updateInventory();
        }
    }

    @EventHandler
    public void handleMenuClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(ChatColor.GREEN + "AutoPickups")) {
            e.setCancelled(true);
            if (!e.getClick().isLeftClick()) return;

            Player p = (Player) e.getWhoClicked();

            switch (e.getCurrentItem().getType()) {
                case DROPPER:
                    if(PickupManager.isAutoPickupEnabled(p)){
                        PickupManager.SetAutpickup(p, false);
                    } else {
                        PickupManager.SetAutpickup(p, true);
                    }
                    updateItems(p);
                    break;
                case BARRIER:
                    p.closeInventory();
                    break;
                case FURNACE:
                    if(PickupManager.isAutoSmeltEnabled(p)){
                        PickupManager.SetAutoSmelt(p, false);
                    } else {
                        PickupManager.SetAutoSmelt(p, true);
                    }
                    updateItems(p);
            }
        }
    }
}
