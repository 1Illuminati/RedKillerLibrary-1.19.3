package org.redkiller.system.gamerule;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.redkiller.RedKillerLibrary;
import org.redkiller.entity.livingentity.player.NewPlayer;
import org.redkiller.inventory.CustomInventory;
import org.redkiller.item.ItemBuilder;
import org.redkiller.system.SystemInventory;

import java.util.Arrays;
import java.util.List;

public class GameRuleInventory extends CustomInventory {
    private final HasCustomGameRule hasCustomGameRule;

    public GameRuleInventory(HasCustomGameRule hasCustomGameRule) {
        super("CustomGameRule Inv",  27);
        this.hasCustomGameRule = hasCustomGameRule;
        this.makeGuiBoolean(0, CustomGameRule.CRAFT, Material.CRAFTING_TABLE);
        this.makeGuiBoolean(1, CustomGameRule.SEX, Material.EGG);
        this.makeGuiBoolean(2, CustomGameRule.LEFT_HAND, Material.SHIELD);
        this.makeGuiBoolean(3, CustomGameRule.ENCHANTMENT, Material.ENCHANTING_TABLE);
        this.makeGuiBoolean(4, CustomGameRule.PVP, Material.DIAMOND_AXE);
        this.makeGuiBoolean(5, CustomGameRule.RIDING, Material.SADDLE);
        this.makeGuiBoolean(6, CustomGameRule.SPECTATOR_MOVE, Material.OBSERVER);
        this.makeGuiBoolean(7, CustomGameRule.DROP, Material.DIAMOND);
        this.makeGuiBoolean(8, CustomGameRule.MOVE, Material.FEATHER);
        this.makeGuiBoolean(9, CustomGameRule.BREAK, Material.GOLDEN_PICKAXE);
        this.makeGuiBoolean(10, CustomGameRule.PLACE, Material.DIRT);
        this.makeGuiBoolean(11, CustomGameRule.FISHING, Material.FISHING_ROD);
        this.makeGuiBoolean(12, CustomGameRule.CHAT, Material.SPRUCE_SIGN);
        this.makeGuiBoolean(13, CustomGameRule.PICK_UP, Material.ITEM_FRAME);

        if (RedKillerLibrary.isGodMode())
            this.makeGuiBoolean(14, CustomGameRule.COMMAND, Material.COMMAND_BLOCK);
    }

    private void makeGuiBoolean(int slot, CustomGameRule<Boolean> customGameRule, Material material) {
        Boolean value = hasCustomGameRule.getCustomGameRuleValue(customGameRule);
        ItemStack itemStack = new ItemBuilder(material).setDisplayName("§f" + customGameRule.getName()).setLore(Arrays.asList(
                value ? "§aTRUE" : "§cFALSE",
                "",
                "§f§o(Click to change the value)")).build();
        this.setItem(slot, itemStack);

        this.putButton(slot, new Button() {
            private final CustomGameRule<Boolean> gameRule = customGameRule;
            @Override
            public void onClick(InventoryClickEvent event) {
                event.setCancelled(true);
                NewPlayer player = NewPlayer.getNewPlayer((Player) event.getWhoClicked());
                hasCustomGameRule.setCustomGameRuleValue(gameRule, !hasCustomGameRule.getCustomGameRuleValue(gameRule));
                player.openIgnoreEventInv(new GameRuleInventory(hasCustomGameRule));
            }
        });
    }

    private void makeGuiDouble(int slot, CustomGameRule<Double> customGameRule, Material material) {
        Double value = hasCustomGameRule.getCustomGameRuleValue(customGameRule);
        ItemStack itemStack = new ItemBuilder(material).setDisplayName("§f" + customGameRule.getName()).setLore(List.of("§7" + Math.round(value * 100) / 100)).build();
        this.setItem(slot, itemStack);

        this.putButton(slot, new Button() {
            private final CustomGameRule<Double> gameRule = customGameRule;
            @Override
            public void onClick(InventoryClickEvent event) {
                event.setCancelled(true);
                Double value = hasCustomGameRule.getCustomGameRuleValue(gameRule);
                ClickType clickType = event.getClick();
                NewPlayer player = NewPlayer.getNewPlayer((Player) event.getWhoClicked());

                switch(clickType) {
                    case LEFT-> hasCustomGameRule.setCustomGameRuleValue(customGameRule, value + 0.1);
                    case RIGHT-> hasCustomGameRule.setCustomGameRuleValue(customGameRule, value - 0.1);
                    case SHIFT_LEFT-> hasCustomGameRule.setCustomGameRuleValue(customGameRule, value + 1);
                    case SHIFT_RIGHT-> hasCustomGameRule.setCustomGameRuleValue(customGameRule, value - 1);
                    case MIDDLE-> hasCustomGameRule.setCustomGameRuleValue(customGameRule, 0.0);
                    default -> {}
                }

                player.openIgnoreEventInv(new GameRuleInventory(hasCustomGameRule));
            }
        });
    }

    @Override
    public void close(InventoryCloseEvent event) {
        NewPlayer player = NewPlayer.getNewPlayer((Player) event.getPlayer());
        player.openIgnoreEventInv(new SystemInventory());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GameRuleInventory gameRuleInventory) {
            return gameRuleInventory.hasCustomGameRule.equals(this.hasCustomGameRule);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.hasCustomGameRule.hashCode();
    }
}
