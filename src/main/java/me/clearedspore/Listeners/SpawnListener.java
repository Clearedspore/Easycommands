package me.clearedspore.Listeners;

import me.clearedspore.easycommands;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class SpawnListener implements Listener {

    private final easycommands plugin;

    public SpawnListener(easycommands plugin){
        this.plugin = plugin;
    }

    

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){

        Player p = e.getPlayer();

        if(e.getPlayer().hasPlayedBefore()){

            Location location =  plugin.getConfig().getLocation("spawn");

            if (location != null){

                p.teleport(location);

                p.sendMessage(ChatColor.BLUE +"You have been teleported to spawn!");

            }


        }

    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e){

        Location location = plugin.getConfig().getLocation("spawn");
        if(location != null){
            e.setRespawnLocation(location);
        }
    }


}