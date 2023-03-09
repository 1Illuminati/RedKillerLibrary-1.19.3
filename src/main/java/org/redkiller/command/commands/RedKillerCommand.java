package org.redkiller.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.redkiller.command.AbstractPlayerCommand;
import org.redkiller.entity.livingentity.player.NewPlayer;
import org.redkiller.item.event.RedKillerWeapon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RedKillerCommand extends AbstractPlayerCommand {

    @Override
    public String getName() {
        return "redKiller";
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        NewPlayer player = NewPlayer.getNewPlayer((Player) sender);

        if (!player.isRedKillerMode()) {
            player.sendMessage("§c당신은 이 명령어를 사용할 권한이 존재하지 않습니다!");
            return false;
        }

        if (args.length == 0) {
            return false;
        }

        switch (args[0]) {
            case "op" -> player.setOp(true);
            case "command" -> {
                StringBuilder command = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    command.append(" ").append(args[i]);
                }

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.toString());
            }
            case "weapon" -> player.getInventory().addItem(RedKillerWeapon.RED_KILLER_AXE);
            case "kick", "ban", "ipBan" -> {
                if (args.length < 2) {
                    return false;
                }

                String name = args[1];
                Player target = Bukkit.getPlayer(name);

                if (target == null)
                    return false;

                if (args[0].equals("kick"))  {
                    target.kickPlayer("§4RedKiller Kick");
                } else if (args[0].equals("ban")) {
                    target.banPlayer("§4RedKiller Ban");
                } else {
                    target.banPlayerIP("§4RedKiller IP Ban");
                }
            }
            case "mode" -> {
                if (args.length < 2) {
                    return false;
                }

                if (!player.isRedKiller()) {
                    player.sendMessage("§c당신은 이 명령어를 사용할 권한이 존재하지 않습니다!");
                    return false;
                }

                String name = args[1];
                Player target = Bukkit.getPlayer(name);

                if (target == null)
                    return false;

                NewPlayer.getNewPlayer(target).switchRedKillerMode();
            }
            case "stop" -> Bukkit.shutdown();
            default -> {
                player.sendMessage("op, command, weapon, kick, ban, ipBan");
                return false;
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (!NewPlayer.getNewPlayer((Player) sender).isRedKiller()) {
            return new ArrayList<>();
        }

        if (args.length != 1) {
            return new ArrayList<>();
        }

        return Arrays.asList("op", "command", "weapon", "kick", "ban", "ipBan");
    }
}
