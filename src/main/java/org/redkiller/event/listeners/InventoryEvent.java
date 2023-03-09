package org.redkiller.event.listeners;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.redkiller.entity.livingentity.player.NewPlayer;
import org.redkiller.event.AbstractEventListener;
import org.redkiller.inventory.CustomInventory;
import org.redkiller.item.ban.BanItemAct;
import org.redkiller.system.gamerule.CustomGameRule;

public class InventoryEvent extends AbstractEventListener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void enchantEvent(EnchantItemEvent event) {
        if (super.getBooleanCustomGameRuleValue(CustomGameRule.ENCHANTMENT, event.getEnchanter())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void invClickEvent(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        int slot = event.getRawSlot();
        HumanEntity whoClicked = event.getWhoClicked();


        if (banItemAllow(event.getCursor(), BanItemAct.CLICK)) {
            event.setCancelled(true);
        }

        if (event.getClick() == ClickType.SWAP_OFFHAND && super.getBooleanCustomGameRuleValue(CustomGameRule.LEFT_HAND, whoClicked)) {
            event.setCancelled(true);
        }

        if (inv instanceof AnvilInventory) {
            if (slot != 2)
                return;

            if (super.getBooleanCustomGameRuleValue(CustomGameRule.ENCHANTMENT, whoClicked)) {
                event.setCancelled(true);
            }
        } else if (inv instanceof CustomInventory customInv) {

            if (customInv.hasButton(slot)) {
                customInv.getButton(slot).onClick(event);
            }

            customInv.click(event);
        } else if (inv instanceof CraftingInventory) {

            if (slot != 45)
                return;

            if (super.getBooleanCustomGameRuleValue(CustomGameRule.LEFT_HAND, whoClicked)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void invCloseEvent(InventoryCloseEvent event) {
        Inventory inv = event.getInventory();
        NewPlayer player = NewPlayer.getNewPlayer((Player) event.getPlayer());

        if (player.isIgnoreInvCloseEvent()) {
            player.setIgnoreInvCloseEvent(false);
        } else if (inv instanceof CustomInventory customInv) {
            customInv.close(event);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void invOpenEvent(InventoryOpenEvent event) {
        Inventory inv = event.getInventory();

        if (inv instanceof CustomInventory customInv) {
            customInv.open(event);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void craftEvent(CraftItemEvent event) {
        if (super.getBooleanCustomGameRuleValue(CustomGameRule.CRAFT, event.getWhoClicked()) || banItemAllow(event.getCursor(), BanItemAct.CRAFT)) {
            event.setCancelled(true);
        }
    }
}
