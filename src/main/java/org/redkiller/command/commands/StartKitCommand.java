package org.redkiller.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.redkiller.command.AbstractPlayerCommand;
import org.redkiller.entity.livingentity.player.NewPlayer;
import org.redkiller.item.startkit.StartKit;
import org.redkiller.util.map.dataMap.DataMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartKitCommand extends AbstractPlayerCommand {
    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        if (!sender.isOp())
            return new ArrayList<>();

        if (args.length == 1)
            return Arrays.asList("clear", "give");

        return null;
    }

    @Override
    public String getName() {
        return "startKit";
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        final String startKitKey = "redKillerLibrary.startKit";
        NewPlayer player = NewPlayer.getNewPlayer((Player) sender);
        DataMap dataMap = player.getDataMap();
        boolean isStartKit = dataMap.getBoolean(startKitKey);

        if (args.length == 0) {
            if (isStartKit) {
                player.sendMessage("이미 기본템을 지급받으셨습니다.");
            } else {
                player.getInventory().addItem(StartKit.getInstance().getStartKit());
                dataMap.set(startKitKey, true);
                player.sendMessage("기본템 지급 완료.");
            }
        } else {
            if (!sender.isOp()) {
                player.sendMessage("당신은 이 명령어를 사용할 권한이 존재하지 않습니다!");
                return false;
            } else if (args.length < 2) {
                player.sendMessage("사용법: /startKit <clear|give> [player]");
                return false;
            } else {
                String command = args[0];
                String targetName = args[1];
                NewPlayer target = NewPlayer.getNewPlayer(targetName);

                if (target == null) {
                    player.sendMessage("해당 플레이어는 존재하지 않습니다!");
                    return false;
                }

                switch (command) {
                    case "clear" -> {
                        target.getDataMap().set(startKitKey, false);
                        player.sendMessage("기본템 지급 상태를 초기화하였습니다.");
                    }
                    case "give" -> {
                        target.getInventory().addItem(StartKit.getInstance().getStartKit());
                        player.sendMessage("기본템 지급 완료.");
                    }
                    default -> {
                        player.sendMessage("사용법: /startKit <clear|give> [player]");
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
