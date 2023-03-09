package org.redkiller.item.ban;

import org.bukkit.Material;
import org.redkiller.RedKillerLibrary;
import org.redkiller.server.ServerDataMap;
import org.redkiller.util.map.NotNullMap;
import org.redkiller.util.map.dataMap.DataMap;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class BanItem {

    private static BanItem instance;

    public static BanItem getInstance() {
        if (instance == null) {
            instance = new BanItem();
        }

        return instance;
    }

    private final Map<Material, BanItemInfo> map = new LinkedHashMap<>();

    private BanItem() {
        DataMap dataMap = ServerDataMap.getInstance().getDataMap("banItem");

        for (String key : dataMap.keySet()) {
            Material material = Material.getMaterial(key);
            BanItemInfo info = new BanItemInfo(material);
            DataMap infoMap = dataMap.getDataMap(key);
            for (String infoKey : infoMap.keySet()) {
                BanItemAct act = BanItemAct.valueOf(infoKey);
                info.setAllow(act, infoMap.getBoolean(infoKey));
            }
            map.put(material, info);
        }

        RedKillerLibrary.sendDebugLog("BanItem Load Complete");
    }

    public Set<Material> keySet() {
        return map.keySet();
    }

    public void save() {
        DataMap dataMap = new DataMap();
        for (Map.Entry<Material, BanItemInfo> entry : map.entrySet()) {
            Material material = entry.getKey();
            BanItemInfo info = entry.getValue();
            DataMap infoMap = new DataMap();
            for (BanItemAct act : BanItemAct.values()) {
                infoMap.put(act.name(), info.getAllow(act));
            }
            dataMap.put(material.name(), infoMap);
        }

        ServerDataMap.getInstance().set("banItem", dataMap);
    }

    public void add(Material material) {
        map.put(material, new BanItemInfo(material));
    }

    public boolean contains(Material material) {
        return map.containsKey(material);
    }

    public void remove(Material material) {
        map.remove(material);
    }

    public boolean getAllow(Material material, BanItemAct act) {
        if (!map.containsKey(material)) {
            return true;
        }

        return map.get(material).getAllow(act);
    }

    public void setAllow(Material material, BanItemAct act, boolean allow) {
        if (!map.containsKey(material)) {
            add(material);
        }

        map.get(material).setAllow(act, allow);
    }

    public org.redkiller.item.ban.BanItemInfo get(Material material) {
        return map.get(material);
    }

    private static class BanItemInfo implements org.redkiller.item.ban.BanItemInfo {
        private final Map<BanItemAct, Boolean> map = new NotNullMap<>(true);
        private final Material material;
        private BanItemInfo(Material material) {
            this.material = material;
        }

        public Material getMaterial() {
            return material;
        }

        public boolean getAllow(BanItemAct act) {
            return map.get(act);
        }

        public void setAllow(BanItemAct act, boolean allow) {
            map.put(act, allow);
        }
    }
}
