package me.clearedspore.Commands.settings;

import org.bukkit.entity.Player;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class SettingsManager {
    private static SettingsManager instance;
    private SettingGUI gui;

    private final HashMap<UUID, Boolean> logsetting = new HashMap<>();
    private final HashMap<UUID, Boolean> adminlogssetting = new HashMap<>();
    private final HashMap<UUID, Boolean> pmsgssettings = new HashMap<>();
    private final File settingsFile = new File ("plugins/Easycommands/settings.txt");

    public SettingsManager() {
        loadSettings();
    }

    public static SettingsManager getInstance(){
        if(instance == null){
            instance = new SettingsManager();
        }
        return instance;
    }

    public void setGui(SettingGUI gui){
        this.gui = gui;
    }

    public SettingGUI getGUI(){
        return gui;
    }

    public void setLogSetting(Player player, boolean enabled) {
        logsetting.put(player.getUniqueId(),enabled);
        saveSettings();
    }
    public boolean isLogEnabled(Player player){
        return logsetting.containsKey(player.getUniqueId()) && logsetting.get(player.getUniqueId());
    }
    public void setAdminLogsSetting(Player player, boolean enabled) {
        adminlogssetting.put(player.getUniqueId(), enabled);
        saveSettings();
    }
    public boolean isAdminLogsEnabled(Player player){
        return adminlogssetting.containsKey(player.getUniqueId()) && adminlogssetting.get(player.getUniqueId());
    }
    public void setpmsgssettings(Player player, boolean enabled){
        pmsgssettings.put(player.getUniqueId(), enabled);
        saveSettings();
    }
    public boolean ispmsgssettings(Player player){
        return pmsgssettings.containsKey(player.getUniqueId()) && pmsgssettings.get(player.getUniqueId());
    }
    public void saveSettings() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(settingsFile))) {
            for (UUID uuid : logsetting.keySet()) {
                writer.write(uuid + ":log:" + logsetting.get(uuid));
                writer.newLine();
            }
            for (UUID uuid : adminlogssetting.keySet()) {
                writer.write(uuid + ":adminlog:" + adminlogssetting.get(uuid));
                writer.newLine();
            }
            for (UUID uuid : pmsgssettings.keySet()) {
                writer.write(uuid + ":pmsg:" + pmsgssettings.get(uuid));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSettings() {
        if (!settingsFile.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(settingsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 3) {
                    UUID uuid = UUID.fromString(parts[0]);
                    boolean value = Boolean.parseBoolean(parts[2]);
                    switch (parts[1]) {
                        case "log":
                            logsetting.put(uuid, value);
                            break;
                        case "adminlog":
                            adminlogssetting.put(uuid, value);
                            break;
                        case "pmsg":
                            pmsgssettings.put(uuid, value);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
