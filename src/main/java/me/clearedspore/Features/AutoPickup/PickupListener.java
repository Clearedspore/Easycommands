package me.clearedspore.Features.AutoPickup;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;


public class PickupListener implements Listener {
    private final PickupManager pickupManager;
    private static final Map<Material, Material> RAW_TO_SMELTED_MAP = new HashMap<>();

    static {
        RAW_TO_SMELTED_MAP.put(Material.RAW_GOLD, Material.GOLD_INGOT);
        RAW_TO_SMELTED_MAP.put(Material.RAW_IRON, Material.IRON_INGOT);
        RAW_TO_SMELTED_MAP.put(Material.RAW_COPPER, Material.COPPER_INGOT);
    }

    public PickupListener(PickupManager pickupManager) {
        this.pickupManager = pickupManager;
    }

    // Block break event - handles auto-pickup
    @EventHandler
    public void OnAutoPickup(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.CREATIVE) {
            return;
        } else {
            if (pickupManager.isAutoPickupEnabled(p)) {
                e.getBlock().getDrops().forEach(itemStack -> p.getInventory().addItem(itemStack));
                }
            }
    }

    // Block drop event - prevents dropping if auto-pickup is enabled
    @EventHandler
    public void OnDrop(BlockDropItemEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.CREATIVE) {
            return;
        } else {
            if (pickupManager.isAutoPickupEnabled(p)) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void OnPickup(PlayerPickupItemEvent e) {
        ItemStack pickedUpItem = e.getItem().getItemStack();
        Material rawMaterial = pickedUpItem.getType();
        Material smeltedMaterial = RAW_TO_SMELTED_MAP.get(rawMaterial);
        ItemStack smeltedItem = new ItemStack(smeltedMaterial, pickedUpItem.getAmount());
        Player p = e.getPlayer();
        if (pickupManager.isAutoSmeltEnabled(p)) {
            if (RAW_TO_SMELTED_MAP.containsKey(rawMaterial)) {
                e.setCancelled(true);
                p.getInventory().addItem(smeltedItem);
                e.getItem().remove();
            }
        }
    }
}
