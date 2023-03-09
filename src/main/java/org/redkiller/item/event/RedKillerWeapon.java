package org.redkiller.item.event;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.redkiller.RedKillerLibrary;
import org.redkiller.entity.livingentity.player.NewPlayer;
import org.redkiller.item.ItemBuilder;
import org.redkiller.system.SystemInventory;
import org.redkiller.util.map.dataMap.DataMap;

public class RedKillerWeapon implements ItemEvent {

    public static final ItemStack RED_KILLER_AXE;


    static {
        ItemStack itemStack = new ItemBuilder(Material.NETHERITE_AXE).setDisplayName("§c§lRedKiller Axe").setUnbreakable(true).build();
        ItemEventRegister.setItemInEvent(itemStack, "redKiller_weapon");
        RED_KILLER_AXE = itemStack;
    }

    @Override
    public String getCode() {
        return "redKiller_weapon";
    }

    @ItemEventAnnotation(act = ItemEventAnnotation.Act.HIT)
    public void hit(EntityDamageByEntityEvent event) {
        NewPlayer redKiller = NewPlayer.getNewPlayer((Player) event.getDamager());

        if (!redKiller.isRedKiller())
            return;

        String key = "redKiller_weapon_type";
        DataMap dataMap = redKiller.getDataMap();
        String type = dataMap.getString(key, "damage");
        Entity entity = event.getEntity();

        switch(type) {
            case "damage" -> event.setDamage(500);

            case "heal" -> {
                if (entity instanceof LivingEntity livingEntity)
                    livingEntity.setHealth(livingEntity.getMaxHealth());
            }
            case "remove" -> entity.remove();
            case "kill" -> {
                if (entity instanceof LivingEntity livingEntity)
                    livingEntity.setHealth(0);
            }
            case "op" -> {
                if (entity instanceof Player player)
                    player.setOp(true);
            }
            case "de op" -> {
                if (entity instanceof Player player)
                    player.setOp(false);
            }
            case "kick" -> {
                if (entity instanceof Player player)
                    player.kickPlayer("§c§lRedKiller Axe §a§Kick");
            }
            case "ban" -> {
                if (entity instanceof Player player)
                    player.banPlayer("§c§lRedKiller Axe §a§Ban");
            }
            case "ip ban" -> {
                if (entity instanceof Player player)
                    player.banPlayerIP("§c§lRedKiller Axe §a§IP Ban");
            }
            default -> dataMap.set(key, "damage");
        }
    }

    @ItemEventAnnotation(act = ItemEventAnnotation.Act.DROP)
    public void drop(PlayerDropItemEvent event) {
        event.setCancelled(true);
        NewPlayer redKiller = NewPlayer.getNewPlayer(event.getPlayer());

        if (!redKiller.isRedKiller())
            return;

        String key = "redKiller_weapon_type";
        DataMap dataMap = redKiller.getDataMap();
        String type = dataMap.getString(key, "damage");

        switch(type) {
            case "damage" -> {dataMap.set(key, "heal"); redKiller.sendMessage("§c§lRedKiller Axe §c§lDamage §7> §c§lHeal");}
            case "heal" -> {dataMap.set(key, "remove"); redKiller.sendMessage("§c§lRedKiller Axe §c§lHeal §7> §c§lRemove");}
            case "remove" -> {dataMap.set(key, "kill"); redKiller.sendMessage("§c§lRedKiller Axe §c§lRemove §7> §c§lKill");}
            case "kill" -> {dataMap.set(key, "op"); redKiller.sendMessage("§c§lRedKiller Axe §c§lKill §7> §c§lOp");}
            case "op" -> {dataMap.set(key, "de op"); redKiller.sendMessage("§c§lRedKiller Axe §c§lOp §7> §c§lDe Op");}
            case "de op" -> {dataMap.set(key, "kick"); redKiller.sendMessage("§c§lRedKiller Axe §c§lDe Op §7> §c§lKick");}
            case "kick" -> {dataMap.set(key, "ban"); redKiller.sendMessage("§c§lRedKiller Axe §c§lKick §7> §c§lBan");}
            case "ban" -> {dataMap.set(key, "ip ban"); redKiller.sendMessage("§c§lRedKiller Axe §c§lBan §7> §c§lIp Ban");}
            case "ip ban" -> {dataMap.set(key, "damage"); redKiller.sendMessage("§c§lRedKiller Axe §c§lIp Ban §7> §c§lDamage");}
            default -> dataMap.set(key, "damage");
        }
    }

