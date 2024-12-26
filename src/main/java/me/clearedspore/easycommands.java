package me.clearedspore;

import me.clearedspore.Commands.*;
import me.clearedspore.Commands.Freeze.Freeze;
import me.clearedspore.Commands.Freeze.FreezeListener;
import me.clearedspore.Commands.Gamemodes.*;
import me.clearedspore.Commands.Items.Item;
import me.clearedspore.Commands.Nick;
import me.clearedspore.Commands.Spawn.SetSpawn;
import me.clearedspore.Commands.Spawn.Spawn;
import me.clearedspore.Commands.Teleport.Teleport;
import me.clearedspore.Commands.Teleport.TeleportAll;
import me.clearedspore.Commands.Teleport.tphere;
import me.clearedspore.Features.AutoPickup.PickupCommand;
import me.clearedspore.Features.AutoPickup.PickupGUI;
import me.clearedspore.Features.AutoPickup.PickupListener;
import me.clearedspore.Features.AutoPickup.PickupManager;
import me.clearedspore.Features.ChatColor.ChatColorCommand;
import me.clearedspore.Features.ChatColor.ChatListener;
import me.clearedspore.Listeners.CheckerListener;
import me.clearedspore.Features.Logs.GetLogsCommand;
import me.clearedspore.Features.MaintenanceSection.MaintenanceCommand;
import me.clearedspore.Features.MaintenanceSection.MaintenanceJoinListener;
import me.clearedspore.Features.MaintenanceSection.MaintenanceManager;
import me.clearedspore.Features.WarpSection.*;
import me.clearedspore.ConfigFiles.Messages;
import me.clearedspore.Listeners.JoinLeaveListener;
import me.clearedspore.Listeners.JoinListener;
import me.clearedspore.Utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public final class easycommands extends JavaPlugin {

    private UpdateChecker updateChecker;
    private PickupManager pickupManager;
    private PickupGUI gui;
    private MaintenanceManager maintenanceManager;


    public static Set<Player> Frozen = new HashSet<>();
    public static Set<Player> Confirmation = new HashSet<>();

    public static easycommands plugin;

    private WarpManager warpManager;

    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }

    public static easycommands getPlugin() {
        return plugin;
    }

    @Override
    public void onLoad() {
        // Ensure the config file is loaded but not overwritten
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        warpManager = new WarpManager(this);
        maintenanceManager = new MaintenanceManager(this);
        pickupManager = new PickupManager();  // Correct initialization
        PickupGUI gui = new PickupGUI(pickupManager);
        pickupManager.setGui(gui);

        System.out.println("EasyCommands has loaded!");

        int resourceId = 121185;
        updateChecker = new UpdateChecker(this, resourceId);
        updateChecker.checkForUpdates();

        // Register the PlayerJoinListener
        getServer().getPluginManager().registerEvents(new CheckerListener(updateChecker, this), this);

        getCommand("clear").setExecutor(new ClearInventory());
        getCommand("gmc").setExecutor(new CreativeMode());
        getCommand("gmsp").setExecutor(new SpectatoMode());
        getCommand("gma").setExecutor(new AdventureMode());
        getCommand("gms").setExecutor(new SurvivalMode());
        getCommand("god").setExecutor(new GodMode());
        getCommand("feed").setExecutor(new Feed());
        getCommand("repair").setExecutor(new Repair());
        getCommand("heal").setExecutor(new Heal());
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
        getCommand("back").setExecutor(new Back());
        getCommand("freeze").setExecutor(new Freeze());
        getCommand("easycommands").setExecutor(new ECInfo(warpManager, this));
        getCommand("kill").setExecutor(new Kill());
        getCommand("copyinventory").setExecutor(new CopyInv());
        getCommand("invsee").setExecutor(new Invsee());
        getCommand("getlogs").setExecutor(new GetLogsCommand());
        getCommand("getlogs").setTabCompleter(new GetLogsCommand());
        getCommand("kick").setExecutor(new Kick());
        getCommand("msg").setExecutor(new Msg());

        getCommand("setwarp").setExecutor(new SetWarp(warpManager));
        getCommand("warp").setExecutor(new WarpCommand(warpManager));
        getCommand("warp").setTabCompleter(new WarpTabCompleter(warpManager));
        getCommand("delwarp").setExecutor(new Delwarp(warpManager));
        getCommand("delwarp").setTabCompleter(new WarpTabCompleter(warpManager));
        getCommand("delwarpall").setExecutor(new DelwarpAll(warpManager));

        getCommand("maintenance").setExecutor(new MaintenanceCommand(this, maintenanceManager));
        getCommand("maintenance").setTabCompleter(new MaintenanceCommand(this, maintenanceManager));
        getServer().getPluginManager().registerEvents(new MaintenanceJoinListener(this, maintenanceManager), this);

        getCommand("chatcolor").setExecutor(new ChatColorCommand());
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener.InventoryClickListener(), this);

        getCommand("item").setExecutor(new Item(this));
        getCommand("item").setTabCompleter(new Item(this));

        getCommand("pickup").setExecutor(new PickupCommand(pickupManager));
        getServer().getPluginManager().registerEvents(new PickupGUI(pickupManager), this);
        getServer().getPluginManager().registerEvents(new PickupListener(pickupManager), this);

        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new FreezeListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(this), this);
        getServer().getPluginManager().registerEvents(new WarpCommand(warpManager), this);

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        Messages.setup();
        Messages.get().addDefault("Prefix", "&f[&9&lEC&f]");
        Messages.get().addDefault("StaffJoin", "&9[Staff] &f%player% &9 has joined the server");
        Messages.get().addDefault("StaffLeave", "&9[Staff] &f%player% &9 has left the server");
        Messages.get().addDefault("Freeze", "%prefix% &9You have freezed &f%target%");
        Messages.get().addDefault("FrozenNotify", "%prefix% &cYou have been frozen. Do not log out or you will be cleared!");
        Messages.get().addDefault("FrozencmdBlock", "%prefix% &cYou can't send commands while being frozen!");
        Messages.get().addDefault("UnFreeze", "%prefix% &9You have unfreezed &f%target%");
        Messages.get().addDefault("Gamemode", "%prefix% &9Gamemode changed to &f%gamemode%!");
        Messages.get().addDefault("GamemodeOther", "%prefix% &9You have changed &f%target%'s &9Gamemode to &f%gamemode%!");
        Messages.get().addDefault("GamemodeTarget", "%prefix% &9Your gamemode has been changed To &f%gamemode%!");
        Messages.get().addDefault("SetSpawn", "%prefix% &9You set the new spawn location!");
        Messages.get().addDefault("Spawn", "%prefix% &9You have been teleported to spawn!");
        Messages.get().addDefault("SpawnOther", "%prefix% &9You have teleported &f%target% to spawn!");
        Messages.get().addDefault("Teleport", "%prefix% &9Teleporting to &f%target%");
        Messages.get().addDefault("TeleportOthers", "%prefix% &9Teleporting &f%playertosend% &9to &f%target%");
        Messages.get().addDefault("TeleportHere", "%prefix% &9Teleporting &f%playertosend% &9to yourself");
        Messages.get().addDefault("TeleportAll","%prefix% &9Teleporting &f%online% &9players(s) to you!");
        Messages.get().addDefault("Back","%prefix% &9Teleporting back to your last death location!");
        Messages.get().addDefault("BackTarget","%prefix% &9Teleporting back to your last death location!");
        Messages.get().addDefault("BackOther","%prefix% &9Teleporting &f%target% &fto their last death location");
        Messages.get().addDefault("ClearChat", "%prefix% &9%player% has cleared the chat!");
        Messages.get().addDefault("ClearInventory", "%prefix% &9You have cleared your inventory!");
        Messages.get().addDefault("ClearInventoryOther", "%prefix% &9You have cleared &f%target%'s &9Inventory!");
        Messages.get().addDefault("EnderChestOther", "%prefix% &9Opening &f%target%'s &9Enderchest!");
        Messages.get().addDefault("Feed", "%prefix% &9Your saturation has been set to the max!");
        Messages.get().addDefault("FeedOther", "%prefix% &9you have set &f%target%'s &9Saturation to the max!");
        Messages.get().addDefault("GodEnable", "%prefix% &aGodMode enabled");
        Messages.get().addDefault("GodDisable", "%prefix% &cGodMode disabled");
        Messages.get().addDefault("Heal", "%prefix% &9You have been healed!");
        Messages.get().addDefault("HealOther", "%prefix% &9You have healed &f%target%");
        Messages.get().addDefault("Invsee", "%prefix% &9Opening &f%target%'s &fInventory!");
        Messages.get().addDefault("Nick", "%prefix% &9You have nicked yourself as &f%nickname%&9!");
        Messages.get().addDefault("UnNick", "%prefix% &9You have Unicked yourself!");
        Messages.get().addDefault("Reload", "%prefix% &9Reloading all files!");
        Messages.get().addDefault("Repair", "%prefix% &9You have repaired the item in your hand!");
        Messages.get().addDefault("RepairAll", "%prefix% &9You have repaired your inventory!");
        Messages.get().addDefault("Kill", "%prefix% &9You have killed yourself!");
        Messages.get().addDefault("KillOther", "%prefix% &9You have killed &f%target%&9!");
        Messages.get().addDefault("CopyInv", "%prefix% &9You have copied &f%target%'s &9Inventory");
        Messages.get().addDefault("setwarp", "%prefix% &9You have created the %warpname% warp");
        Messages.get().addDefault("warp", "%prefix% &9You have been teleported to %warpname%");
        Messages.get().addDefault("mon", "%prefix% &9Maintenance has been turned on");
        Messages.get().addDefault("moff", "%prefix% &9Maintenance has been turned off");
        Messages.get().addDefault("madd", "%prefix% &9%target% has been added to the maintenance whitelist");
        Messages.get().addDefault("mremove", "%prefix% &9%target% has been removed from the maintenance whitelist");
        Messages.get().addDefault("mkickall", "%prefix% &9All online players have been kicked!");


        Messages.get().options().copyDefaults(true);
        Messages.save();



    }
    public PickupManager getPickupManager() {
        return pickupManager;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic


        System.out.println("Shutting down the plugin!");
    }
}
