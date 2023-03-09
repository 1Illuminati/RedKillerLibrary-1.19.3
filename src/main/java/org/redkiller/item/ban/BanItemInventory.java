package org.redkiller.item.ban;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.redkiller.entity.livingentity.player.NewPlayer;
import org.redkiller.inventory.CustomInventory;
import org.redkiller.item.ItemBuilder;
import org.redkiller.system.SystemInventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BanItemInventory extends CustomInventory {
    private final int page;
    public BanItemInventory(int page) {
        super("BanItem", 54);
        this.page = page;

        ItemStack nowPage = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§f현재 페이지 : " + page).build();

        if (page != 0)
            this.setItem(45, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayName("§f이전 페이지 : " + (page - 1)).build());
        else
            this.setItem(45, nowPage);

        for (int i = 46; i < 53; i++)
            this.setItem(i, nowPage);

        this.setItem(53, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayName("§f다음 페이지 : " + (page + 1)).build());

        BanItem banItem = BanItem.getInstance();
        List<Material> banItemList = new ArrayList<>(banItem.keySet());
        String banTrue = "§a허용";
        String banFalse = "§c금지";


        for (int i = 0; i < 45; i++) {
            int banItemNum = i + (page * 45);

            if (banItemNum >= banItemList.size()) break;

            BanItemInfo banItemInfo = banItem.get(banItemList.get(banItemNum));
            ItemStack infoItem = new ItemBuilder(banItemInfo.getMaterial())
                    .setLore(Arrays.asList(
                            "§fCLICK : " + (banItemInfo.getAllow(BanItemAct.CLICK) ? banTrue : banFalse),
                            "§fUSE : " + (banItemInfo.getAllow(BanItemAct.USE) ? banTrue : banFalse),
                            "§fDROP : " + (banItemInfo.getAllow(BanItemAct.DROP) ? banTrue : banFalse),
                            "§fPLACE : " + (banItemInfo.getAllow(BanItemAct.PLACE) ? banTrue : banFalse),
                            "§fBREAK : " + (banItemInfo.getAllow(BanItemAct.BREAK) ? banTrue : banFalse),
                            "§fCRAFT : " + (banItemInfo.getAllow(BanItemAct.CRAFT) ? banTrue : banFalse),
                            "§fCPICKUP : " + (banItemInfo.getAllow(BanItemAct.PICKUP) ? banTrue : banFalse),
                            "§fEQUIP(WIP) : " + (banItemInfo.getAllow(BanItemAct.EQUIP) ? banTrue : banFalse),
                            "§f",
                            "§f§o(Left Click to open ban item info)",
                            "§f§o(Shift + Left to delete ban item)")).build();

            this.setItem(i, infoItem);

            this.putButton(i, event -> {
                event.setCancelled(true);

                ClickType clickType = event.getClick();
                NewPlayer player = NewPlayer.getNewPlayer((Player) event.getWhoClicked());

                if (clickType == ClickType.LEFT) {
                    player.openIgnoreEventInv(new BanItemInfoInventory(banItemInfo, page));
                } else if (clickType == ClickType.SHIFT_LEFT) {
                    banItem.remove(banItemInfo.getMaterial());
                    player.openIgnoreEventInv(new BanItemInventory(page));
                }
            });
        }
    }

    @Override
    public void click(InventoryClickEvent event) {
        event.setCancelled(true);
        int slot = event.getSlot();
        NewPlayer player = NewPlayer.getNewPlayer((Player) event.getWhoClicked());

        if (slot == 45) {
            player.openIgnoreEventInv(new BanItemInventory(this.page - 1));
            return;
        } else if (slot == 53) {
            player.openIgnoreEventInv(new BanItemInventory(this.page + 1));
            return;
        }

        ItemStack itemStack = event.getCurrentItem();

        if (itemStack == null) {
            return;
        }

        Material material = itemStack.getType();
        BanItem banItem = BanItem.getInstance();
        if (!banItem.contains(material)) {
            if (event.getClick() != ClickType.LEFT) {
                return;
            }

            banItem.add(material);
            player.openIgnoreEventInv(new BanItemInventory(page));
        }
    }

    @Override
    public void close(InventoryCloseEvent event) {
        NewPlayer player = NewPlayer.getNewPlayer((Player) event.getPlayer());
        player.openIgnoreEventInv(new SystemInventory());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BanItemInventory banItemInventory) {
            return banItemInventory.page == this.page;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + this.page;
    }
}
