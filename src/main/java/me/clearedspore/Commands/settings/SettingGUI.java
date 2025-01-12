package me.clearedspore.Commands.settings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.bukkit.Registry.MATERIAL;

public class SettingGUI implements Listener {
    private SettingsManager settingsManager;

    public SettingGUI(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    public void openSettingGUI(Player p) {

        Inventory gui = Bukkit.createInventory(null, 4 * 9, ChatColor.BLUE + "Settings GUI");


        //Buttons
        ItemStack closeButton = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = closeButton.getItemMeta();
        closeMeta.setDisplayName(ChatColor.RED + "Close Menu");
        closeMeta.setLore(Collections.singletonList(ChatColor.WHITE + " \n" + "Left click to close the GUI"));
        hideFlag(closeMeta);

        closeButton.setItemMeta(closeMeta);

        ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta glassMeta = glass.getItemMeta();
        glassMeta.setDisplayName(" ");
        hideFlag(glassMeta);

        glass.setItemMeta(glassMeta);

        //options
        ItemStack logoption = new ItemStack(Material.BELL);
        ItemMeta logMeta = logoption.getItemMeta();
        hideFlag(logMeta);

        ItemStack adminlogsoption = new ItemStack(Material.CLOCK);
        ItemMeta adminlogsMeta = adminlogsoption.getItemMeta();
        hideFlag(adminlogsMeta);

        ItemStack pmoption = new ItemStack(Material.OAK_SIGN);
        ItemMeta pmoptionMeta = pmoption.getItemMeta();
        hideFlag(pmoptionMeta);
        if (p.hasPermission("easycommands.logs")) {
            if (settingsManager.isLogEnabled(p)) {
                logMeta.setItemName(ChatColor.GREEN + "Logs");
                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatColor.WHITE + "| Get ingame logs when a staff member does a staff command");
                lore.add("");
                lore.add(ChatColor.GREEN + " > Receive logs");
                lore.add(ChatColor.RED + "  Don't receive logs");
                logMeta.setLore(lore);
                logoption.setItemMeta(logMeta);
            } else if (!settingsManager.isLogEnabled(p)) {
                logMeta.setItemName(ChatColor.RED + "Logs");
                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatColor.WHITE + "| Get ingame logs when a staff member does a staff command");
                lore.add("");
                lore.add(ChatColor.RED + "  Receive logs");
                lore.add(ChatColor.GREEN + " > Don't receive logs");
                logMeta.setLore(lore);
                logoption.setItemMeta(logMeta);
            }
        }
        if (p.hasPermission("easycommands.adminlogs")) {
            if (settingsManager.isAdminLogsEnabled(p)) {
                adminlogsMeta.setItemName(ChatColor.GREEN + "AdminLogs");
                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatColor.WHITE + "| Get adminlogs ingame such as when the plugin is getting reloaded");
                lore.add("");
                lore.add(ChatColor.GREEN + " > Receive Adminlogs");
                lore.add(ChatColor.RED + "  Don't receive Adminlogs");
                adminlogsMeta.setLore(lore);
                adminlogsoption.setItemMeta(adminlogsMeta);
            } else if (!settingsManager.isAdminLogsEnabled(p)) {
                adminlogsMeta.setItemName(ChatColor.RED + "AdminLogs");
                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatColor.WHITE + "| Get adminlogs ingame such as when the plugin is getting reloaded");
                lore.add("");
                lore.add(ChatColor.RED + "  Receive adminlogs");
                lore.add(ChatColor.GREEN + " > Don't adminreceive logs");
                adminlogsMeta.setLore(lore);
                adminlogsoption.setItemMeta(adminlogsMeta);
            }
        }
        if(settingsManager.ispmsgssettings(p)){
            pmoptionMeta.setItemName(ChatColor.GREEN + "Private messages");
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColor.WHITE + "| Get private messages from other players");
            lore.add("");
            lore.add(ChatColor.GREEN + " > Receive private messages");
            lore.add(ChatColor.RED + "  Don't receive private messages");
            pmoptionMeta.setLore(lore);
            pmoption.setItemMeta(pmoptionMeta);
        } else if(!settingsManager.ispmsgssettings(p)){
            pmoptionMeta.setItemName(ChatColor.RED + "Private messages");
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColor.WHITE + "| Get private messages from other players");
            lore.add("");
            lore.add(ChatColor.RED + "  Receive private messages");
            lore.add(ChatColor.GREEN + " > Don't receive private messages");
            pmoptionMeta.setLore(lore);
            pmoption.setItemMeta(pmoptionMeta);
        }
        for (int i = 27; i < 31; i++) {
            gui.setItem(i, glass);
        }
        for (int i = 32; i < 36; i++) {
            gui.setItem(i, glass);
        }
        gui.setItem(31, closeButton);
        if (p.hasPermission("easycommands.logs")) {
            gui.setItem(0, logoption);
        }
        if (p.hasPermission("easycommands.adminlogs")) {
            gui.setItem(1, adminlogsoption);
        }
        gui.setItem(2, pmoption);

        p.openInventory(gui);
    }

    public void updateGUI(Player p) {
        if (p.getOpenInventory() != null && p.getOpenInventory().getTitle().equals(ChatColor.BLUE + "Settings GUI")) {
            Inventory gui = p.getOpenInventory().getTopInventory();

            ItemStack closeButton = new ItemStack(Material.BARRIER);
            ItemMeta closeMeta = closeButton.getItemMeta();
            closeMeta.setDisplayName(ChatColor.RED + "Close Menu");
            closeMeta.setLore(Collections.singletonList(ChatColor.WHITE + " \n" + "Left click to close the GUI"));
            hideFlag(closeMeta);

            closeButton.setItemMeta(closeMeta);

            ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            ItemMeta glassMeta = glass.getItemMeta();
            glassMeta.setDisplayName(" ");
            hideFlag(glassMeta);

            glass.setItemMeta(glassMeta);

            //options
            ItemStack logoption = new ItemStack(Material.BELL);
            ItemMeta logMeta = logoption.getItemMeta();
            hideFlag(logMeta);

            ItemStack adminlogsoption = new ItemStack(Material.CLOCK);
            ItemMeta adminlogsMeta = adminlogsoption.getItemMeta();
            hideFlag(adminlogsMeta);

            ItemStack pmoption = new ItemStack(Material.OAK_SIGN);
            ItemMeta pmoptionMeta = pmoption.getItemMeta();
            hideFlag(pmoptionMeta);
            if (p.hasPermission("easycommands.logs")) {
                if (settingsManager.isLogEnabled(p)) {
                    logMeta.setItemName(ChatColor.GREEN + "Logs");
                    List<String> lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatColor.WHITE + "| Get ingame logs when a staff member does a staff command");
                    lore.add("");
                    lore.add(ChatColor.GREEN + " > Receive logs");
                    lore.add(ChatColor.RED + "  Don't receive logs");
                    logMeta.setLore(lore);
                    logoption.setItemMeta(logMeta);
                } else if (!settingsManager.isLogEnabled(p)) {
                    logMeta.setItemName(ChatColor.RED + "Logs");
                    List<String> lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatColor.WHITE + "| Get ingame logs when a staff member does a staff command");
                    lore.add("");
                    lore.add(ChatColor.RED + "  Receive logs");
                    lore.add(ChatColor.GREEN + " > Don't receive logs");
                    logMeta.setLore(lore);
                    logoption.setItemMeta(logMeta);
                }
            }
            if (p.hasPermission("easycommands.adminlogs")) {
                if (settingsManager.isAdminLogsEnabled(p)) {
                    adminlogsMeta.setItemName(ChatColor.GREEN + "AdminLogs");
                    List<String> lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatColor.WHITE + "| Get adminlogs ingame such as when the plugin is getting reloaded");
                    lore.add("");
                    lore.add(ChatColor.GREEN + " > Receive Adminlogs");
                    lore.add(ChatColor.RED + "  Don't receive Adminlogs");
                    adminlogsMeta.setLore(lore);
                    adminlogsoption.setItemMeta(adminlogsMeta);
                } else if (!settingsManager.isAdminLogsEnabled(p)) {
                    adminlogsMeta.setItemName(ChatColor.RED + "AdminLogs");
                    List<String> lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatColor.WHITE + "| Get adminlogs ingame such as when the plugin is getting reloaded");
                    lore.add("");
                    lore.add(ChatColor.RED + "  Receive adminlogs");
                    lore.add(ChatColor.GREEN + " > Don't adminreceive logs");
                    adminlogsMeta.setLore(lore);
                    adminlogsoption.setItemMeta(adminlogsMeta);
                }
            }
            if(settingsManager.ispmsgssettings(p)){
                pmoptionMeta.setItemName(ChatColor.GREEN + "Private messages");
                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatColor.WHITE + "| Get private messages from other players");
                lore.add("");
                lore.add(ChatColor.GREEN + " > Receive private messages");
                lore.add(ChatColor.RED + "  Don't receive private messages");
                pmoptionMeta.setLore(lore);
                pmoption.setItemMeta(pmoptionMeta);
            } else if(!settingsManager.ispmsgssettings(p)){
                pmoptionMeta.setItemName(ChatColor.RED + "Private messages");
                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatColor.WHITE + "| Get private messages from other players");
                lore.add("");
                lore.add(ChatColor.RED + "  Receive private messages");
                lore.add(ChatColor.GREEN + " > Don't receive private messages");
                pmoptionMeta.setLore(lore);
                pmoption.setItemMeta(pmoptionMeta);
            }

            for (int i = 27; i < 31; i++) {
                gui.setItem(i, glass);
            }
            for (int i = 32; i < 36; i++) {
                gui.setItem(i, glass);
            }
            gui.setItem(31, closeButton);
            if (p.hasPermission("easycommands.logs")) {
                gui.setItem(0, logoption);
            }
            if (p.hasPermission("easycommands.adminlogs")) {
                gui.setItem(1, adminlogsoption);
            }
            gui.setItem(2, pmoption);

            p.updateInventory();
        }
    }


    private void hideFlag(ItemMeta itemMeta) {
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(ChatColor.BLUE + "Settings GUI")) {
            e.setCancelled(true);
            if (!e.getClick().isLeftClick()) return;

            Player p = (Player) e.getWhoClicked();

            switch (e.getCurrentItem().getType()) {
                case BELL:
                    if(settingsManager.isLogEnabled(p)){
                        settingsManager.setLogSetting(p, false);
                    } else {
                        settingsManager.setLogSetting(p, true);
                    }
                    updateGUI(p);
                    break;
                case CLOCK:
                    if(settingsManager.isAdminLogsEnabled(p)){
                        settingsManager.setAdminLogsSetting(p, false);
                    } else {
                        settingsManager.setAdminLogsSetting(p, true);
                    }
                    updateGUI(p);
                    break;
                case OAK_SIGN:
                    if(settingsManager.ispmsgssettings(p)){
                        settingsManager.setpmsgssettings(p, false);
                    } else {
                        settingsManager.setpmsgssettings(p, true);
                    }
                    updateGUI(p);
                    break;
                case BARRIER:
                    p.closeInventory();
                    p.sendMessage(ChatColor.GREEN + "Settings updated!");
                    break;
            }
        }
    }
}
