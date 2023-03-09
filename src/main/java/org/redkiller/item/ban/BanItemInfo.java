package org.redkiller.item.ban;

import org.bukkit.Material;

public interface BanItemInfo {

    public Material getMaterial();

    public boolean getAllow(BanItemAct act);

    public void setAllow(BanItemAct act, boolean allow);
}