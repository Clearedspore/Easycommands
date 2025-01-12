package me.clearedspore.Commands.UtilityGUIS;

import me.clearedspore.Commands.Back;
import org.bukkit.Bukkit;
import org.bukkit.Translatable;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.checkerframework.checker.units.qual.A;

public class Anvil implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){
            Inventory Anvil = Bukkit.createInventory(null, InventoryType.ANVIL);
            p.openInventory(Anvil);
        }
        return true;
    }
}
