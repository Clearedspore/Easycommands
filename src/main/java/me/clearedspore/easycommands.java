package me.clearedspore;

import me.clearedspore.Commands.*;
import me.clearedspore.Commands.Freeze.Freeze;
import me.clearedspore.Commands.Freeze.FreezeListener;
import me.clearedspore.Commands.Gamemodes.*;
import me.clearedspore.Commands.Item;
import me.clearedspore.Commands.Nick;
import me.clearedspore.Commands.Spawn.SetSpawn;
import me.clearedspore.Commands.Spawn.Spawn;
import me.clearedspore.Commands.Teleport.*;
import me.clearedspore.Commands.UtilityGUIS.*;
import me.clearedspore.Commands.settings.SettingGUI;
import me.clearedspore.Commands.settings.SettingsCommand;
import me.clearedspore.Commands.settings.SettingsManager;
import me.clearedspore.Features.AutoPickup.PickupCommand;
import me.clearedspore.Features.AutoPickup.PickupGUI;
import me.clearedspore.Features.AutoPickup.PickupListener;
import me.clearedspore.Features.AutoPickup.PickupManager;
import me.clearedspore.Features.ChatChannels.ChatChannel;
import me.clearedspore.Features.ChatChannels.ChatChannelCommand;
import me.clearedspore.Features.ChatChannels.ChatChannelListener;
import me.clearedspore.Features.ChatChannels.ChatChannelManager;
import me.clearedspore.Features.ChatColor.ChatColorCommand;
import me.clearedspore.Features.ChatColor.ChatListener;
import me.clearedspore.Features.Logs.LogManager;
import me.clearedspore.Features.Report.*;
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
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.util.HashSet;
import java.util.Set;

public final class easycommands extends JavaPlugin {

    private UpdateChecker updateChecker;
    private PickupManager pickupManager;
    private ReportManager reportManager;
    private LogManager logManager;
    private PickupGUI gui;
    private MaintenanceManager maintenanceManager;
    private SettingGUI settingGUI;
    private SettingsManager settingsManager;
    private TeleportManager teleportManager;



    public static Set<Player> Frozen = new HashSet<>();
    public static Set<Player> Confirmation = new HashSet<>();

    public static easycommands plugin;
    private easycommands easycommands;

