package org.redkiller.event.listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.redkiller.RedKillerLibrary;
import org.redkiller.entity.livingentity.player.NewPlayer;
import org.redkiller.event.AbstractEventListener;
import org.redkiller.event.AreaEvent;

public class CustomEvent extends AbstractEventListener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void areaEvent(AreaEvent event) {
        NewPlayer player = event.getPlayer();
        Location location = player.getLocation();
        RedKillerLibrary.sendDebugLog("AreaEvent : " + player.getUniqueId() + " " + event.getAreaAct() + " " +
                location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ());
    }
}
