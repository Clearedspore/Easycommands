package me.clearedspore.Commands;

import me.clearedspore.ConfigFiles.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.clearedspore.easycommands.Confirmation;


public class CopyInv implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 0) {
                p.sendMessage(ChatColor.RED + "Please provide a player name");


            } else if (args.length == 1) {
                String playername = args[0];
                Player target = Bukkit.getServer().getPlayerExact(playername);

                if (target == null) {
                    p.sendMessage(ChatColor.RED + "Player is not online!");
                }else if(args.equals(p.getDisplayName())){
                    p.sendMessage(ChatColor.RED + "You can't copy your own inventory!");
                } else {

                        if (Confirmation.contains(p)) {
                            p.getInventory().clear();
                            Confirmation.remove(p);
                            for (int i = 0; i <= 36; i++) {
                                try {
                                   p.getInventory().setItem(i, target.getInventory().getItem(i));
                                   p.getInventory().setBoots(target.getInventory().getBoots());
                                   p.getInventory().setHelmet(target.getInventory().getHelmet());
                                   p.getInventory().setChestplate(target.getInventory().getChestplate());
                                   p.getInventory().setLeggings(target.getInventory().getLeggings());
                                   p.getInventory().setItemInOffHand(target.getInventory().getItemInOffHand());
                                } catch (Exception e) {

                                }
                            }
                            String CopyInv = Messages.get().getString("CopyInv");
                            CopyInv = CopyInv.replace("%target%", target.getDisplayName());
                            String Prefix = Messages.get().getString("Prefix");
                            CopyInv = CopyInv.replace("%prefix%", Prefix);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', CopyInv));


                        } else if (!Confirmation.contains(p)) {
                            Confirmation.add(p);
                            p.sendMessage(ChatColor.RED + "========================================");
                            p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "WARNING!");
                            p.sendMessage(ChatColor.RED + "Are you sure you want to copy " + target.getDisplayName() + " inventory?");
                            p.sendMessage(ChatColor.RED + "This means that your inventory will be cleared and you will get " + target.getDisplayName() + "s inventory.");
                            p.sendMessage(ChatColor.RED + "type this command again to confirm");
                            p.sendMessage(ChatColor.RED + "========================================");
                        }
                    }
                }
            }
        return true;
    }
    }

