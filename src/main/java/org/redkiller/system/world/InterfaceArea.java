package org.redkiller.system.world;

import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.redkiller.entity.livingentity.player.NewPlayer;
import org.redkiller.util.key.Keyed;


public interface InterfaceArea extends Keyed {
    public BoundingBox getBoundingBox();

    /**
     * area안에 vector가 포함되어있는지 확인
     * @param vector 확인할 위치
     * @return 포함되어있으면 true, 아니면 false
     */
    public boolean contain(Vector vector);

    /**
     * area안에 boundingBox가 포함되어있는지 확인
     * @param boundingBox 확인할 위치
     * @return 포함되어있으면 true, 아니면 false
     */
    public boolean contain(BoundingBox boundingBox);
    /**
     * area안에 interfaceArea가 포함되어있는지 확인
     * @param interfaceArea 확인할 위치
     * @return 포함되어있으면 true, 아니면 false
     */
    public boolean contain(InterfaceArea interfaceArea);

    /**
     * area안에 interfaceArea가 겹치는 구역이 있는지 확인
     * @param interfaceArea 확인할 위치
     * @return 겹치는 공간이 존재하면 true, 아니면 false
     */
    public boolean overlap(InterfaceArea interfaceArea);

    /**
     * area안에 boundingBox가 겹치는 구역이 있는지 확인
     * @param boundingBox 확인할 위치
     * @return 겹치는 공간이 존재하면 true, 아니면 false
     */
    public boolean overlap(BoundingBox boundingBox);

    public boolean playerAct(NewPlayer newPlayer, AreaAct act);
}