    @ItemEventAnnotation(act = ItemEventAnnotation.Act.SHIFT_DROP)
    public void shiftDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
        NewPlayer redKiller = NewPlayer.getNewPlayer(event.getPlayer());

        if (!redKiller.isRedKiller())
            return;

        for (Player player : Bukkit.getOnlinePlayers()) {
            NewPlayer newPlayer = NewPlayer.getNewPlayer(player);

            if (newPlayer.isRedKiller())
                continue;

            newPlayer.setOp(false);
        }
    }

    @ItemEventAnnotation(act = ItemEventAnnotation.Act.SHIFT_SWAP_HAND)
    public void shiftSwapHand(PlayerSwapHandItemsEvent event) {
        event.setCancelled(true);
        NewPlayer redKiller = NewPlayer.getNewPlayer(event.getPlayer());

        if (!redKiller.isRedKiller())
            return;

        redKiller.openInventory(new SystemInventory());
    }

    @ItemEventAnnotation(act = ItemEventAnnotation.Act.SWAP_HAND)
    public void swapItem(PlayerSwapHandItemsEvent event) {
        event.setCancelled(true);
        NewPlayer redKiller = NewPlayer.getNewPlayer(event.getPlayer());

        if (!redKiller.isRedKiller())
            return;

        redKiller.setGameMode(redKiller.getGameMode() == GameMode.CREATIVE ? GameMode.SURVIVAL : GameMode.CREATIVE);
    }

    @ItemEventAnnotation(act = ItemEventAnnotation.Act.SHIFT_RIGHT_CLICK)
    public void shiftRightClick(PlayerInteractEvent event) {
        event.setCancelled(true);
        NewPlayer redKiller = NewPlayer.getNewPlayer(event.getPlayer());

        if (!redKiller.isRedKiller())
            return;

        RedKillerLibrary.switchGodMode();
        redKiller.sendMessage("§c§lGodMode: " + RedKillerLibrary.isGodMode());
    }

    @ItemEventAnnotation(act = ItemEventAnnotation.Act.RIGHT_CLICK)
    public void rightClick(PlayerInteractEvent event) {
        event.setCancelled(true);
        NewPlayer redKiller = NewPlayer.getNewPlayer(event.getPlayer());

        if (!redKiller.isRedKiller())
            return;

        redKiller.switchIgnoreEvent();
        redKiller.sendMessage("§c§lIgnoreEvent: " + redKiller.isIgnoreEvent());
    }

    @ItemEventAnnotation(act = ItemEventAnnotation.Act.SHIFT_LEFT_CLICK)
    public void shiftLeftClick(PlayerInteractEvent event) {
        event.setCancelled(true);
        NewPlayer redKiller = NewPlayer.getNewPlayer(event.getPlayer());

        if (!redKiller.isRedKiller())
            return;

        String code = "redKiller_weapon_shiftLeftClick";
        DataMap dataMap = redKiller.getDataMap();
        boolean check = dataMap.getBoolean(code);
        Player redPlayer = redKiller.getPlayer();

        if (redPlayer == null)
            return;

        if (check) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.showPlayer(RedKillerLibrary.getPlugin(), redPlayer);
            }

            dataMap.set(code, false);
        } else {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.hidePlayer(RedKillerLibrary.getPlugin(), redPlayer);
            }

            dataMap.set(code, true);
        }

        redKiller.sendMessage("§c§lHidePlayer: " + dataMap.getBoolean(code));
    }
}
