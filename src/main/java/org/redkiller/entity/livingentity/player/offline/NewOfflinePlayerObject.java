package org.redkiller.entity.livingentity.player.offline;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class NewOfflinePlayerObject {
    private final OfflinePlayer offlinePlayer;

    protected NewOfflinePlayerObject(OfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
    }

    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    public boolean isOnline() {
        return offlinePlayer.isOnline();
    }

    public String getName() {
        return offlinePlayer.getName();
    }

    public UUID getUniqueId() {
        return offlinePlayer.getUniqueId();
    }

    public boolean isBanned() {
        return offlinePlayer.isBanned();
    }

    public boolean isWhitelisted() {
        return offlinePlayer.isWhitelisted();
    }

    public void setWhitelisted(boolean value) {
        offlinePlayer.setWhitelisted(value);
    }

    public Player getPlayer() {
        return offlinePlayer.getPlayer();
    }

    public long getFirstPlayed() {
        return offlinePlayer.getFirstPlayed();
    }

    public long getLastPlayed() {
        return offlinePlayer.getLastPlayed();
    }

    public boolean hasPlayedBefore() {
        return offlinePlayer.hasPlayedBefore();
    }

    public Location getBedSpawnLocation() {
        return offlinePlayer.getBedSpawnLocation();
    }

    public void incrementStatistic(Statistic statistic) throws IllegalArgumentException {
        offlinePlayer.incrementStatistic(statistic);
    }

    public void decrementStatistic(Statistic statistic) throws IllegalArgumentException {
        offlinePlayer.decrementStatistic(statistic);
    }

    public void incrementStatistic(Statistic statistic, int amount) throws IllegalArgumentException {
        offlinePlayer.incrementStatistic(statistic, amount);
    }

    public void decrementStatistic(Statistic statistic, int amount) throws IllegalArgumentException {
        offlinePlayer.decrementStatistic(statistic, amount);
    }

    public void setStatistic(Statistic statistic, int newValue) throws IllegalArgumentException {
        offlinePlayer.setStatistic(statistic, newValue);
    }

    public int getStatistic(Statistic statistic) throws IllegalArgumentException {
        return offlinePlayer.getStatistic(statistic);
    }

    public void incrementStatistic(Statistic statistic, Material material) throws IllegalArgumentException {
        offlinePlayer.incrementStatistic(statistic, material);
    }

    public void decrementStatistic(Statistic statistic, Material material) throws IllegalArgumentException {
        offlinePlayer.decrementStatistic(statistic, material);
    }

    public int getStatistic(Statistic statistic, Material material) throws IllegalArgumentException {
        return offlinePlayer.getStatistic(statistic, material);
    }

    public void incrementStatistic(Statistic statistic, Material material, int amount) throws IllegalArgumentException {
        offlinePlayer.incrementStatistic(statistic, material, amount);
    }

    public void decrementStatistic(Statistic statistic, Material material, int amount) throws IllegalArgumentException {
        offlinePlayer.decrementStatistic(statistic, material, amount);
    }

    public void setStatistic(Statistic statistic, Material material, int newValue) throws IllegalArgumentException {
        offlinePlayer.setStatistic(statistic, material, newValue);
    }

    public void incrementStatistic(Statistic statistic, EntityType entityType) throws IllegalArgumentException {
        offlinePlayer.incrementStatistic(statistic, entityType);
    }

    public void decrementStatistic(Statistic statistic, EntityType entityType) throws IllegalArgumentException {
        offlinePlayer.decrementStatistic(statistic, entityType);
    }

    public int getStatistic(Statistic statistic, EntityType entityType) throws IllegalArgumentException {
        return offlinePlayer.getStatistic(statistic, entityType);
    }

    public void incrementStatistic(Statistic statistic, EntityType entityType, int amount) throws IllegalArgumentException {
        offlinePlayer.incrementStatistic(statistic, entityType, amount);
    }

    public void decrementStatistic(Statistic statistic, EntityType entityType, int amount) {
        offlinePlayer.decrementStatistic(statistic, entityType, amount);
    }

    public void setStatistic(Statistic statistic, EntityType entityType, int newValue) {
        offlinePlayer.setStatistic(statistic, entityType, newValue);
    }

    public boolean isOp() {
        return offlinePlayer.isOp();
    }

    public void setOp(boolean value) {
        offlinePlayer.setOp(value);
    }

    public Map<String, Object> serialize() {
        return offlinePlayer.serialize();
    }
}
