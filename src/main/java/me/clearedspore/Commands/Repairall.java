package me.clearedspore.Commands;

import me.clearedspore.Files.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;

public class Repairall implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player p) {

            String RepairAll = Messages.get().getString("RepairAll");
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', RepairAll));

            for (int i = 0; i <= 36; i++) {
                try {
                    p.getInventory().getItem(i).setDurability((short) 0);
                    p.getInventory().getBoots().setDurability((short) 0);
                    p.getInventory().getHelmet().setDurability((short) 0);
                    p.getInventory().getLeggings().setDurability((short) 0);
                    p.getInventory().getChestplate().setDurability((short) 0);
                    p.getInventory().getItemInOffHand().setDurability((short) 0);
                } catch (Exception e) {

                }
            }
            return true;
        }
        return false;
    }
}