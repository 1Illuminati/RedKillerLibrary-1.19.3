package org.redkiller.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.redkiller.command.AbstractPlayerCommand;
import org.redkiller.entity.livingentity.player.NewPlayer;
import org.redkiller.entity.livingentity.player.mail.Mail;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestCommand extends AbstractPlayerCommand {
    @Override
    public String getName() {
        return "test";
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        NewPlayer player = NewPlayer.getNewPlayer((Player) sender);
        Mail mail = new Mail(UUID.randomUUID().toString()).addAll(player.getInventory().getContents()).addDescription("test");
        player.sendMail(mail);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return new ArrayList<>();
    }
}
