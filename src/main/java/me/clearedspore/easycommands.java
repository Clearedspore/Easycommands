package me.clearedspore;

import me.clearedspore.Commands.*;
import me.clearedspore.Commands.Gamemodes.*;
import me.clearedspore.Commands.Guis.CustomItems;
import me.clearedspore.Commands.Guis.MechanicSwordListener;
import me.clearedspore.Commands.Nick;
import me.clearedspore.Commands.Spawn.SetSpawn;
import me.clearedspore.Commands.Spawn.Spawn;
import me.clearedspore.Commands.Teleport.Teleport;
import me.clearedspore.Commands.Teleport.TeleportAll;
import me.clearedspore.Commands.Teleport.tphere;
import me.clearedspore.Commands.Guis.MenuListener;
import me.clearedspore.Listeners.SpawnListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class easycommands extends JavaPlugin {

    private static easycommands plugin;

    public static easycommands getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        System.out.println("EasyCommands has loaded!");
        System.out.println("A plugin full off commands");

        plugin = this;

        getCommand("clear").setExecutor(new ClearInventory());
        getCommand("gmc").setExecutor(new CreativeMode());
        getCommand("gmsp").setExecutor(new SpectatoMode());
        getCommand("gma").setExecutor(new AdventureMode());
        getCommand("gms").setExecutor(new SurvivalMode());
        getCommand("god").setExecutor(new GodMode());
        getCommand("feed").setExecutor(new Feed());
        getCommand("repair").setExecutor(new Repair());
        getCommand("heal").setExecutor(new Heal());
        getCommand("easycommands").setExecutor(new InfoMessage());
        getCommand("repairall").setExecutor(new Repairall());
        getCommand("teleport").setExecutor(new Teleport());
        getCommand("tphere").setExecutor(new tphere());
        getCommand("teleportall").setExecutor(new TeleportAll());
        getCommand("setspawn").setExecutor(new SetSpawn(this));
        getCommand("spawn").setExecutor(new Spawn(this));
        getCommand("enderchest").setExecutor(new Enderchest());
        getCommand("nick").setExecutor(new Nick());
        getCommand("unnick").setExecutor(new Nick());
        getCommand("clearchat").setExecutor(new ClearChat());
        getCommand("customitems").setExecutor(new CustomItems());


        getServer().getPluginManager().registerEvents(new SpawnListener(this), this);
        getServer().getPluginManager().registerEvents(new MenuListener(this), this);
        getServer().getPluginManager().registerEvents(new MechanicSwordListener(this), this);


        getConfig().options().copyDefaults();
        saveDefaultConfig();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        System.out.println("Shutting down the plugin!");
    }
}
