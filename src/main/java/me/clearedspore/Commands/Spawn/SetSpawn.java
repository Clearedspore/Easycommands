package me.clearedspore.Commands.Spawn;

import me.clearedspore.ConfigFiles.Messages;
import me.clearedspore.Features.Logs.LogManager;
import me.clearedspore.easycommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawn implements CommandExecutor {

    private final easycommands plugin;

    public SetSpawn(easycommands plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            // Get the player's current location
            Location location = p.getLocation();

            // Save the spawn location components individually
            plugin.getConfig().set("spawn.world", location.getWorld().getName());
            plugin.getConfig().set("spawn.x", location.getX());
            plugin.getConfig().set("spawn.y", location.getY());
            plugin.getConfig().set("spawn.z", location.getZ());
            plugin.getConfig().set("spawn.pitch", location.getPitch());
            plugin.getConfig().set("spawn.yaw", location.getYaw());

            // Save the config to disk
            plugin.saveConfig();

            // Notify the player
            String setSpawnMessage = Messages.get().getString("SetSpawn");
            String Prefix = Messages.get().getString("Prefix");
            setSpawnMessage = setSpawnMessage.replace("%prefix%", Prefix);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', setSpawnMessage));

            LogManager.log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has set a new spawn.");
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (online.hasPermission("easycommands.logs")) {
                    online.sendMessage(ChatColor.GRAY + "[ " + p.getDisplayName() + " has set a new spawn ]");
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
        }
        return true;
    }
}
