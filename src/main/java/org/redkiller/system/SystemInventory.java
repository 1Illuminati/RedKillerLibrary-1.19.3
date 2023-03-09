package org.redkiller.system;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.redkiller.RedKillerLibrary;
import org.redkiller.inventory.CustomInventory;
import org.redkiller.item.ItemBuilder;
import org.redkiller.item.ban.BanItemInventory;
import org.redkiller.item.startkit.StartKitInventory;
import org.redkiller.system.gamerule.GameRuleInventory;
import org.redkiller.system.world.WorldDataMap;

import java.util.Arrays;

public class SystemInventory extends CustomInventory {
    public SystemInventory() {
        super("System", 27);
        this.setItem(0, new ItemBuilder(Material.BOOK).setDisplayName("§fCustomGameRule Option").build());
        this.setItem(1, new ItemBuilder(Material.BARRIER).setDisplayName("§fBamItem Option").build());
        this.setItem(2, new ItemBuilder(Material.CHEST).setDisplayName("§fStartKit Option").build());
        this.setItem(26, new ItemBuilder(Material.DEBUG_STICK).setDisplayName("§fDebug").setLore(Arrays.asList(RedKillerLibrary.isDebug() ? "§aTRUE" : "§cFALSE")).build());
    }

    @Override
    public void click(InventoryClickEvent event) {
        event.setCancelled(true);
        HumanEntity player = event.getWhoClicked();
        switch (event.getSlot()) {
            case 0 -> player.openInventory(new GameRuleInventory(WorldDataMap.getWorldDataMap(player.getWorld())));
            case 1 -> player.openInventory(new BanItemInventory(0));
            case 2 -> player.openInventory(new StartKitInventory());
            case 26 -> {
                RedKillerLibrary.setDebug(!RedKillerLibrary.isDebug());
                player.openInventory(new SystemInventory());
            }
            default -> {}
        }
    }
}
