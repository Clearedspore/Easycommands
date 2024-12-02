package me.clearedspore.Commands.BeaconShop;

import me.clearedspore.easycommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


public class BeaconShopListener implements Listener {

    public BeaconShopListener(easycommands easycommands) {
    }

    @EventHandler
    public void OpenBeaconShop(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock() != null && e.getClickedBlock().getType().equals(Material.BEACON)) {
                e.setCancelled(true);

                Inventory BeaconShop = Bukkit.createInventory(p, 9, ChatColor.AQUA + "Beacon Shop");

                ItemStack Copper = new ItemStack(Material.RAW_COPPER);
                ItemStack Gold = new ItemStack(Material.RAW_GOLD);
                ItemStack Kit = new ItemStack(Material.SHULKER_BOX);
                ItemStack Diamond = new ItemStack(Material.DIAMOND);
                ItemStack Emerald = new ItemStack(Material.EMERALD);

                ItemMeta CopperMeta = Copper.getItemMeta();
                ItemMeta GoldMeta = Gold.getItemMeta();
                ItemMeta KitMeta = Kit.getItemMeta();
                ItemMeta DiamondMeta = Diamond.getItemMeta();
                ItemMeta EmeraldMeta = Emerald.getItemMeta();

                CopperMeta.setDisplayName(ChatColor.GOLD + "Copper");
                GoldMeta.setDisplayName(ChatColor.YELLOW + "Gold");
                KitMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "CustomKit");
                DiamondMeta.setDisplayName(ChatColor.AQUA + "Diamond");
                EmeraldMeta.setDisplayName(ChatColor.GREEN + "Emerald");

                List<String> CopperLore = new ArrayList<>();
                CopperLore.add(ChatColor.WHITE + "Left click to sell!");
                CopperMeta.setLore(CopperLore);

                List<String> GoldLore = new ArrayList<>();
                GoldLore.add(ChatColor.WHITE + "Left click to sell!");
                GoldMeta.setLore(GoldLore);

                List<String> DiamondLore = new ArrayList<>();
                DiamondLore.add(ChatColor.WHITE + "Left click to sell!");
                DiamondMeta.setLore(DiamondLore);

                List<String> EmeraldLore = new ArrayList<>();
                EmeraldLore.add(ChatColor.WHITE + "Left click to sell!");

                Copper.setAmount(2);
                Gold.setAmount(1);
                Diamond.setAmount(3);
                Emerald.setAmount(4);

                EmeraldMeta.setLore(EmeraldLore);
                Copper.setItemMeta(CopperMeta);
                Gold.setItemMeta(GoldMeta);
                Diamond.setItemMeta(DiamondMeta);
                Emerald.setItemMeta(EmeraldMeta);
                Kit.setItemMeta(KitMeta);

                BeaconShop.setItem(2, Copper);
                BeaconShop.setItem(3, Gold);
                BeaconShop.setItem(4, Kit);
                BeaconShop.setItem(5, Diamond);
                BeaconShop.setItem(6, Emerald);

                p.openInventory(BeaconShop);
            }
        }
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equals(ChatColor.AQUA + "Beacon Shop")) {
            e.setCancelled(true); // Prevent any default inventory interaction
            if (!e.getClick().isLeftClick()) return; // Only proceed on left clicks

            ItemStack Token = new ItemStack(Material.SUNFLOWER);
            ItemMeta TokenMeta = Token.getItemMeta();

            TokenMeta.setDisplayName(ChatColor.YELLOW + "Token");
            TokenMeta.setEnchantmentGlintOverride(true);
            TokenMeta.setUnbreakable(true);
            Token.setItemMeta(TokenMeta);

            switch (e.getCurrentItem().getType()) {
                case RAW_COPPER:
                    if (p.getInventory().contains(Material.RAW_COPPER, 2)) {
                        removeItems(p, Material.RAW_COPPER, 2);
                        p.getInventory().addItem(Token);
                    } else {
                        p.sendMessage(ChatColor.RED + "You don't have enough Copper to sell!");
                    }
                    break;

                case RAW_GOLD:
                    if (p.getInventory().contains(Material.RAW_GOLD, 1)) {
                        removeItems(p, Material.RAW_GOLD, 1); // Remove exactly 1 gold
                        p.getInventory().addItem(Token);
                    } else {
                        p.sendMessage(ChatColor.RED + "You don't have enough Gold to sell!");
                    }
                    break;

                case EMERALD:
                    if (p.getInventory().contains(Material.EMERALD, 4)) {
                        removeItems(p, Material.EMERALD, 4); // Remove exactly 4 emeralds
                        p.getInventory().addItem(Token);
                    } else {
                        p.sendMessage(ChatColor.RED + "You don't have enough Emeralds to sell!");
                    }
                    break;

                case DIAMOND:
                    if (p.getInventory().contains(Material.DIAMOND, 3)) {
                        removeItems(p, Material.DIAMOND, 3); // Remove exactly 3 diamonds
                        p.getInventory().addItem(Token);
                    } else {
                        p.sendMessage(ChatColor.RED + "You don't have enough Diamonds to sell!");
                    }
                    break;
            }
        }
    }
    private void removeItems(Player player, Material material, int amount) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == material) {
                int itemAmount = item.getAmount();

                if (itemAmount > amount) {
                    item.setAmount(itemAmount - amount);
                    break;
                } else {
                    amount -= itemAmount;
                    player.getInventory().removeItem(item);
                    if (amount == 0) break;
                }
            }
        }
    }

}
