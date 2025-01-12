package me.clearedspore.Commands;

import me.clearedspore.ConfigFiles.Messages;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class StaffHelp implements CommandExecutor {
    private final FileConfiguration config;
    private final Map<UUID, Long> lastReportTime = new HashMap<>();

    public StaffHelp(FileConfiguration config) {
        this.config = config;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < args.length; i++) {
                        builder.append(args[i]);
                        builder.append(" ");
                    }
                    String MSG = builder.toString();
                    MSG = MSG.stripTrailing();

                    String staffhelp = Messages.get().getString("staffhelp");
                    String Prefix = Messages.get().getString("Prefix");
                    staffhelp = staffhelp.replace("%prefix%", Prefix);
                     p.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', staffhelp));

                    for (Player online : Bukkit.getOnlinePlayers()) {
                        if (online.hasPermission("easycommands.staff")) {

                            TextComponent messageButton = new TextComponent("[Message]");
                            messageButton.setColor(net.md_5.bungee.api.ChatColor.YELLOW);
                            messageButton.setBold(true);
                            messageButton.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + p.getName() + " "));

                            TextComponent TeleportButton = new TextComponent("[Teleport]");
                            TeleportButton.setColor(net.md_5.bungee.api.ChatColor.BLUE);
                            TeleportButton.setBold(true);
                            TeleportButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/easycommands:teleport " + p.getName()));

                            String border = org.bukkit.ChatColor.BLUE + "=========================";
                            TextComponent message = new TextComponent();
                            message.addExtra(border + "\n");
                            message.addExtra(org.bukkit.ChatColor.WHITE + p.getName() + org.bukkit.ChatColor.BLUE + " has request staff!" + "\n");
                            message.addExtra(org.bukkit.ChatColor.BLUE + "Reason: " + org.bukkit.ChatColor.WHITE + MSG + "\n");
                            message.addExtra(messageButton);
                            message.addExtra(" ");
                            message.addExtra(TeleportButton);
                            message.addExtra("\n" + border);

                            online.spigot().sendMessage(message);
                        }
                    }
                }
        return true;
    }
}
