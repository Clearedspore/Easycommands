package me.clearedspore.Commands.Guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CustomItems implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){

            //rows for a menu
            //9, 18, 27, 36, 45, 54
            Inventory menu = Bukkit.createInventory(p, 18, ChatColor.BLUE + "Custom Items");

            //buttons
            ItemStack Drill = new ItemStack(Material.PRISMARINE_SHARD);
            ItemStack Mechanic_sword = new ItemStack(Material.NETHERITE_SWORD);


            //item metadata
            ItemMeta drillMeta = Drill.getItemMeta();
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
            Drill.setItemMeta(drillMeta);

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




            //add items in menu

            menu.setItem(0, Drill);
            menu.setItem(1, Mechanic_sword);

            p.openInventory(menu);


        }
        return true;
    }
}
