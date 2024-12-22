package me.clearedspore.ConfigFiles;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Messages {

    private static File file;
    private static FileConfiguration MessagesFile;

    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Easycommands").getDataFolder(), "messages.yml");

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e){
                //done
            }
        }
        MessagesFile = YamlConfiguration.loadConfiguration(file);
        List<String> HeaderText = new ArrayList<>();
        HeaderText.add("=======================================================");
        HeaderText.add("ONLY EDIT THIS IF YOU KNOW WHAT YOU ARE DOING!!!");
        HeaderText.add("=======================================================");
        HeaderText.add("Here are all the messages off the plugin that you can edit.");
        HeaderText.add("If you want to edit a message but its not in the list then you can't edit it since you don't need to or the code will break.");
        HeaderText.add("Placeholders:");
        HeaderText.add(" NOTE: m means maintenance");
        HeaderText.add(" %target% A player target ex: /tp player (player is the target)");
        HeaderText.add(" %online% Gets all the online players. Only works for tpall");
        HeaderText.add(" %gamemode% THIS IS ONLY FOR THE GAMEMODE MESSAGES. If you use this in a different messages it wil just return %gamemode%");
        HeaderText.add(" %playertosend% this is only for the /tp and ONLY if you use 2 arguments. Ex: /tp player1 player2. You want to send player1 so that is %playertosend% and then %target% for player 2");
        HeaderText.add(" %nickname% This is ONLY used for the nickname message. It is a placeholder for what nickname the player has chosen");
        HeaderText.add(" %warpname% This is the warpname a player has set/warped to");
        HeaderText.add("Good luck editing the messages.");
        MessagesFile.options().setHeader((HeaderText));

        List<String> LowerText = new ArrayList<>();
        MessagesFile.options().setFooter(LowerText);
    }

    public static FileConfiguration get(){
        return MessagesFile;
    }

    public static void save(){
        try{
            MessagesFile.save(file);
        }catch (IOException e){
            System.out.println("Couldn't save the file!");
        }
    }

    public static void reload(){
        MessagesFile = YamlConfiguration.loadConfiguration(file);
    }

}
