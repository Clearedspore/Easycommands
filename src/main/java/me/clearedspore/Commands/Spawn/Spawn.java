package me.clearedspore.Commands.Spawn;

import me.clearedspore.Commands.settings.SettingsManager;
import me.clearedspore.ConfigFiles.Messages;
import me.clearedspore.Features.Logs.LogManager;
import me.clearedspore.easycommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {
    private final SettingsManager settingsManager;


    private final easycommands plugin;

    public Spawn(SettingsManager settingsManager, easycommands plugin) {
        this.settingsManager = settingsManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {

            Location location = getSpawnLocation();
            if(location == null){
                p.sendMessage(ChatColor.RED + "Spawn location is not set!");
            }

            if (location != null) {
                if (args.length == 0) {
                    p.teleport(location);

                    String Spawn = Messages.get().getString("Spawn");
                    String Prefix = Messages.get().getString("Prefix");
                    Spawn = Spawn.replace("%prefix%", Prefix);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Spawn));

                } else if (args.length == 1) {
                    // Handle teleporting another player to spawn
                    String playername = args[0];

                    Player target = Bukkit.getServer().getPlayerExact(playername);

                    if (target == null) {
                        p.sendMessage(ChatColor.RED + "Player is not online!");
                    } else if (p.hasPermission("easycommands.spawn.other")) {

                        target.teleport(location);
                        String Spawn = Messages.get().getString("Spawn");
                        String Prefix = Messages.get().getString("Prefix");
                        Spawn = Spawn.replace("%prefix%", Prefix);
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', Spawn));

                        String SpawnT = Messages.get().getString("SpawnTarget");
                        SpawnT = SpawnT.replace("%prefix%", Prefix);
                        SpawnT = SpawnT.replace("%target%", target.getDisplayName());
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', SpawnT));

                        LogManager.getInstance().log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has teleported " + target.getDisplayName() + " to spawn");
                        for (Player online : Bukkit.getOnlinePlayers()) {
                            if(settingsManager.isLogEnabled(online)) {
                            if (online.hasPermission("easycommands.logs")) {
                                online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has teleported " + target.getDisplayName() + " to spawn]");
                            }
                        }
                        }
                    } else {
                        // Handle insufficient permissions for teleporting others
                        p.sendMessage(ChatColor.RED + "You don't have permission to teleport other people to spawn!");
                    }
                }
            } else {
                p.sendMessage(ChatColor.RED + "Spawn location is not set!");
            }
        }
        return true;
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
