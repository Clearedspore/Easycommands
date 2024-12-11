package me.clearedspore.Commands.WarpSection;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class WarpTabCompleter implements TabCompleter {
    public final WarpManager warpManager;

    public WarpTabCompleter(WarpManager warpManager){
        this.warpManager = warpManager;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length ==1){
            return new ArrayList<>(warpManager.getWarpNames());
        }
        return null;
    }
}
