package me.clearedspore.Commands.WarpSection;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.event.WindowStateListener;

public class SetWarp implements CommandExecutor {

    private final WarpManager warpManager;
    public SetWarp(WarpManager warpManager) {
        this.warpManager = warpManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){
            sender.sendMessage("Only players can set warps!");
            return true;
        }


        Player player = (Player) sender;
        if (args.length != 1) {
            player.sendMessage("Usage: /setwarp <name>");
            return true;
        }

        String warpName = args[0];
        warpManager.setWarp(warpName, player.getLocation());
        player.sendMessage(ChatColor.BLUE + "Warp " + ChatColor.WHITE + warpName + ChatColor.BLUE + " set successfully!");
        return true;
        }
    }

