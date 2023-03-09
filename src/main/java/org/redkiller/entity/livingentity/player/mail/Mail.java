package org.redkiller.entity.livingentity.player.mail;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.redkiller.item.ItemBuilder;
import org.redkiller.util.map.dataMap.DataMap;
import org.redkiller.util.map.dataMap.ToDataMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mail implements ToDataMap {
    private final String name;
    private String description = "";
    private List<ItemStack> itemStacks = new ArrayList<>();
    public Mail(String name) {
        this.name = name;
    }

    public void clear() {
        itemStacks.clear();
    }

    public void remove(int index) {
        itemStacks.remove(index);
    }

    public void remove(ItemStack itemStack) {
        itemStacks.remove(itemStack);
    }

    public Mail add(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR)
            return this;

        itemStacks.add(itemStack);
        return this;
    }

    public Mail addAll(List<ItemStack> itemStacks) {
        itemStacks.forEach(this::add);
        return this;
    }

    public Mail addAll(ItemStack[] itemStacks) {
        return addAll(Arrays.asList(itemStacks));
    }

    public Mail addDescription(String description) {
        this.description = description;
        return this;
    }

    public ItemStack[] getItemStacks() {
        return itemStacks.toArray(new ItemStack[0]);
    }
    public ItemStack getItemStack(int index) {
        return itemStacks.get(index);
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public ItemStack toItemStack() {
        List<String> lore = new ArrayList<>();
        lore.add("§7" + description);

        for (int i = 0; i < 3; i++) {
            if (i >= itemStacks.size())
                break;

            ItemStack itemStack = itemStacks.get(i);
            ItemMeta itemMeta = itemStack.getItemMeta();
            lore.add("§7" + (itemMeta.hasDisplayName() ? itemMeta.getDisplayName() : itemStack.getType().name()));


        }
        if (itemStacks.size() > 3)
            lore.add("§7...");
        lore.add("");
        lore.add("§f§o(Left Click to open the mail)");
        lore.add("§f§o(Shift + Left Click to delete the mail)");

        return new ItemBuilder(Material.CHEST).setDisplayName("§e" + name).setLore(lore).build();
    }

    @Override
    public DataMap toDataMap() {
        DataMap dataMap = new DataMap();
        dataMap.put("name", name);
        dataMap.put("description", description);
        dataMap.put("itemStacks", itemStacks);
        return dataMap;
    }

    public static Mail fromDataMap(DataMap dataMap) {
        Mail mail = new Mail(dataMap.getString("name"));
        mail.description = dataMap.getString("description");
        mail.itemStacks = dataMap.getList("itemStacks");
        return mail;
    }
}
