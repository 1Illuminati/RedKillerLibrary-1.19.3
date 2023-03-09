package org.redkiller.item.startkit;

import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.redkiller.inventory.CustomInventory;

public class StartKitInventory extends CustomInventory {
    public StartKitInventory() {
        super("§f기본템 설정", 27);
        StartKit startKit = StartKit.getInstance();

        for (ItemStack itemStack : startKit.getStartKit()) {
            this.addItem(itemStack);
        }
    }

    @Override
    public void close(InventoryCloseEvent event) {
        StartKit startKit = StartKit.getInstance();
        startKit.clear();
        for (ItemStack itemStack : event.getInventory().getContents()) {
            if (itemStack != null)
                startKit.add(itemStack);
        }
    }
}
