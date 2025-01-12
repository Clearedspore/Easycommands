package me.clearedspore.Commands;

import me.clearedspore.Commands.settings.SettingsManager;
import me.clearedspore.ConfigFiles.Messages;
import me.clearedspore.Features.Logs.LogManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ClearInventory implements CommandExecutor {
    private final SettingsManager settingsManager;

    public ClearInventory(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {

            if (args.length == 0) {

                String ClearInventory = Messages.get().getString("ClearInventory");
                String Prefix = Messages.get().getString("Prefix");
                ClearInventory = ClearInventory.replace("%prefix%", Prefix);
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
                            String Prefix = Messages.get().getString("Prefix");
                            ClearInventoryOther = ClearInventoryOther.replace("%prefix%", Prefix);
                            ClearInventoryOther = ClearInventoryOther.replace("%target%", target.getDisplayName());
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', ClearInventoryOther));

                            LogManager.getInstance().log(p.getUniqueId(), ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " Has cleared the inventory of " + target.getDisplayName());
                                for (Player online : Bukkit.getOnlinePlayers()) {
                                    if(settingsManager.isLogEnabled(online)) {
                                    if (online.hasPermission("easycommands.logs"))
                                        online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " has cleared the inventory of " + target.getDisplayName() + "]");
                                }
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

