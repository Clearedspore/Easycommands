package me.clearedspore.Listeners;

import me.clearedspore.ConfigFiles.Messages;
import me.clearedspore.easycommands;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.awt.*;

public class JoinListener implements Listener {

    private final easycommands plugin;
    private final boolean SpawnTPenabled;
    private final boolean HealEnabled;
    private final boolean TitleEnabled;

    public JoinListener(easycommands plugin){
        this.plugin = plugin;
        this.SpawnTPenabled = plugin.getConfig().getBoolean("SpawnTP", true);
        this.HealEnabled = plugin.getConfig().getBoolean("Heal", true);
        this.TitleEnabled = plugin.getConfig().getBoolean("Title", true);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
    if(SpawnTPenabled) {
        if (e.getPlayer().hasPlayedBefore()) {
            Location location = getSpawnLocation();
            if (location != null) {
                p.teleport(location);
                String Spawn = Messages.get().getString("Spawn");
                String Prefix = Messages.get().getString("Prefix");
                Spawn = Spawn.replace("%prefix%", Prefix);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Spawn));
            }
        }
    }
    if(HealEnabled){
        p.setHealth(20);
    }
    if(TitleEnabled){
        String Text = plugin.getConfig().getString("TitleText");
        Text = Text.replace("%player%", p.getName());
        p.sendTitle(ChatColor.translateAlternateColorCodes('&', Text), (""), 10, 70, 20);
    }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e){

        Location location = plugin.getConfig().getLocation("Spawn");
        if(location != null){
            e.setRespawnLocation(location);
        }
    }
    private Location getSpawnLocation() {
        String worldName = plugin.getConfig().getString("spawn.world");
        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            return null; // World not loaded or invalid
        }

        double x = plugin.getConfig().getDouble("spawn.x");
        double y = plugin.getConfig().getDouble("spawn.y");
        double z = plugin.getConfig().getDouble("spawn.z");
        float pitch = (float) plugin.getConfig().getDouble("spawn.pitch");
        float yaw = (float) plugin.getConfig().getDouble("spawn.yaw");

        return new Location(world, x, y, z, yaw, pitch);
    }


}