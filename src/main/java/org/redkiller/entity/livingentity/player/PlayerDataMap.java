package org.redkiller.entity.livingentity.player;

import org.redkiller.util.file.nbt.NBTHelper;
import org.redkiller.util.map.dataMap.DataMap;

import java.util.UUID;

public class PlayerDataMap extends DataMap {
    private final UUID playerUUID;
    private final String path;
    public PlayerDataMap(UUID playerUUID) {
        this.playerUUID = playerUUID;
        this.path = "plugins/redKillerLibrary/nbt/player/" + playerUUID + ".nbt";
        this.load();
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    @Override
    public void save() {
        NBTHelper.saveDataMap(path, this);
    }

    @Override
    public void load() {
        this.copy(NBTHelper.loadDataMap(path));
    }
}
