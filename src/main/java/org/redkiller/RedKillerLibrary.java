package org.redkiller;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.redkiller.command.AbstractCommand;
import org.redkiller.command.commands.*;
import org.redkiller.entity.livingentity.player.offline.NewOfflinePlayer;
import org.redkiller.event.listeners.ConnectionEvent;
import org.redkiller.event.listeners.EntityEvent;
import org.redkiller.event.listeners.InventoryEvent;
import org.redkiller.event.listeners.PlayerActEvent;
import org.redkiller.item.ban.BanItem;
import org.redkiller.item.event.ItemEventRegister;
import org.redkiller.item.event.RedKillerWeapon;
import org.redkiller.item.startkit.StartKit;
import org.redkiller.server.ServerDataMap;
import org.redkiller.system.world.WorldDataMap;
import org.redkiller.util.Scheduler;
import org.redkiller.util.file.FileHelper;

public final class RedKillerLibrary extends JavaPlugin {


    public static final String PLUGIN_NAME = "RedKillerLibrary";
    private static RedKillerLibrary plugin;
    private static boolean reload = true;
    private static boolean debug = false;
    private static boolean godMode = false;

    public static boolean isGodMode() {
        return godMode;
    }

    public static void switchGodMode() {
        RedKillerLibrary.godMode = !godMode;
    }
    @Override
    public void onEnable() {
        plugin = this;

        FileHelper.setDoublePluginFolder();
        setCommand();
        sendDebugLog("All Command Registered Complete");
        setEvent();
        sendDebugLog("All Event Registered Complete");
        ItemEventRegister.registerItemEvent(new RedKillerWeapon());
        BanItem.getInstance();
        reload = false;
        sendLog("RedKillerLibrary Enabled Complete");

        Scheduler.infiniteRepeatScheduler(new Scheduler.RunnableEx() {
            @Override
            public void function() {
                save();
                sendLog("RedKillerLibrary Intermediate saving of data Complete");
            }
        }, 36000, 36000);
    }

    @Override
    public void onDisable() {
        reload = true;
        save();
    }

    private void save() {
        NewOfflinePlayer.saveAllNewOfflinePlayer();
        BanItem.getInstance().save();
        WorldDataMap.saveAllWorldDataMap();
        ServerDataMap.getInstance().save();
        StartKit.getInstance().save();
    }

    public static void setDebug(boolean debug) {
        RedKillerLibrary.debug = debug;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static RedKillerLibrary getPlugin() {
        return plugin;
    }

    public static boolean getReload() {
        return reload;
    }

    public static void sendLog(Object message) {
        Bukkit.getConsoleSender().sendMessage("§5§l[ §5" + PLUGIN_NAME + "§5§l ]: §f" + message);
    }

    public static void sendDebugLog(Object message) {
        if (debug)
            Bukkit.getConsoleSender().sendMessage("§5§l[ §5" + PLUGIN_NAME + " Debug" + "§5§l ]: §f" + message);
    }

    private void registerEvent(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    private void setEvent() {
        this.registerEvent(new PlayerActEvent());
        this.registerEvent(new InventoryEvent());
        this.registerEvent(new ConnectionEvent());
        this.registerEvent(new EntityEvent());
    }

    private void registerCommand(AbstractCommand command) {
        PluginCommand co = this.getCommand(command.getName());

        if (co == null) {
            throw new NullPointerException("Command " + command.getName() + " not found");
        }

        co.setExecutor(command);
        co.setTabCompleter(command);
    }

    private void setCommand() {
        this.registerCommand(new SkullCommand());
        this.registerCommand(new MailBoxCommand());
        this.registerCommand(new TestCommand());
        this.registerCommand(new RedKillerCommand());
        this.registerCommand(new SystemCommand());
        this.registerCommand(new StartKitCommand());
        this.registerCommand(new DataCommand());
    }
}
