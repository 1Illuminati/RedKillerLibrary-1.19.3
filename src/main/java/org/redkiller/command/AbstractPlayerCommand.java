package org.redkiller.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractPlayerCommand extends AbstractCommand {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals(getName())) {
            if (sender instanceof Player) {
                return onCommand(sender, label, args);
            }
            sender.sendMessage("§4This Command only use on Player");
        }
        return false;
    }

    public abstract String getName();

    public abstract boolean onCommand(CommandSender sender, String label, String[] args);
}
