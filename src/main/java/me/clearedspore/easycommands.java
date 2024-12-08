package me.clearedspore;

import me.clearedspore.Commands.*;
import me.clearedspore.Commands.Freeze.Freeze;
import me.clearedspore.Commands.Freeze.FreezeListener;
import me.clearedspore.Commands.Gamemodes.*;
import me.clearedspore.Commands.Nick;
import me.clearedspore.Commands.Spawn.SetSpawn;
import me.clearedspore.Commands.Spawn.Spawn;
import me.clearedspore.Commands.Teleport.Teleport;
import me.clearedspore.Commands.Teleport.TeleportAll;
import me.clearedspore.Commands.Teleport.tphere;
import me.clearedspore.Files.Messages;
import me.clearedspore.Listeners.JoinLeaveListener;
import me.clearedspore.Listeners.SpawnListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public final class easycommands extends JavaPlugin {

    public static Set<Player> Frozen = new HashSet<>();
    public static Set<Player> LeftFrozen = new HashSet<>();
    public static Set<Player> FlyEnabled = new HashSet<>();
    public static Set<Player> InvLooking = new HashSet<>();
    public static Set<Player> Confirmation = new HashSet<>();

    public static easycommands plugin;


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
        getCommand("invsee").setExecutor(new Invsee());
        getCommand("easycommands").setExecutor(new Reload());
        getCommand("kill").setExecutor(new Kill());
        getCommand("copyinventory").setExecutor(new CopyInv());


        getServer().getPluginManager().registerEvents(new SpawnListener(this), this);
        getServer().getPluginManager().registerEvents(new FreezeListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(this), this);

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        Messages.setup();
        Messages.get().addDefault("StaffJoin", "&9[Staff] &f%player% &9 has joined the server");
        Messages.get().addDefault("StaffLeave", "&9[Staff] &f%player% &9 has left the server");
        Messages.get().addDefault("Freeze", "&9You have freezed &f%target%");
        Messages.get().addDefault("FrozenNotify", "&cYou have been frozen. Do not log out or you will be cleared!");
        Messages.get().addDefault("FrozencmdBlock", "&cYou can't send commands while being frozen!");
        Messages.get().addDefault("UnFreeze", "&9You have unfreezed &f%target%");
        Messages.get().addDefault("Gamemode", "&9Gamemode changed to &f%gamemode%!");
        Messages.get().addDefault("GamemodeOther", "&9You have changed &f%target%'s &9Gamemode to &f%gamemode%!");
        Messages.get().addDefault("GamemodeTarget", "&9Your gamemode has been changed To &f%gamemode%!");
        Messages.get().addDefault("SetSpawn", "&9You set the new spawn location!");
        Messages.get().addDefault("Spawn", "&9You have been teleported to spawn!");
        Messages.get().addDefault("SpawnOther", "&9You have teleported &f%target% to spawn!");
        Messages.get().addDefault("Teleport", "&9Teleporting to &f%target%");
        Messages.get().addDefault("TeleportOthers", "&9Teleporting &f%playertosend% &9to &f%target%");
        Messages.get().addDefault("TeleportHere", "&9Teleporting &f%playertosend% &9to yourself");
        Messages.get().addDefault("TeleportAll","&9Teleporting &f%online% &9players to you!");
        Messages.get().addDefault("Back","&9Teleporting back to your last death location!");
        Messages.get().addDefault("BackTarget","&9Teleporting back to your last death location!");
        Messages.get().addDefault("BackOther","&9Teleporting &f%target% &fto their last death location");
        Messages.get().addDefault("ClearChat", "&9%player% has cleared the chat!");
        Messages.get().addDefault("ClearInventory", "&9You have cleared your inventory!");
        Messages.get().addDefault("ClearInventoryOther", "&9You have cleared &f%target%'s &9Inventory!");
        Messages.get().addDefault("EnderChestOther", "&9Opening &f%target%'s &9Enderchest!");
        Messages.get().addDefault("Feed", "&9Your saturation has been set to the max!");
        Messages.get().addDefault("FeedOther", "&9you have set &f%target%'s &9Saturation to the max!");
        Messages.get().addDefault("GodEnable", "&aGodMode enabled");
        Messages.get().addDefault("GodDisable", "&cGodMode disabled");
        Messages.get().addDefault("Heal", "&9You have been healed!");
        Messages.get().addDefault("HealOther", "&9You have healed &f%target%");
        Messages.get().addDefault("Invsee", "&9Opening &f%target%'s &fInventory!");
        Messages.get().addDefault("Nick", "&9You have nicked yourself as &f%nickname%&9!");
        Messages.get().addDefault("UnNick", "&9You have Unicked yourself!");
        Messages.get().addDefault("Reload", "&9Reloading all files!");
        Messages.get().addDefault("Repair", "&9You have repaired the item in your hand!");
        Messages.get().addDefault("Repairall", "&9You have repaired your inventory!");
        Messages.get().addDefault("Kill", "&9You have killed yourself!");
        Messages.get().addDefault("KillOther", "&9You have killed &f%target%&9!");
        Messages.get().addDefault("CopyInv", "&9You have copied &f%target%'s &9Inventory");
        Messages.get().options().copyDefaults(true);
        Messages.save();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        System.out.println("Shutting down the plugin!");
    }
}
