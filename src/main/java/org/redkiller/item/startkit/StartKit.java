package org.redkiller.item.startkit;

import org.bukkit.inventory.ItemStack;
import org.redkiller.server.ServerDataMap;
import org.redkiller.util.map.dataMap.DataMap;

import java.util.List;

public class StartKit {
    private static StartKit instance;

    public static StartKit getInstance() {
        if (instance == null) {
            instance = new StartKit();
        }

        return instance;
    }

    private final List<ItemStack> itemList;

    private StartKit() {
        DataMap dataMap = ServerDataMap.getInstance();
        itemList = dataMap.getList("startKit");
    }

    public ItemStack[] getStartKit() {
        return itemList.toArray(new ItemStack[0]);
    }

    public void add(ItemStack itemStack) {
        itemList.add(itemStack);
    }

    public void remove(ItemStack itemStack) {
        itemList.remove(itemStack);
    }

    public void save() {
        DataMap dataMap = ServerDataMap.getInstance();
        dataMap.set("startKit", itemList);
    }

    public void clear() {
        itemList.clear();
    }
}
