package org.redkiller.entity.livingentity.player.mail;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.redkiller.entity.livingentity.player.NewPlayer;
import org.redkiller.inventory.CustomInventory;

import java.util.UUID;

public class MailInventory extends CustomInventory {
    private final Mail mail;
    private final UUID playerUUID;
    public MailInventory(Mail mail, UUID playerUUID) {
        super("§f" + mail.getName() + " mail", 54);
        this.mail = mail;
        this.playerUUID = playerUUID;

        int i = 0;
        for (ItemStack itemStack : mail.getItemStacks()) {
            this.setItem(i, itemStack);

            this.putButton(i++, event -> {
                event.setCancelled(true);

                int slot = event.getRawSlot();
                NewPlayer player = NewPlayer.getNewPlayer((Player) event.getWhoClicked());
                ItemStack item = this.getItem(slot);
                mail.remove(slot);

                if (item == null)
                    return;

                player.getInventory().addItem(item);
                player.openIgnoreEventInv(new MailInventory(mail, playerUUID));
            });
        }
    }

    @Override
    public void close(InventoryCloseEvent event) {
        NewPlayer player = NewPlayer.getNewPlayer((Player) event.getPlayer());
        player.openIgnoreEventInv(new MailBoxInventory(playerUUID));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MailInventory mailInventory) {
            return mailInventory.mail.equals(this.mail);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return mail.hashCode();
    }
}
