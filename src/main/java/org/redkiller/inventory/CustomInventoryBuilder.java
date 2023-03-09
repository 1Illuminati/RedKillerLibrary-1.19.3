package org.redkiller.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class CustomInventoryBuilder {
    private final CustomInventory inventory;

    public CustomInventoryBuilder(CustomInventory inventory) {
        this.inventory = inventory;
    }
    public CustomInventoryBuilder(String title, int size, InventoryHolder holder) {
        this.inventory = new CustomInventory(title, size, holder);
    }

    public CustomInventoryBuilder(String title, InventoryType type, InventoryHolder holder) {
        this.inventory = new CustomInventory(title, type, holder);
    }

    public CustomInventoryBuilder(String title, InventoryType type) {
        this(title, type, null);
    }


    public CustomInventoryBuilder(String title, int size) {
        this(title, size, null);
    }

    public CustomInventoryBuilder setItem(int slot, ItemStack itemStack) {
        return this.setItem(slot, itemStack, itemStack.getAmount());
    }

    public CustomInventoryBuilder setItem(int slot, ItemStack item, int amount) {
        item.setAmount(amount);
        this.inventory.setItem(slot, item);
        return this;
    }

    public CustomInventoryBuilder addItem(ItemStack itemStack) {
        this.inventory.addItem(itemStack);
        return this;
    }

    public CustomInventoryBuilder filledItem(ItemStack itemStack) {
        for (int i = 0; i < this.inventory.getSize(); i++) {
            this.inventory.setItem(i, itemStack);
        }
        return this;
    }

    public CustomInventoryBuilder setButton(int slot, CustomInventory.Button button) {
        inventory.putButton(slot, button);
        return this;
    }

    public ItemStack getItem(int slot) {
        return this.inventory.getItem(slot);
    }

    public Inventory getInventory() {
        return this.inventory;
    }
}
