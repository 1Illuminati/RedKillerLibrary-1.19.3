package org.redkiller.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.redkiller.entity.livingentity.player.NewPlayer;
import org.redkiller.system.world.AreaAct;
import org.redkiller.system.world.InterfaceArea;

public class AreaEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    private final NewPlayer player;
    private final InterfaceArea interfaceArea;
    private final AreaAct areaAct;

    private final Block block;
    private final Item item;
    public AreaEvent(NewPlayer player, InterfaceArea interfaceArea, AreaAct areaAct, Block block, Item item) {
        this.player = player;
        this.interfaceArea = interfaceArea;
        this.areaAct = areaAct;
        this.block = block;
        this.item = item;

        this.isCancelled = interfaceArea.playerAct(player, areaAct);
    }

    public NewPlayer getPlayer() {
        return player;
    }

    public InterfaceArea getInterfaceArea() {
        return interfaceArea;
    }

    public AreaAct getAreaAct() {
        return areaAct;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

    public Block getBlock() {
        return block;
    }


    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS_LIST;
    }
}
