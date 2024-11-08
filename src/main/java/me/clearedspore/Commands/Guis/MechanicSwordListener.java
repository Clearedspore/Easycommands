package me.clearedspore.Commands.Guis;

import me.clearedspore.easycommands;
import org.bukkit.*;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.UUID;

import static org.bukkit.event.entity.EntityDamageEvent.*;


public class MechanicSwordListener implements Listener {

    private final easycommands plugin;

    public MechanicSwordListener(easycommands plugin) {
        this.plugin = plugin;
    }

    public static HashSet<UUID> preventFallDamages = new HashSet<>();

    @EventHandler
    public void OnPlayerRightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getInventory().getItemInHand().getType().equals(Material.NETHERITE_SWORD)) {
            if (p.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "" + ChatColor.BOLD + "Mechanics sword")) {
                if (e.getAction() == Action.RIGHT_CLICK_AIR) {
                    p.setVelocity(new Vector(0, 2, 0));

                }
            }

        }
    }
}
