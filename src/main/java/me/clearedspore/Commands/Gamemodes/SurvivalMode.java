package me.clearedspore.Commands.Gamemodes;

import me.clearedspore.Files.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.Executor;

public class SurvivalMode implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){

            if(args.length == 0){
                p.setGameMode(GameMode.SURVIVAL);
                String GameMode = Messages.get().getString("Gamemode");
                GameMode = GameMode.replace("%gamemode%", "Survival");
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', GameMode));

                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online.hasPermission("easycommands.logs"))
                        online.sendMessage(ChatColor.GRAY + "[" + ChatColor.GRAY + p.getDisplayName() + ChatColor.GRAY + " has changed their gamemode to Survival]");
                }
            }else{

                String playername = args[0];

                Player target = Bukkit.getServer().getPlayerExact(playername);

                if(target == null){
                    p.sendMessage(ChatColor.RED + "Player is not online!");
                }else{
                    if(p.hasPermission("easycommands.gamemode.other")) {

                        target.setGameMode(GameMode.SURVIVAL);

                        String GameModeT = Messages.get().getString("GamemodeTarget");
                        GameModeT = GameModeT.replace("%gamemode%", "Survival");
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', GameModeT));

                        String GameMode = Messages.get().getString("GamemodeOther");
                        GameMode = GameMode.replace("%gamemode%", "Survival");
                        GameMode = GameMode.replace("%target%", target.getDisplayName());
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', GameMode));                        for (Player online : Bukkit.getOnlinePlayers()) {
                            if (online.hasPermission("easycommands.logs"))
                                online.sendMessage(ChatColor.GRAY + "[" + ChatColor.GRAY + p.getDisplayName() + ChatColor.GRAY + " has changed their gamemode to Survival]");
                        }
                    }else{
                        if (!p.hasPermission(("easycommands.gamemode.other"))){

                            p.sendMessage(ChatColor.RED + "You don't have permission to change other players their gamemode!");
                        }
                    }
                }
            }
        }
        return true;
    }
}
