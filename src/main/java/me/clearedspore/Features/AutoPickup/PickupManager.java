package me.clearedspore.Features.AutoPickup;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PickupManager {
    private static PickupManager instance;
    private PickupGUI gui;

    private final HashMap<UUID, Boolean> autopickupsettings = new HashMap<>();
    private final HashMap<UUID, Boolean> autosmeltsettings = new HashMap<>();
    public PickupManager() {}

    public static PickupManager getInstance() {
        if (instance == null) {
            instance = new PickupManager();
        }
        return instance;
    }

    public void setGui(PickupGUI gui) {
        this.gui = gui;
    }

    public PickupGUI getGui() {
        return gui;
    }

    public void SetAutpickup(Player player, boolean enabled){
        autopickupsettings.put(player.getUniqueId(), enabled);
    }
    public boolean isAutoPickupEnabled(Player player){
        return autopickupsettings.getOrDefault(player.getUniqueId(), false);
    }
    public void SetAutoSmelt(Player player, boolean enabled){
        autosmeltsettings.put(player.getUniqueId(), enabled);
    }
    public boolean isAutoSmeltEnabled(Player player){
        return autosmeltsettings.getOrDefault(player.getUniqueId(), false);
    }
}
