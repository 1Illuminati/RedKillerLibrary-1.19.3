package org.redkiller.entity.livingentity.player.mail;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.redkiller.entity.livingentity.player.NewPlayer;
import org.redkiller.entity.livingentity.player.offline.NewOfflinePlayer;
import org.redkiller.inventory.CustomInventory;

import java.util.UUID;

public class MailBoxInventory extends CustomInventory {

    private final MailBox mailBox;

    public MailBoxInventory(UUID playerUUID) {
        super("§fMail Box", 54);

        this.mailBox = NewOfflinePlayer.getNewOfflinePlayer(playerUUID).getMailBox();

        int i = 0;
        for (Mail mail : mailBox.getMails()) {
            if (i >= 54)
                break;

            this.setItem(i, mail.toItemStack());

            this.putButton(i, event -> {
                event.setCancelled(true);

                ClickType clickType = event.getClick();
                NewPlayer player = NewPlayer.getNewPlayer((Player) event.getWhoClicked());
                player.setIgnoreInvCloseEvent(true);

                if (clickType == ClickType.LEFT) {
                    player.openInventory(new MailInventory(mail, playerUUID));
                } else if (clickType == ClickType.SHIFT_LEFT) {
                    mailBox.remove(mail);
                    player.openInventory(new MailBoxInventory(playerUUID));
                }
            });
            i++;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MailBoxInventory) {
            MailBoxInventory mailBoxInventory = (MailBoxInventory) obj;
            return mailBoxInventory.mailBox.equals(this.mailBox);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return mailBox.hashCode();
    }
}
