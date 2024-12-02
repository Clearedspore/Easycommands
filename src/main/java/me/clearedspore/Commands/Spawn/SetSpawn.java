package me.clearedspore.Commands.Spawn;

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

    public SetSpawn(easycommands plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){

            Location location = p.getLocation();

            plugin.getConfig().set("spawn", location);
            plugin.saveConfig();

            p.sendMessage(ChatColor.BLUE + "You have set the spawn location!");
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (online.hasPermission("easycommands.logs"))
                    online.sendMessage(ChatColor.GRAY + "[ " + ChatColor.GRAY + p.getDisplayName() + ChatColor.GRAY + " has set a new spawn]");
            }

        }
        return true;
    }
}
