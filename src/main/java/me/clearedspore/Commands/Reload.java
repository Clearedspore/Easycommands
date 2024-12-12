package me.clearedspore.Commands;

import me.clearedspore.WarpSection.WarpManager;
import me.clearedspore.Files.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Reload implements TabExecutor {

    private final WarpManager warpManager;
    public Reload(WarpManager warpManager) {
        this.warpManager = warpManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){
            if(args.length == 0){
                p.sendMessage(ChatColor.RED + "Please provide an argument");
            }
            if(args.length > 0){
                switch (args[0]) {
                    case "reload":
                        Messages.reload();
                        warpManager.reloadWarps();
                        String Reload = Messages.get().getString("Reload");
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Reload));
                        for (Player online : Bukkit.getOnlinePlayers()){
                            if(online.hasPermission("easycommands.logs.admin")){
                                online.sendMessage(ChatColor.GRAY + "[" + p.getDisplayName() + " is reloading the plugin]");
                            }
                    }
                }
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        List<String> arguments = new ArrayList<>();
        if(args.length == 1){
            if(sender.hasPermission("easycommands.easycommands.reload")){
                arguments.add("reload");
            }
            return arguments;
        }
        return null;
    }
}
