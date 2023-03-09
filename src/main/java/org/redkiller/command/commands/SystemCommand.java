package org.redkiller.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.redkiller.command.AbstractPlayerCommand;
import org.redkiller.system.SystemInventory;

import java.util.ArrayList;
import java.util.List;

public class SystemCommand extends AbstractPlayerCommand {
    @Override
    public String getName() {
        return "system";
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        ((Player) sender).openInventory(new SystemInventory());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String label, String[] args) {
        return new ArrayList<>();
    }
}
