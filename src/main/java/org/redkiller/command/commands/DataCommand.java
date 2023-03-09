package org.redkiller.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.redkiller.command.AbstractCommand;
import org.redkiller.entity.livingentity.player.offline.NewOfflinePlayer;
import org.redkiller.server.ServerDataMap;
import org.redkiller.system.world.WorldDataMap;
import org.redkiller.util.map.dataMap.DataMap;

import java.util.*;

public class DataCommand extends AbstractCommand {
    @Override
    public String getName() {
        return "data";
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {

        if (args.length < 2) {
            sender.sendMessage("Usage: /data <load|save> <player|world|server> [UUID|WorldName]");
            return false;
        }

        String act = args[0];
        String type = args[1];
        DataMap dataMap;

        switch (type) {
            case "player" -> {
                if (args.length < 3) {
                    sender.sendMessage("Usage: /data <load|save> player [UUID]");
                    return false;
                }

                UUID uuid;

                try {
                    uuid = UUID.fromString(args[2]);
                } catch (IllegalArgumentException e) {
                    sender.sendMessage("Invalid UUID.");
                    return false;
                }

                NewOfflinePlayer newOfflinePlayer = NewOfflinePlayer.getNewOfflinePlayer(uuid);

                dataMap = newOfflinePlayer.getPlayerDataMap();
            }
            case "world" -> {
                if (args.length < 3) {
                    sender.sendMessage("Usage: /data <load|save> world [WorldName]");
                    return false;
                }

                String worldName = args[2];
                World world = Bukkit.getWorld(worldName);

                if (world == null) {
                    sender.sendMessage("Invalid world name.");
                    return false;
                }

                dataMap = WorldDataMap.getWorldDataMap(world);
            }
            case "server" -> dataMap = ServerDataMap.getInstance();
            default -> {
                sender.sendMessage("Usage: /data <load|save> <player|world|server> [UUID|WorldName]");
                return false;
            }
        }

        if (act.equals("load")) {
            dataMap.load();
            sender.sendMessage("Data loaded.");
            return true;
        } else if (act.equals("save")) {
            dataMap.save();
            sender.sendMessage("Data saved.");
            return true;
        }

        sender.sendMessage("Usage: /data <load|save> <player|world|server> [UUID|WorldName]");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (args.length == 1)
            return Arrays.asList("load", "save");

        if (args.length == 2)
            return Arrays.asList("player", "world", "server");


        if (args.length == 3) {
            String type = args[1];

            if (type.equals("player"))
                return Collections.singletonList("[UUID]");

            if (type.equals("world"))
                return Collections.singletonList("[WorldName]");
        }
        return new ArrayList<>();
    }
}
