package me.clearedspore.Commands.UtilityGUIS;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class loom implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){
            Inventory Loom = Bukkit.createInventory(null, InventoryType.LOOM);
            p.openInventory(Loom);
        }
        return true;
    }
}
