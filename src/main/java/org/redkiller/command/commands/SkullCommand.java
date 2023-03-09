package org.redkiller.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.redkiller.command.AbstractPlayerCommand;
import org.redkiller.entity.livingentity.player.NewPlayer;
import org.redkiller.item.ItemHelper;

import java.util.List;

public class SkullCommand extends AbstractPlayerCommand {
    @Override
    public String getName() {
        return "skull";
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        NewPlayer player = NewPlayer.getNewPlayer((Player) sender);
        Player headOwner;
        if (args.length == 0)
            headOwner = player.getPlayer();
        else
            headOwner = Bukkit.getPlayer(args[0]);
        String value = args[0];
        ItemStack head;
        if (headOwner != null) {
            head = ItemHelper.getPlayerSkull(headOwner);
        } else {
            head = ItemHelper.getSkull(value);
        }

        player.getInventory().addItem(head);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return null;
    }
}
