package me.clearedspore.Commands.Freeze;

import me.clearedspore.easycommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
            p.sendMessage(ChatColor.RED + "You have been frozen. Don't log out or you will be cleared!");
            p.setInvulnerable(true);
            p.setGlowing(true);
            p.setAllowFlight(true);

            PotionEffect effect = new PotionEffect(PotionEffectType.BLINDNESS, 99999, 1);
            effect.apply((p));
            PotionEffect effect2 = new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 1);
            effect2.apply((p));

        } else if (!Frozen.contains(p)) {
            e.setCancelled(false);
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
            p.removePotionEffect(PotionEffectType.BLINDNESS);
            p.removePotionEffect(PotionEffectType.INVISIBILITY);
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
}

