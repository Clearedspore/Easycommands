package me.clearedspore.Features.ChatColor;

import org.bukkit.ChatColor;

public class ChatSettings {
    private ChatColor color = ChatColor.WHITE;
    private boolean bold = false;
    private boolean italic = false;
    private boolean underline = false;

    public ChatColor getColor() {
        return color;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean isUnderline() {
        return underline;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public void reset() {
        this.color = ChatColor.WHITE;
        this.bold = false;
        this.italic = false;
        this.underline = false;
    }

    public String applyFormatting(String message) {
        StringBuilder formattedMessage = new StringBuilder(color.toString());
        if (bold) formattedMessage.append(ChatColor.BOLD);
        if (italic) formattedMessage.append(ChatColor.ITALIC);
        if (underline) formattedMessage.append(ChatColor.UNDERLINE);
        formattedMessage.append(message);
        return formattedMessage.toString();
    }
}
