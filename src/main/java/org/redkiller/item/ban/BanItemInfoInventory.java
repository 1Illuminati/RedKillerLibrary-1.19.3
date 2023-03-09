package org.redkiller.item.ban;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.redkiller.entity.livingentity.player.NewPlayer;
import org.redkiller.inventory.CustomInventory;
import org.redkiller.item.ItemBuilder;

import java.util.Arrays;

public class BanItemInfoInventory extends CustomInventory {
    private final BanItemInfo banItemInfo;
    private final int page;

    public BanItemInfoInventory(BanItemInfo banItemInfo, int page) {
        super(banItemInfo.getMaterial().name() + " banInfo", 9);
        this.banItemInfo = banItemInfo;
        this.page = page;
        temp(0, BanItemAct.CLICK, Material.CHEST, "인벤토리 클릭 금지");
        temp(1, BanItemAct.USE, Material.BARRIER, "우클 좌클 금지");
        temp(2, BanItemAct.DROP, Material.DIAMOND, "드랍 금지");
        temp(3, BanItemAct.PLACE, Material.DIRT, "설치 금지");
        temp(4, BanItemAct.BREAK, Material.GOLDEN_PICKAXE, "부수기 금지");
        temp(5, BanItemAct.CRAFT, Material.CRAFTING_TABLE, "조합 금지");
        temp(6, BanItemAct.PICKUP, Material.ARMOR_STAND, "픽업 금지");
        temp(7, BanItemAct.EQUIP, Material.IRON_CHESTPLATE, "착용 금지");
    }

    private void temp(int slot, BanItemAct act, Material material, String explain) {
        ItemStack itemStack = new ItemBuilder(material)
                .setDisplayName("§f§l" + act.toString())
                .setLore(Arrays.asList(
                        (banItemInfo.getAllow(act) ? "§aTrue" : "§cFalse"),
                        "§f" + explain, "", "§f§o(Click to change the value)"))
                .build();

        this.setItem(slot, itemStack);

        this.putButton(slot, new Button() {
            private final BanItemAct banItemAct = act;
            @Override
            public void onClick(InventoryClickEvent event) {
                event.setCancelled(true);
                NewPlayer player = NewPlayer.getNewPlayer((Player) event.getWhoClicked());
                banItemInfo.setAllow(banItemAct, !banItemInfo.getAllow(banItemAct));
                player.openIgnoreEventInv(new BanItemInfoInventory(banItemInfo, page));
            }
        });
    }

    @Override
    public void close(InventoryCloseEvent event) {
        NewPlayer player = NewPlayer.getNewPlayer((Player) event.getPlayer());
        player.openIgnoreEventInv(new BanItemInventory(page));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BanItemInfoInventory inventory) {
            return inventory.banItemInfo.equals(banItemInfo);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return banItemInfo.hashCode();
    }
}
