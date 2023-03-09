package org.redkiller.event.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.redkiller.entity.livingentity.player.NewPlayer;
import org.redkiller.entity.livingentity.player.PlayerDataMap;
import org.redkiller.event.AbstractEventListener;
import org.redkiller.util.map.dataMap.DataMap;

public class ConnectionEvent extends AbstractEventListener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void playerJoinEvent(PlayerJoinEvent event) {
        NewPlayer player = NewPlayer.getNewPlayer(event.getPlayer());
        DataMap dataMap = player.getDataMap();
        dataMap.addInt("RedKillerLibrary.PlayerConnectNum", 1);
        dataMap.set("RedKillerLibrary.PlayerConnect", true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerQuitEvent(PlayerQuitEvent event) {
        NewPlayer player = NewPlayer.getNewPlayer(event.getPlayer());
        PlayerDataMap playerDataMap = player.getDataMap();
        playerDataMap.set("RedKillerLibrary.PlayerConnect", false);
        player.save();
    }
}
