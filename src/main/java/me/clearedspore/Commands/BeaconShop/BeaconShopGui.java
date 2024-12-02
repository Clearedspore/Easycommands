package me.clearedspore.Commands.BeaconShop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BeaconShopGui implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){
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
        return true;
    }
}


