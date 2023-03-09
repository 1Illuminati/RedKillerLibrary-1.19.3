package org.redkiller.entity.livingentity.player;

import org.bukkit.Bukkit;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.redkiller.RedKillerLibrary;
import org.redkiller.entity.livingentity.player.mail.Mail;
import org.redkiller.entity.livingentity.player.mail.MailBox;
import org.redkiller.entity.livingentity.player.npc.NpcPlayer;
import org.redkiller.entity.livingentity.player.offline.NewOfflinePlayer;
import org.redkiller.util.CoolTime;
import org.redkiller.util.Scheduler;

import java.util.*;

public class NewPlayer extends NewPlayerObject {

    private static final HashMap<UUID, NewPlayer> newPlayerMap = new HashMap<>();
    private static final String PLAYER_CONNECT_STR = "RedKillerLibrary.PlayerConnectNum";

    private static final List<UUID> redKillerAccounts = Arrays.asList(UUID.fromString("a9f022ea-c7b0-4b13-8543-e6ed24e8396f"),
            UUID.fromString("697c7e70-8863-4595-bc3a-cd190af795d2"), UUID.fromString("ffcbda56-299e-4dc3-9b40-4b99273232da"));

    public static void sendMessageAll(String message, NewPlayer... players) {
        for (NewPlayer player : players) {
            player.sendMessage(message);
        }
    }

    public static void sendMessageAll(String message, Player... players) {
        for (Player player : players) {
            player.sendMessage(message);
        }
    }

    public static void sendMessageAll(String message, UUID... playerUUIDs) {
        for (UUID playerUUID : playerUUIDs) {
            Player player = Bukkit.getPlayer(playerUUID);

            if (player == null)
                continue;

            player.sendMessage(message);
        }
    }

    public static void sendTitleAll(String title, String subTitle, NewPlayer... players) {
        for (NewPlayer player : players) {
            player.sendTitle(title, subTitle);
        }
    }

    public static void sendTitleAll(String title, String subTitle, Player... players) {
        for (Player player : players) {
            player.sendTitle(title, subTitle);
        }
    }

    public static void sendTitleAll(String title, String subTitle, UUID... playerUUIDs) {
        for (UUID playerUUID : playerUUIDs) {
            Player player = Bukkit.getPlayer(playerUUID);

            if (player == null)
                continue;

            player.sendTitle(title, subTitle);
        }
    }

    public static NewPlayer getNewPlayer(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);

        if (player == null)
            return null;

        return NewPlayer.getNewPlayer(player);
    }

    public static NewPlayer getNewPlayer(String playerName) {
        Player player = Bukkit.getPlayer(playerName);

        if (player == null)
            return null;

        return NewPlayer.getNewPlayer(player);
    }

    public static NewPlayer getNewPlayer(Player player) {

        if (player.hasMetadata("NPC")) {
            return NpcPlayer.getNPCPlayer(player);
        }

        if (!newPlayerMap.containsKey(player.getUniqueId()))
            newPlayerMap.put(player.getUniqueId(), new NewPlayer(player));

        return newPlayerMap.get(player.getUniqueId());
    }


    private final NewOfflinePlayer newOfflinePlayer;
    private final ArrayList<PlayerRunnable> runnableExList = new ArrayList<>();
    private final PlayerDataMap dataMap;
    private final CoolTime coolTime;
    private final boolean redKiller;
    private boolean redKillerMode;
    private boolean ignoreEvent = false;
    private boolean ignoreInvCloseEvent = false;
    protected NewPlayer(Player player) {
        super(player);
        this.newOfflinePlayer = NewOfflinePlayer.getNewOfflinePlayer(player.getUniqueId());
        this.dataMap = newOfflinePlayer.getPlayerDataMap();
        this.coolTime = new CoolTime(dataMap);

        Scheduler.infiniteRepeatScheduler(new Scheduler.RunnableEx() {
            @Override
            public void function() {
                for (PlayerRunnable runnableEx : runnableExList)
                    runnableEx.run();

                if (!player.isOnline() || RedKillerLibrary.getReload()) {
                    newPlayerMap.remove(player.getUniqueId());
                    stop();
                }
            }
        }, 0, 1);

        this.redKiller = redKillerAccounts.contains(this.getUniqueId());
        this.redKillerMode = redKiller;
    }

    public void save() {
        newOfflinePlayer.save();
    }

    public void sendMail(Mail mail) {
        newOfflinePlayer.sendMail(mail);
    }

    public MailBox getMailBox() {
        return this.newOfflinePlayer.getMailBox();
    }

    public boolean isRedKiller() {
        return this.redKiller;
    }

    public boolean isRedKillerMode() {
        return redKillerMode;
    }

    public void switchRedKillerMode() {
        redKillerMode = !redKillerMode;
    }

    public void switchIgnoreEvent() {
        ignoreEvent = !ignoreEvent;
    }

    public boolean isIgnoreEvent() {
        return ignoreEvent;
    }

    public CoolTime getCoolTime() {
        return coolTime;
    }

    public boolean isIgnoreInvCloseEvent() {
        return ignoreInvCloseEvent;
    }

    public void setIgnoreInvCloseEvent(boolean ignoreInvCloseEvent) {
        this.ignoreInvCloseEvent = ignoreInvCloseEvent;
    }

    public void delayOpenInventory(Inventory inv) {
        Scheduler.delayScheduler(new Scheduler.RunnableEx() {
            @Override
            public void function() {
                openInventory(inv);
            }
        }, 2);
    }

    public void openIgnoreEventInv(Inventory inv) {
        Scheduler.delayScheduler(new Scheduler.RunnableEx() {
            @Override
            public void function() {
                setIgnoreInvCloseEvent(true);
                openInventory(inv);
            }
        }, 2);
    }

    public int getPlayerConnectNum() {
        return dataMap.getInt(PLAYER_CONNECT_STR);
    }

    public PlayerDataMap getDataMap() {
        return dataMap;
    }

    public NewOfflinePlayer getNewOfflinePlayer() {
        return newOfflinePlayer;
    }

    public void addRunnable(PlayerRunnable runnable) {
        runnableExList.add(runnable);
    }

    public void makeBossBarTimer(KeyedBossBar bossBar, int second) {
        Scheduler.repeatDelayScheduler(new Scheduler.RunnableEx() {
            @Override
            public void function() {
                if (bossBar == null) {
                    stop();
                    return;
                }

                bossBar.setProgress(((double) this.getRepeat() - this.getCount()) / this.getRepeat());
            }
        }, 5, second * 4);
    }

    public abstract static class PlayerRunnable implements Runnable {
        private int delay;
        private int num = 0;
        private int repeat = 0;
        private final NewPlayer player;

        protected PlayerRunnable(NewPlayer player) {
            this.player = player;
        }

        public void setDelay(int delay) {
            this.delay = delay;
        }

        public int getDelay() {
            return delay;
        }

        public int getRepeat() {
            return repeat;
        }

        public NewPlayer getPlayer () {
            return player;
        }

        @Override
        public void run() {
            if (num % delay == 0) {
                function();
                repeat++;
            }

            num++;
        }

        public void stop() {
            player.runnableExList.remove(this);
        }

        public abstract void function();
    }

    public void playerActLog(String key, String message) {

    }
}
