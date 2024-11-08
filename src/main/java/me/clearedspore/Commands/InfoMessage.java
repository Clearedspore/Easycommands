package me.clearedspore.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InfoMessage implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player p){

            p.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "This the official PlutoTiers plugin!");
            p.sendMessage(ChatColor.BLUE + "------------------------------------------------------");
            p.sendMessage(ChatColor.BLUE + "Commands in this plugin:");
            p.sendMessage(ChatColor.WHITE + "- clear, ci " + ChatColor.BLUE + "Clear your inventory with this command!");
            p.sendMessage(ChatColor.WHITE + "- gmc, gms, gma, gmsp " + ChatColor.BLUE + "Change your gamemode!");
            p.sendMessage(ChatColor.WHITE + "- feed, heal " + ChatColor.BLUE + "Feed or Heal yourself!");
            p.sendMessage(ChatColor.WHITE + "- repair " + ChatColor.BLUE + "Repair your item in your hand!");
            p.sendMessage(ChatColor.WHITE + "- god " + ChatColor.BLUE + "Put yourself in godmode to not take damage!");
            p.sendMessage(ChatColor.WHITE + "- nick, unnick " + ChatColor.BLUE + "Nick yourself (Still working on)");
            p.sendMessage(ChatColor.WHITE + "- ec,endersee,enderchest, openenderchest " + ChatColor.BLUE + "open your enderchest");
            p.sendMessage(ChatColor.WHITE + "- setspawn, spawn " + ChatColor.BLUE + "go to spawn!");
            p.sendMessage(ChatColor.WHITE + "- repairall " + ChatColor.BLUE + "repair all your items in your inventory!");
            p.sendMessage(ChatColor.WHITE + "- tp, teleport,tphere " + ChatColor.BLUE + "teleport to players or them to you!");
            p.sendMessage(ChatColor.BLUE + "------------------------------------------------------");
            p.sendMessage("");
            p.sendMessage(ChatColor.BLUE + "The plugin is not finished and a lot off updates wil come!");
            p.sendMessage(ChatColor.BLUE + "Plugin made by Clearedspore");
            p.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "You cannot sell/release this plugin like its yours!");
            p.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "If you want to edit/add something to this plugin dm " + ChatColor.WHITE + "Clearedspore" + ChatColor.BLUE + "" + ChatColor.BOLD + " on discord!");
        }

        return true;
    }
}