    private WarpManager warpManager;

    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }

    public static easycommands getPlugin() {
        return plugin;
    }

    @Override
    public void onLoad() {
        saveDefaultConfig();
    }


    public void registerCommand(String commandName, ChatChannelCommand executor) {
        try {
            Field commandMapField = SimplePluginManager.class.getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getPluginManager());

            BukkitCommand command = new BukkitCommand(commandName) {
                @Override
                public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                    return executor.onCommand(sender, this, commandLabel, args);
                }
            };

            command.setDescription("Dynamic command for " + commandName);
            command.setPermission(executor.getChannel().getPermission());

            commandMap.register(getDescription().getName(), command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onEnable() {
        ChatChannelManager chatChannelManager = new ChatChannelManager(this);

        for (ChatChannel channel : chatChannelManager.getChannels().values()) {
            for (String commandName : channel.getCommands()) {
                if (commandName.startsWith("/")) {
                    commandName = commandName.substring(1);
                }
                registerCommand(commandName, new ChatChannelCommand(channel, chatChannelManager));
            }
        }
        getServer().getPluginManager().registerEvents(new ChatChannelListener(chatChannelManager), this);

        warpManager = new WarpManager(this);
        maintenanceManager = new MaintenanceManager(this);
        pickupManager = new PickupManager();

        reportManager = new ReportManager(this);
        GetReportGUI reportGUI = new GetReportGUI(reportManager);
        ReportGUI reportGUI1 = new ReportGUI(reportManager, getConfig());
        reportManager.setviewreportsGui(reportGUI);
        reportManager.setReportGUI(reportGUI1);

        logManager = new LogManager();

        PickupGUI gui = new PickupGUI(pickupManager);
        pickupManager.setGui(gui);

        settingsManager = new SettingsManager();
        SettingGUI settingGUI = new SettingGUI(settingsManager);
        settingsManager.setGui(settingGUI);

        teleportManager = new TeleportManager();

        System.out.println("EasyCommands has loaded!");

        int resourceId = 121185;
        updateChecker = new UpdateChecker(this, resourceId);
        updateChecker.checkForUpdates();

        getServer().getPluginManager().registerEvents(new CheckerListener(updateChecker, this), this);

        getCommand("clear").setExecutor(new ClearInventory(settingsManager));
        getCommand("gmc").setExecutor(new CreativeMode(settingsManager));
        getCommand("gmsp").setExecutor(new SpectatorMode(settingsManager));
        getCommand("gma").setExecutor(new AdventureMode(settingsManager));
        getCommand("gms").setExecutor(new SurvivalMode(settingsManager));
        getCommand("gamemode").setExecutor(new Gamemode(settingsManager));
        getCommand("gamemode").setTabCompleter(new Gamemode(settingsManager));
        getCommand("god").setExecutor(new GodMode(settingsManager));
        getCommand("feed").setExecutor(new Feed(settingsManager));
        getCommand("repair").setExecutor(new Repair());
        getCommand("heal").setExecutor(new Heal(settingsManager));
        getCommand("repairall").setExecutor(new Repairall());
        getCommand("teleport").setExecutor(new Teleport(settingsManager));
        getCommand("tphere").setExecutor(new tphere(settingsManager));
        getCommand("tpa").setExecutor(new Tpa(teleportManager, this));
        getCommand("tpaccept").setExecutor(new Tpaccept(teleportManager, this));
        getCommand("tpahere").setExecutor(new TpaHere(teleportManager, this));
        getCommand("teleportall").setExecutor(new TeleportAll(settingsManager));
        getCommand("setspawn").setExecutor(new SetSpawn(settingsManager,this));
        getCommand("spawn").setExecutor(new Spawn(settingsManager, this));
        getCommand("enderchest").setExecutor(new Enderchest(settingsManager));
        getCommand("nick").setExecutor(new Nick(settingsManager));
        getCommand("unnick").setExecutor(new Nick(settingsManager));
        getCommand("clearchat").setExecutor(new ClearChat());
        getCommand("back").setExecutor(new Back(settingsManager));
        getCommand("freeze").setExecutor(new Freeze(settingsManager));
        getCommand("easycommands").setExecutor(new ECInfo(settingsManager, warpManager, reportManager, this));
        getCommand("kill").setExecutor(new Kill(settingsManager, logManager));
        getCommand("kill").setTabCompleter(new Kill(settingsManager, logManager));
        getCommand("copyinventory").setExecutor(new CopyInv());
        getCommand("invsee").setExecutor(new Invsee(settingsManager));
        getCommand("getlogs").setExecutor(new GetLogsCommand());
        getCommand("getlogs").setTabCompleter(new GetLogsCommand());
        getCommand("kick").setExecutor(new Kick(settingsManager));
        getCommand("msg").setExecutor(new Msg(settingsManager));
        getCommand("sudo").setExecutor(new Sudo());
        getCommand("settings").setExecutor(new SettingsCommand(settingsManager));
        getCommand("flight").setExecutor(new Fly());
        getCommand("staffhelp").setExecutor(new StaffHelp(getConfig()));
        getCommand("broadcast").setExecutor(new Broadcast(this));

        getCommand("workbench").setExecutor(new CraftingTable());
        getCommand("furnace").setExecutor(new Furnace());
        getCommand("smithingtable").setExecutor(new Smithing());
        getCommand("cartography").setExecutor(new cartographytable());
        getCommand("anvil").setExecutor(new Anvil());
        getCommand("loom").setExecutor(new loom());

        getCommand("setwarp").setExecutor(new SetWarp(settingsManager, warpManager));
        getCommand("warp").setExecutor(new WarpCommand(warpManager));
        getCommand("warp").setTabCompleter(new WarpTabCompleter(warpManager));
        getCommand("delwarp").setExecutor(new Delwarp(settingsManager, warpManager));
        getCommand("delwarp").setTabCompleter(new WarpTabCompleter(warpManager));
        getCommand("delwarpall").setExecutor(new DelwarpAll(warpManager));
        getServer().getPluginManager().registerEvents(new WarpCommand(warpManager), this);

        getCommand("maintenance").setExecutor(new MaintenanceCommand(this, maintenanceManager));
        getCommand("maintenance").setTabCompleter(new MaintenanceCommand(this, maintenanceManager));
        getServer().getPluginManager().registerEvents(new MaintenanceJoinListener(this, maintenanceManager), this);

        getCommand("chatcolor").setExecutor(new ChatColorCommand());
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener.InventoryClickListener(), this);

        getCommand("item").setExecutor(new Item());
        getCommand("item").setTabCompleter(new Item());

        getCommand("report").setExecutor(new ReportCommand(reportManager, getConfig()));
        getCommand("report").setTabCompleter(new ReportCommand(reportManager, getConfig()));
        getCommand("reports").setExecutor(new GetReportsCommand(reportManager));
        getServer().getPluginManager().registerEvents(new GetReportGUI(reportManager), this);
        getServer().getPluginManager().registerEvents(new ReportGUI(reportManager, getConfig()), this);
        getServer().getPluginManager().registerEvents(new ReportListener(reportManager), this);

        getCommand("pickup").setExecutor(new PickupCommand(pickupManager));
        getServer().getPluginManager().registerEvents(new PickupGUI(pickupManager), this);
        getServer().getPluginManager().registerEvents(new PickupListener(pickupManager), this);

        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new FreezeListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(this), this);
        getServer().getPluginManager().registerEvents(new SettingGUI(settingsManager), this);
        getServer().getPluginManager().registerEvents(new FreezeListener(this), this);
        getServer().getPluginManager().registerEvents(new TeleportListener(teleportManager, this), this);


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
        Messages.get().addDefault("KillOther", "%prefix% &9You have killed &f%target%&9!");
        Messages.get().addDefault("CopyInv", "%prefix% &9You have copied &f%target%'s &9Inventory");
        Messages.get().addDefault("setwarp", "%prefix% &9You have created the %warpname% warp");
        Messages.get().addDefault("warp", "%prefix% &9You have been teleported to %warpname%");
        Messages.get().addDefault("mon", "%prefix% &9Maintenance has been turned on");
        Messages.get().addDefault("moff", "%prefix% &9Maintenance has been turned off");
        Messages.get().addDefault("madd", "%prefix% &9%target% has been added to the maintenance whitelist");
        Messages.get().addDefault("mremove", "%prefix% &9%target% has been removed from the maintenance whitelist");
        Messages.get().addDefault("mkickall", "%prefix% &9All online players have been kicked!");
        Messages.get().addDefault("flightenabled", "%prefix% &9Flight enabled");
        Messages.get().addDefault("flightdisabled", "%prefix% &9Flight disabled");
        Messages.get().addDefault("staffhelp", "%prefix% &9You have requested help from staff. They will reply/fix your problem soon!");


        Messages.get().options().copyDefaults(true);
        Messages.save();



    }
    public PickupManager getPickupManager() {
        return pickupManager;
    }
    public LogManager getLogManager(){
        return logManager;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        if (reportManager != null) {
            reportManager.saveReports();
        }
        if(settingsManager != null){
            settingsManager.saveSettings();
        }
        System.out.println("Shutting down the plugin!");
    }
}
