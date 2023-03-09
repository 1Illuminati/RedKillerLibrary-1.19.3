package org.redkiller.event.listeners;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.redkiller.entity.livingentity.player.NewPlayer;
import org.redkiller.event.AbstractEventListener;
import org.redkiller.item.ban.BanItemAct;
import org.redkiller.item.event.ItemEventAnnotation;
import org.redkiller.item.event.ItemEventRegister;
import org.redkiller.system.gamerule.CustomGameRule;
import org.redkiller.system.world.AreaAct;

public class PlayerActEvent extends AbstractEventListener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void blockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        NewPlayer newPlayer = NewPlayer.getNewPlayer(player);
        Block block = event.getBlock();
        if (super.getBooleanCustomGameRuleValue(CustomGameRule.BREAK, player) || super.banItemAllow(block.getType(), BanItemAct.BREAK)) {
            event.setCancelled(true);
            return;
        }

        if (super.areaEvent(newPlayer, AreaAct.BREAK, block.getLocation(), block, null)) {
            event.setCancelled(true);
            return;
        }

        ItemEventRegister.runItemEvent(event.getPlayer().getInventory().getItemInMainHand(), ItemEventAnnotation.Act.BREAK, event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void blockPlaceEvent(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        NewPlayer newPlayer = NewPlayer.getNewPlayer(player);
        Block block = event.getBlock();
        if (super.getBooleanCustomGameRuleValue(CustomGameRule.PLACE, player)) {
            event.setCancelled(true);
            return;
        }

        if (super.banItemAllow(block.getType(), BanItemAct.PLACE)) {
            event.setCancelled(true);
            return;
        }

        if (super.areaEvent(newPlayer, AreaAct.PLACE, block.getLocation(), block, null)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        NewPlayer newPlayer = NewPlayer.getNewPlayer(player);

        if (super.getBooleanCustomGameRuleValue(CustomGameRule.MOVE, player)) {
            event.setCancelled(true);
            return;
        }

        if (player.getGameMode() == GameMode.SPECTATOR && super.getBooleanCustomGameRuleValue(CustomGameRule.SPECTATOR_MOVE, player)) {
            event.setCancelled(true);
            return;
        }

        if (super.areaEvent(newPlayer, AreaAct.MOVE, event.getTo(), null, null)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        if (super.getBooleanCustomGameRuleValue(CustomGameRule.COMMAND, event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerTeleportEvent(PlayerTeleportEvent event) {
        this.playerMoveEvent(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerDropItemEvent(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        NewPlayer newPlayer = NewPlayer.getNewPlayer(player);
        Item item = event.getItemDrop();
        if (super.getBooleanCustomGameRuleValue(CustomGameRule.DROP, player) || super.banItemAllow(event.getItemDrop().getItemStack(), BanItemAct.DROP)) {
            event.setCancelled(true);
        }

        if (super.areaEvent(newPlayer, AreaAct.DROP, item.getLocation(), null, item)) {
            event.setCancelled(true);
        }

        if (event.getPlayer().isSneaking())
            ItemEventRegister.runItemEvent(event.getItemDrop().getItemStack(), ItemEventAnnotation.Act.SHIFT_DROP, event);
        else
            ItemEventRegister.runItemEvent(event.getItemDrop().getItemStack(), ItemEventAnnotation.Act.DROP, event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerSwapHandItemsEvent(PlayerSwapHandItemsEvent event) {
        if (super.getBooleanCustomGameRuleValue(CustomGameRule.LEFT_HAND, event.getPlayer())) {
            event.setCancelled(true);
        }

        if (event.getPlayer().isSneaking())
            ItemEventRegister.runItemEvent(event.getOffHandItem(), ItemEventAnnotation.Act.SHIFT_SWAP_HAND, event);
        else
            ItemEventRegister.runItemEvent(event.getOffHandItem(), ItemEventAnnotation.Act.SWAP_HAND, event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerInteractEvent(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }

        Action action = event.getAction();
        NewPlayer player = NewPlayer.getNewPlayer(event.getPlayer());
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemEventAnnotation.Act act = null;

        if (super.banItemAllow(mainHand, BanItemAct.USE)) {
            event.setCancelled(true);
        }

        if (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();

            if (block == null)
                return;

            if (super.areaEvent(player, AreaAct.INTERACT_BLOCK, block.getLocation(), block, null)) {
                event.setCancelled(true);
            }
        }

        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            if (player.isSneaking()) {
                act = ItemEventAnnotation.Act.SHIFT_LEFT_CLICK;
            } else {
                act = ItemEventAnnotation.Act.LEFT_CLICK;
            }
        } else if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (player.isSneaking()) {
                act = ItemEventAnnotation.Act.SHIFT_RIGHT_CLICK;
            } else {
                act = ItemEventAnnotation.Act.RIGHT_CLICK;
            }
        }

        if (act != null) {
            ItemEventRegister.runItemEvent(mainHand, act, event);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerFishEvent(PlayerFishEvent event) {
        if (super.getBooleanCustomGameRuleValue(CustomGameRule.FISHING, event.getPlayer())) {
            event.setCancelled(true);
            return;
        }

        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            ItemEventRegister.runItemEvent(event.getPlayer().getInventory().getItemInMainHand(), ItemEventAnnotation.Act.FISHING, event);
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void asyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        if (super.getBooleanCustomGameRuleValue(CustomGameRule.CHAT, event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}
