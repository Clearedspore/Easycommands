package me.clearedspore.Features.ChatChannels;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatChannelCommand implements CommandExecutor {
    private final ChatChannel channel;
    private final ChatChannelManager channelManager;

    public ChatChannelCommand(ChatChannel channel, ChatChannelManager channelManager) {
        this.channel = channel;
        this.channelManager = channelManager;
    }

    public ChatChannel getChannel() {
        return channel;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission(channel.getPermission())) {
            player.sendMessage("You do not have permission to use this channel.");
            return true;
        }

        if (channel.isActive(player)) {
            channel.togglePlayer(player, channelManager);
            channelManager.setPlayerChannel(player, null);
        } else {
            channel.togglePlayer(player, channelManager);
            channelManager.setPlayerChannel(player, channel);
        }
        return true;
    }
}