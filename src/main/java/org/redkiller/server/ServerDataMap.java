package org.redkiller.server;

import org.redkiller.RedKillerLibrary;
import org.redkiller.util.file.nbt.NBTHelper;
import org.redkiller.util.map.dataMap.DataMap;

public class ServerDataMap extends DataMap {

    private static ServerDataMap instance;

    public static ServerDataMap getInstance() {
        if (instance == null) {
            instance = new ServerDataMap();
            instance.load();
            RedKillerLibrary.sendDebugLog("Server DataMap Load Complete");
        }

        return instance;
    }

    private ServerDataMap() {

    }

    @Override
    public void load() {
        this.copy(NBTHelper.loadDataMap("plugins/redKillerLibrary/nbt/server/Server.dat"));
    }

    @Override
    public void save() {
        NBTHelper.saveDataMap("plugins/redKillerLibrary/nbt/server/Server.dat", this);
    }

}
