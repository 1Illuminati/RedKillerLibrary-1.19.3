package org.redkiller.entity.livingentity.player.npc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.redkiller.entity.livingentity.player.NewPlayer;

import java.util.HashMap;
import java.util.UUID;

public class NpcPlayer extends NewPlayer {
    private static final HashMap<UUID, NpcPlayer> newNpcPlayerMap = new HashMap<>();

    public static NpcPlayer getNPCPlayer(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);

        if (player == null)
            return null;

        return NpcPlayer.getNPCPlayer(player);
    }

    public static NpcPlayer getNPCPlayer(String playerName) {
        Player player = Bukkit.getPlayer(playerName);

        if (player == null)
            return null;

        return NpcPlayer.getNPCPlayer(player);
    }

    public static NpcPlayer getNPCPlayer(Player player) {

        if (!newNpcPlayerMap.containsKey(player.getUniqueId()))
            newNpcPlayerMap.put(player.getUniqueId(), new NpcPlayer(player));

        return newNpcPlayerMap.get(player.getUniqueId());
    }

    protected NpcPlayer(Player npcPlayer) {
        super(npcPlayer);
    }
}
