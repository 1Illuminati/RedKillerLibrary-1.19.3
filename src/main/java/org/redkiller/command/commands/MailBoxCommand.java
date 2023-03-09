package org.redkiller.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.redkiller.command.AbstractPlayerCommand;
import org.redkiller.entity.livingentity.player.mail.MailBoxInventory;

import java.util.ArrayList;
import java.util.List;

public class MailBoxCommand extends AbstractPlayerCommand {
    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return new ArrayList<>();
    }

    @Override
    public String getName() {
        return "mailBox";
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        player.openInventory(new MailBoxInventory(player.getUniqueId()));
        return true;
    }
}
