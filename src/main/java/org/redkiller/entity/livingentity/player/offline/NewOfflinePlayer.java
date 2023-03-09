package org.redkiller.entity.livingentity.player.offline;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.redkiller.entity.livingentity.player.PlayerDataMap;
import org.redkiller.entity.livingentity.player.mail.Mail;
import org.redkiller.entity.livingentity.player.mail.MailBox;
import org.redkiller.util.map.dataMap.DataMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class NewOfflinePlayer extends NewOfflinePlayerObject {
    private static final HashMap<UUID, NewOfflinePlayer> newOfflinePlayerMap = new HashMap<>();

    public static NewOfflinePlayer getNewOfflinePlayer(UUID playerUUID) {
        return NewOfflinePlayer.getNewOfflinePlayer(Bukkit.getOfflinePlayer(playerUUID));
    }

    public static NewOfflinePlayer getNewOfflinePlayer(OfflinePlayer offlinePlayer) {
        if (!newOfflinePlayerMap.containsKey(offlinePlayer.getUniqueId()))
            newOfflinePlayerMap.put(offlinePlayer.getUniqueId(), new NewOfflinePlayer(offlinePlayer));

        return newOfflinePlayerMap.get(offlinePlayer.getUniqueId());
    }

    public static void saveAllNewOfflinePlayer() {
        for (NewOfflinePlayer newOfflinePlayer : newOfflinePlayerMap.values()) {
            newOfflinePlayer.save();
        }
    }

    public static void reloadAllNewOfflinePlayer() {
        for (NewOfflinePlayer newOfflinePlayer : newOfflinePlayerMap.values()) {
            newOfflinePlayer.load();
        }
    }

    private final PlayerDataMap dataMap;
    private final MailBox mailBox;
    private NewOfflinePlayer(OfflinePlayer offlinePlayer) {
        super(offlinePlayer);
        this.dataMap = new PlayerDataMap(offlinePlayer.getUniqueId());
        this.mailBox = PlayerMailBox.fromDataMaps(dataMap.getList("MailBox"));
    }

    public void save() {
        this.dataMap.set("MailBox", this.mailBox.toDataMaps());
        this.dataMap.save();
    }

    public void load() {
        this.dataMap.load();
    }

    public void sendMail(Mail mail) {
        this.mailBox.add(mail);
    }

    public MailBox getMailBox() {
        return mailBox;
    }

    public PlayerDataMap getPlayerDataMap() {
        return dataMap;
    }

    private static class PlayerMailBox implements MailBox {
        private final List<Mail> mails = new ArrayList<>();

        private PlayerMailBox() {
        }

        @Override
        public void add(Mail mail) {
            this.mails.add(mail);
        }

        @Override
        public void remove(Mail mail) {
            this.mails.remove(mail);
        }

        @Override
        public void remove(int index) {
            this.mails.remove(index);
        }

        @Override
        public void clear() {
            this.mails.clear();
        }

        @Override
        public Mail get(int index) {
            return mails.get(index);
        }

        @Override
        public Mail[] getMails() {
            return mails.toArray(new Mail[0]);
        }

        @Override
        public int size() {
            return this.mails.size();
        }

        @Override
        public boolean isEmpty() {
            return this.mails.isEmpty();
        }

        @Override
        public boolean contains(Mail mail) {
            return this.mails.contains(mail);
        }

        @Override
        public List<DataMap> toDataMaps() {
            List<DataMap> dataMaps = new ArrayList<>();
            for (Mail mail : this.mails) {
                dataMaps.add(mail.toDataMap());
            }

            return dataMaps;
        }

        public static PlayerMailBox fromDataMaps(List<DataMap> list) {
            PlayerMailBox playerMailBox = new PlayerMailBox();
            for (DataMap dataMap : list) {
                playerMailBox.add(Mail.fromDataMap(dataMap));
            }

            return playerMailBox;
        }
    }

}
