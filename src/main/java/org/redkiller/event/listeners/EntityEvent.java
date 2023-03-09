package org.redkiller.event.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.redkiller.entity.livingentity.player.NewPlayer;
import org.redkiller.item.ban.BanItemAct;
import org.redkiller.item.event.ItemEventAnnotation;
import org.redkiller.item.event.ItemEventRegister;
import org.redkiller.system.world.AreaAct;
import org.spigotmc.event.entity.EntityMountEvent;
import org.redkiller.event.AbstractEventListener;
import org.redkiller.system.gamerule.CustomGameRule;

public class EntityEvent extends AbstractEventListener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void entityBreedEvent(EntityBreedEvent event) {
        if (super.getBooleanCustomGameRuleValue(CustomGameRule.SEX, event.getEntity())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void entityMountEvent(EntityMountEvent event) {
        if (super.getBooleanCustomGameRuleValue(CustomGameRule.RIDING, event.getEntity())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void entityPickUpItemEvent(EntityPickupItemEvent event) {
        Entity entity = event.getEntity();
        Item item = event.getItem();
        if (!(entity instanceof Player player)) {
            return;
        }

        if (super.getBooleanCustomGameRuleValue(CustomGameRule.PICK_UP, player) || banItemAllow(event.getItem().getItemStack(), BanItemAct.PICKUP)) {
            event.setCancelled(true);
            return;
        }

        if (super.areaEvent(NewPlayer.getNewPlayer(player), AreaAct.PICKUP, item.getLocation(), null, item)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void entityDamagedByEntityEvent(EntityDamageByEntityEvent event) {
        Entity atk = event.getDamager();
        Entity def = event.getEntity();

        if (atk instanceof Player && def instanceof Player && super.getBooleanCustomGameRuleValue(CustomGameRule.PVP, atk)) {
            event.setCancelled(true);
            return;
        }

        if (atk instanceof Player player) {
            if (super.areaEvent(NewPlayer.getNewPlayer(player), AreaAct.PVP, player.getLocation(), null, null)) {
                event.setCancelled(true);
                return;
            }

            ItemEventRegister.runItemEvent((player).getInventory().getItemInMainHand(), ItemEventAnnotation.Act.HIT, event);
        }
    }
}
