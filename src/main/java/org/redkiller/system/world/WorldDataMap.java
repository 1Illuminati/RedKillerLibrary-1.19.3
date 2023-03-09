package org.redkiller.system.world;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.redkiller.RedKillerLibrary;
import org.redkiller.system.gamerule.CustomGameRule;
import org.redkiller.system.gamerule.HasCustomGameRule;
import org.redkiller.util.file.nbt.NBTHelper;
import org.redkiller.util.key.RedKillerKey;
import org.redkiller.util.map.dataMap.DataMap;

import java.util.*;

public class WorldDataMap extends DataMap implements HasCustomGameRule {
    private static final HashMap<UUID, WorldDataMap> worldDataMaps = new HashMap<>();

    public static WorldDataMap getWorldDataMap(World world) {
        UUID worldUID = world.getUID();
        worldDataMaps.putIfAbsent(worldUID, new WorldDataMap(world));
        return worldDataMaps.get(worldUID);
    }

    public static void saveAllWorldDataMap() {
        for (WorldDataMap worldDataMap : worldDataMaps.values()) {
            worldDataMap.save();
        }

        RedKillerLibrary.sendDebugLog("All WorldDataMap Saved");
    }

    public static void reloadAllWorldDataMap() {
        for (WorldDataMap worldDataMap : worldDataMaps.values()) {
            worldDataMap.load();
        }

        RedKillerLibrary.sendDebugLog("All WorldDataMap Reload");
    }

    public static void registerArea(World world, InterfaceArea area) {
        WorldDataMap worldDataMap = getWorldDataMap(world);
        worldDataMap.registerArea(area);
    }

    private final Map<RedKillerKey, InterfaceArea> interfaceAreas = new HashMap();
    private final World world;
    private final String path;
    private DataMap customGameRuleDataMap = new DataMap();
    protected WorldDataMap(World world) {
        this.world = world;
        this.path = "plugins/redKillerLibrary/nbt/world/" + world.getName() + ".nbt";
        this.load();
    }

    public World getWorld() {
        return world;
    }

    @Override
    public <T> T getCustomGameRuleValue(CustomGameRule<T> gameRule) {
        Object value = customGameRuleDataMap.get(gameRule.getName(), gameRule.getDefaultValue());
        return (T) value;
    }

    @Override
    public <T> void setCustomGameRuleValue(CustomGameRule<T> gameRule, T value) {
        customGameRuleDataMap.set(gameRule.getName(), value);
    }

    @Override
    public void save() {
        this.set("RedKillerLibrary CustomGameRule", customGameRuleDataMap);
        NBTHelper.saveDataMap(path, this);
    }

    @Override
    public void load() {
        this.copy(NBTHelper.loadDataMap(path));
        this.customGameRuleDataMap = this.getDataMap("RedKillerLibrary CustomGameRule");
    }

    public void registerArea(InterfaceArea interfaceArea) {
        this.interfaceAreas.put(interfaceArea.getKey(), interfaceArea);
    }

    public List<InterfaceArea> containArea(Vector vector) {
        List<InterfaceArea> areas = new ArrayList<>();
        for (InterfaceArea interfaceArea : this.interfaceAreas.values()) {
            if (interfaceArea.contain(vector))
                areas.add(interfaceArea);
        }

        return areas;
    }

    public List<InterfaceArea> containArea(Location location) {
        return this.containArea(location.toVector());
    }

    public List<InterfaceArea> containArea(BoundingBox boundingBox) {
        List<InterfaceArea> areas = new ArrayList<>();
        for (InterfaceArea interfaceArea : this.interfaceAreas.values()) {
            if (interfaceArea.contain(boundingBox))
                areas.add(interfaceArea);
        }

        return areas;
    }

    public List<InterfaceArea> containArea(InterfaceArea checkArea) {
        List<InterfaceArea> areas = new ArrayList<>();
        for (InterfaceArea interfaceArea : this.interfaceAreas.values()) {
            if (interfaceArea.contain(checkArea))
                areas.add(interfaceArea);
        }

        return areas;
    }

    public List<InterfaceArea> overlapArea(BoundingBox boundingBox) {
        List<InterfaceArea> areas = new ArrayList<>();
        for (InterfaceArea interfaceArea : this.interfaceAreas.values()) {
            if (interfaceArea.overlap(boundingBox))
                areas.add(interfaceArea);
        }

        return areas;
    }

    public List<InterfaceArea> overlapArea(InterfaceArea checkArea) {
        List<InterfaceArea> areas = new ArrayList<>();
        for (InterfaceArea interfaceArea : this.interfaceAreas.values()) {
            if (interfaceArea.overlap(checkArea))
                areas.add(interfaceArea);
        }

        return areas;
    }

    public void removeArea(InterfaceArea interfaceArea) {
        this.interfaceAreas.remove(interfaceArea.getKey());
    }

    public void removeArea(RedKillerKey redKillerKey) {
        this.interfaceAreas.remove(redKillerKey);
    }

    public List<InterfaceArea> getAllAreas() {
        return new ArrayList<>(this.interfaceAreas.values());
    }
}
