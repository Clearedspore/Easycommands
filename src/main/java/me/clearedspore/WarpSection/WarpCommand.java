package me.clearedspore.WarpSection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class WarpCommand implements CommandExecutor, Listener {
    private final WarpManager warpManager;

    public WarpCommand(WarpManager warpManager) {
        this.warpManager = warpManager;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("only players can do this command!");
            return true;
        }
        Player p = (Player) sender;
        if (args.length != 1) {
            openGUI(p, 0);
            return true;
        }

        String warpName = args[0];
        Location location = warpManager.getWarp(warpName);

        if(p.hasPermission("easycommands.warp.warp." + warpName)) {
            if(location != null) {
                p.teleport(warpManager.getWarp(warpName));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9You have been teleported to &f" + warpName));
                return true;
            } else {
                p.sendMessage(ChatColor.RED + "Warp does not exist!");
            }
        } else if(!p.hasPermission("easycommands.warp.warp." + warpName)){
            p.sendMessage(ChatColor.RED + "You don't have permission to teleport to here!");
            return true;
        }
        return true;
    }

    public void openGUI(Player p, int page) {

        Set<String> warps = warpManager.getWarpNames();
        List<String> warpList = new ArrayList<>(warps);


        int totalPages = (int) Math.ceil((double) warpList.size() / 27);

        page = Math.max(0, Math.min(page, totalPages - 1));


        Inventory gui = Bukkit.createInventory(null, 36, ChatColor.BLUE + "warps");


        int startIndex = page * 27;
        int endIndex = Math.min(startIndex + 27, warpList.size());
        for (int i = startIndex, slot = 0; i < endIndex; i++, slot++) {
            String warpName = warpList.get(i);
            ItemStack warpItem = new ItemStack(Material.PAPER);
            ItemMeta warpMeta = warpItem.getItemMeta();

            if (warpMeta != null) {
                warpMeta.setDisplayName(ChatColor.YELLOW + warpName);
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "Click to teleport!");
                warpMeta.setLore(lore);
                warpItem.setItemMeta(warpMeta);
            }


            gui.setItem(slot, warpItem);
        }


        ItemStack closeButton = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = closeButton.getItemMeta();
        if (closeMeta != null) {
            closeMeta.setDisplayName(ChatColor.RED + "Close");
            closeButton.setItemMeta(closeMeta);
        }
        gui.setItem(31, closeButton);


        if (page > 0) {
            ItemStack previousButton = new ItemStack(Material.ARROW);
            ItemMeta prevMeta = previousButton.getItemMeta();
            if (prevMeta != null) {
                prevMeta.setDisplayName(ChatColor.BLUE + "Previous Page");
                prevMeta.setLore(Collections.singletonList(ChatColor.WHITE + "current page " + page));
                previousButton.setItemMeta(prevMeta);
            }
            gui.setItem(27, previousButton);
        }


        if (page < totalPages - 1) {
            ItemStack nextButton = new ItemStack(Material.ARROW);
            ItemMeta nextMeta = nextButton.getItemMeta();
            if (nextMeta != null) {
                nextMeta.setDisplayName(ChatColor.BLUE + "Next Page");
                nextMeta.setLore(Collections.singletonList(ChatColor.WHITE + "Current page " + page));
                nextButton.setItemMeta(nextMeta);
            }
            gui.setItem(35, nextButton);
        }

        p.openInventory(gui);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player p)) return;

        if (!ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("warps")) return;

        e.setCancelled(true);

        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        ItemMeta meta = clickedItem.getItemMeta();
        if (meta == null || !meta.hasDisplayName()) return;

        String itemName = ChatColor.stripColor(meta.getDisplayName());

        if (clickedItem.getType() == Material.BARRIER) {
            p.closeInventory();
            return;
        }

        if (clickedItem.getType() == Material.ARROW && meta.hasLore()) {
            List<String> lore = meta.getLore();
            if (lore != null && !lore.isEmpty()) {
                String[] parts = ChatColor.stripColor(lore.get(0)).split(" ");
                if (parts.length >= 3) {
                    int currentPage = Integer.parseInt(parts[2]);
                    if (itemName.equalsIgnoreCase("Previous Page")) {
                        openGUI(p, currentPage - 1);
                    } else if (itemName.equalsIgnoreCase("Next Page")) {
                        openGUI(p, currentPage + 1);
                    }
                }
            }
            return;
        }

        if (clickedItem.getType() == Material.PAPER) {
            String warpName = itemName;
            Location warpLocation = warpManager.getWarp(warpName);
            if(p.hasPermission("easycommands.warp.warp." + warpName)){
                if (warpLocation != null) {
                    p.teleport(warpLocation);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9You have been teleported to &f" + warpName));
                } else {
                    p.sendMessage(ChatColor.RED + "Warp not found!");
                }
            } else {
                p.closeInventory();
                p.sendMessage(ChatColor.RED + "You don't have permission to teleport to here!");
            }
            p.closeInventory();
        }
    }
}
