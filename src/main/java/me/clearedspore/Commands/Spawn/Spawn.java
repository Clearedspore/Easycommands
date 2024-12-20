package me.clearedspore.Commands.Spawn;

import me.clearedspore.ConfigFiles.Messages;
import me.clearedspore.Logs.LogManager;
import me.clearedspore.easycommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {

    private final easycommands plugin;

    public Spawn(easycommands plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {

            Location location = plugin.getConfig().getLocation("spawn");

            if (location != null) {
                if (args.length == 0) {

                    p.teleport(location);

                    String Spawn = Messages.get().getString("Spawn");
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Spawn));

                }
                if(args.length == 1) {
                    String playername = args[0];

                    Player target = Bukkit.getServer().getPlayerExact(playername);

                    if (target == null) {
                        p.sendMessage(ChatColor.RED + "Player is not online!");

                    } else if (p.hasPermission("easycommands.spawn.other")) {

                        target.teleport(location);
                        String Spawn = Messages.get().getString("Spawn");
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', Spawn));

                        String SpawnT = Messages.get().getString("SpawnTarget");
                        SpawnT = SpawnT.replace("%target%", target.getDisplayName());
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', SpawnT));

                        LogManager.log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " has teleported " + target.getDisplayName() + " to spawn");
                        for (Player online : Bukkit.getOnlinePlayers()) {
                            if (online.hasPermission("easycommands.logs"))
                                online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has teleported " + target.getDisplayName() + " to spawn]");
                        }
                    } else {

                        if (!p.hasPermission(("easycommands.spawn.other"))) {

                            p.sendMessage(ChatColor.RED + "You don't have permission to teleport other people to spawn!");
                        }

                    }
                }
            }
        }
        return true;
    }
}
