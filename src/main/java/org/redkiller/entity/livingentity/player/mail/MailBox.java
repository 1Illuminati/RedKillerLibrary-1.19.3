package org.redkiller.entity.livingentity.player.mail;

import org.redkiller.util.map.dataMap.DataMap;

import java.util.List;

public interface MailBox {
    public void add(Mail mail);

    public void remove(Mail mail);

    public void remove(int index);

    public void clear();

    public Mail get(int index);

    public Mail[] getMails();

    public int size();

    public boolean isEmpty();

    public boolean contains(Mail mail);

    public List<DataMap> toDataMaps();
}
