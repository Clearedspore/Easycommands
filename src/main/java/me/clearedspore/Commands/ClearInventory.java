package me.clearedspore.Commands;

import me.clearedspore.Files.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ClearInventory implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {

            if (args.length == 0) {

                String ClearInventory = Messages.get().getString("ClearInventory");
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', ClearInventory));

                p.getInventory().clear();

                }else if(args.length == 1) {

                    String playername = args[0];

                    Player target = Bukkit.getServer().getPlayerExact(playername);

                    if (target == null) {
                        p.sendMessage(ChatColor.RED + "Player is not online!");
                    } else {
                        if (p.hasPermission("easycommands.clear.other")) {

                            target.getInventory().clear();

                            String ClearInventoryOther = Messages.get().getString("ClearInventoryOther");
                            ClearInventoryOther = ClearInventoryOther.replace("%target%", target.getDisplayName());
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', ClearInventoryOther));

                            for (Player online : Bukkit.getOnlinePlayers()) {
                                if (online.hasPermission("easycommands.logs"))
                                    online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has cleared the inventory off " + target.getDisplayName() + "]");
                            }
                        } else {

                            if (!p.hasPermission(("easycommands.clear.other"))) {

                                p.sendMessage(ChatColor.RED + "You don't have permission to clear other people their inventory!");
                            }
                        }
                    }
                }
            }

        return true;
        }
    }

