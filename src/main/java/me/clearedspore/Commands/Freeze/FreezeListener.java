package me.clearedspore.Commands.Freeze;

import me.clearedspore.Files.Messages;
import me.clearedspore.easycommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static me.clearedspore.easycommands.Frozen;


public class FreezeListener implements Listener {
    public FreezeListener(easycommands easycommands) {
    }

    @EventHandler
    public void OnMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if (Frozen.contains(p)) {
            e.setCancelled(true);
            p.setInvulnerable(true);
            p.setGlowing(true);
            p.setAllowFlight(true);

        } else if (!Frozen.contains(p)) {
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void OnAttack(EntityDamageEvent e){
        Player p = (Player) e.getEntity();

        if(Frozen.contains(p)){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void OnLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();

        if(Frozen.contains(p)){
            p.getInventory().clear();
            p.setInvulnerable(false);
            p.setGlowing(false);
            p.setAllowFlight(false);
            Frozen.remove(p);
        }
    }
    @EventHandler
    public void OnInteraction(InventoryInteractEvent e){
        Player p = (Player) e.getWhoClicked();
        if(Frozen.contains(p)){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void OnInv(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if(Frozen.contains(p)){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void OnRightClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(Frozen.contains(p)){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void OnBlockBreak(BlockBreakEvent e){
        Player p = e.getPlayer();

        if(Frozen.contains(p)){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();

        if(Frozen.contains(p)){
            if(p.hasPermission("easycommands.freeze")){
                return;
            } else {
            e.setCancelled(true);
            String FrozenCMDBlock = Messages.get().getString("FrozencmdBlock");
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', FrozenCMDBlock));
            }
        }
    }
}

