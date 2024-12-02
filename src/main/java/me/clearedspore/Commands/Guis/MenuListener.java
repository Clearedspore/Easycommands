package me.clearedspore.Commands.Guis;

import me.clearedspore.easycommands;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MenuListener implements Listener {

    private final easycommands plugin;

    public MenuListener(easycommands plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){

        if(e.getView().getTitle().equals(ChatColor.BLUE + "Custom Items")){

            if(e.isRightClick()) return;

            e.setCancelled(true);

            if (!e.getClick().isLeftClick()) return;

            Player p = (Player) e.getWhoClicked();

            switch (e.getCurrentItem().getType()) {
                case PRISMARINE_SHARD:
                    ItemStack drill = new ItemStack(Material.PRISMARINE_SHARD, 1);
                    ItemMeta drillMeta = drill.getItemMeta();
                    drillMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Pluto Drill");

                    List<String> drillLore = new ArrayList<>();
                    drillLore.add(" ");
                    drillLore.add(ChatColor.LIGHT_PURPLE + "Efficiency: " + ChatColor.WHITE + "10");
                    drillLore.add(ChatColor.LIGHT_PURPLE + "Fortune: " + ChatColor.WHITE + "4");
                    drillLore.add(ChatColor.LIGHT_PURPLE + "Mining Speed: " + ChatColor.WHITE + "30");
                    drillLore.add(" ");
                    drillLore.add(ChatColor.AQUA + "" + ChatColor.BOLD + "Pluto Rarity");
                    drillMeta.setLore(drillLore);

                    drillMeta.addEnchant(Enchantment.EFFICIENCY, 10, true);
                    drillMeta.addEnchant(Enchantment.FORTUNE, 4, true);
                    drillMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);
                    drillMeta.setRarity(ItemRarity.EPIC);
                    drillMeta.setUnbreakable(true);
                    drillMeta.addAttributeModifier(Attribute.PLAYER_BLOCK_BREAK_SPEED, new AttributeModifier("player.blockbreakspeed", 30, AttributeModifier.Operation.ADD_NUMBER));
                    drillMeta.addAttributeModifier(Attribute.PLAYER_MINING_EFFICIENCY, new AttributeModifier("Player.miningefficiency", 30, AttributeModifier.Operation.ADD_NUMBER));

                    drill.setItemMeta(drillMeta);
                    p.getInventory().addItem(drill);
                    p.closeInventory();

                    p.sendMessage(ChatColor.BLUE + "Received " + ChatColor.WHITE + "Pluto Drill");
                    break;
                case NETHERITE_SWORD:
                    ItemStack Mechanic_sword = new ItemStack(Material.NETHERITE_SWORD, 1);
                    ItemMeta swordMeta = Mechanic_sword.getItemMeta();
                    swordMeta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Mechanics sword");

                    List<String> swordLore = new ArrayList<>();
                    swordLore.add(" ");
                    swordLore.add(ChatColor.AQUA + "Mechanic sword");
                    swordLore.add(ChatColor.AQUA + "This sword was made by a mechanic");
                    swordLore.add(ChatColor.AQUA + "Use it good or badly");
                    swordLore.add("");

                    swordMeta.addEnchant(Enchantment.SHARPNESS, 9, true);
                    swordMeta.addEnchant(Enchantment.FIRE_ASPECT, 3, true);
                    swordMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);

                    Mechanic_sword.setItemMeta(swordMeta);
                    p.getInventory().addItem(Mechanic_sword);
                    p.closeInventory();
                    p.sendMessage(ChatColor.BLUE + "Received " + ChatColor.WHITE + "Mechanic Sword");
                    break;
                case BEACON:
                    ItemStack BeaconShop = new ItemStack(Material.BEACON);
                    ItemMeta BeaconMeta = BeaconShop.getItemMeta();
                    BeaconMeta.setDisplayName(ChatColor.AQUA + "BeaconShop");
                    BeaconMeta.setUnbreakable(true);
                    BeaconMeta.setEnchantmentGlintOverride(true);
                    BeaconShop.setItemMeta(BeaconMeta);
                    p.getInventory().addItem(BeaconShop);
                    p.closeInventory();
                    p.sendMessage(ChatColor.BLUE + "Received " + ChatColor.WHITE + "BeaconShop");
                    break;
            }

        }
    }
}
