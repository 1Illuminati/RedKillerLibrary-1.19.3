package org.redkiller.util.map.dataMap;

import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;
import org.redkiller.util.file.nbt.NBTHelper;
import org.redkiller.util.key.RedKillerKey;

import java.util.*;

public class DataMap {

    public static DataMap fromNBT(NBTTagCompound nbtTagCompound) {
        Object object = NBTHelper.nbtToObj(nbtTagCompound);

        if (object instanceof DataMap dataMap)
            return dataMap;
        else
            throw new IllegalArgumentException("NBT is not a DataMap");
    }
    private Map<String, Object> map = new HashMap<>();

    public void copy(DataMap dataMap) {
        map = dataMap.getMap();
    }

    public ItemStack getItemStack(String key) {
        return getItemStack(key, new ItemStack(Material.AIR));
    }

    public ItemStack getItemStack(String key, ItemStack nullValue) {
        if (!map.containsKey(key))
            put(key, nullValue);

        return (ItemStack) map.get(key);
    }

    public Location getLocation(String key) {
        return getLocation(key, new Location(Bukkit.getWorlds().get(0), 0, 0, 0));
    }

    public Location getLocation(String key, Location nullValue) {
        if (!map.containsKey(key))
            put(key, nullValue);

        return (Location) map.get(key);
    }

    public byte getByte(String key) {
        return getByte(key, (byte) 0);
    }

    public byte getByte(String key, byte nullValue) {
        if (!map.containsKey(key))
            put(key, nullValue);

        return (byte) map.get(key);
    }

    public void addByte(String key, byte value) {
        put(key, getByte(key) + value);
    }

    public short getShort(String key) {
        return getShort(key, (short) 0);
    }

    public short getShort(String key, short nullValue) {
        if (!map.containsKey(key))
            put(key, nullValue);

        return (short) map.get(key);
    }

    public void addShort(String key, short value) {
        put(key, getShort(key) + value);
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int nullValue) {
        if (!map.containsKey(key))
            put(key, nullValue);

        return (int) map.get(key);
    }

    public void addInt(String key, int value) {
        put(key, getInt(key) + value);
    }

    public float getFloat(String key) {
        return getFloat(key, 0);
    }

    public float getFloat(String key, float nullValue) {
        if (!map.containsKey(key))
            put(key, nullValue);

        return (float) map.get(key);
    }

    public void addFloat(String key, float value) {
        put(key, getFloat(key) + value);
    }

    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    public double getDouble(String key, double nullValue) {
        if (!map.containsKey(key))
            put(key, nullValue);

        return (double) map.get(key);
    }

    public void addDouble(String key, double value) {
        put(key, getDouble(key) + value);
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String nullValue) {
        if (!map.containsKey(key))
            put(key, nullValue);

        return map.get(key).toString();
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean nullValue) {
        if (!map.containsKey(key))
            put(key, nullValue);

        return (boolean) map.get(key);
    }

    public Long getLong(String key) {
        return getLong(key, 0L);
    }

    public Long getLong(String key, Long nullValue) {
        if (!map.containsKey(key))
            put(key, nullValue);

        return (Long) map.get(key);
    }

    public void addLong(String key, Long value) {
        put(key, getLong(key) + value);
    }

    public Object get(String key) {
        return this.get(key, null);
    }

    public Object get(String key, Object nullValue) {
        if (!map.containsKey(key))
            put(key, nullValue);

        return map.get(key);
    }

    public <T> T getClass(String key, Class<T> clazz) {
        return this.getClass(key, clazz, null);
    }

    public <T> T getClass(String key, Class<T> clazz, T nullValue) {
        if (!map.containsKey(key)) {
            put(key, nullValue);
            return null;
        }

        Object value = map.get(key);

        if (value.getClass() != clazz) {
            throw new ClassCastException("Value is not of type " + clazz.getName());
        }

        return (T) map.get(key);
    }
    public <T> List<T> getList(String key) {
        return this.getList(key,new ArrayList<>());
    }

    public <T> List<T> getList(String key, List<?> nullValue) {
        if (!map.containsKey(key)) {
            put(key, nullValue);
        }

        return (List<T>) map.get(key);
    }

    public DataMap getDataMap(String key) {
        return this.getDataMap(key, new DataMap());
    }

    public DataMap getDataMap(String key, DataMap nullValue) {
        if (!map.containsKey(key)) {
            put(key, nullValue);
        }

        return (DataMap) map.get(key);
    }

    public BoundingBox getBoundingBox(String key) {
        return this.getBoundingBox(key, new BoundingBox(0,0,0,0,0,0));
    }

    public BoundingBox getBoundingBox(String key, BoundingBox nullValue) {
        if (!map.containsKey(key)) {
            put(key, nullValue);
        }

        return (BoundingBox) map.get(key);
    }

    public UUID getUUID(String key) {
        return this.getUUID(key, UUID.randomUUID());
    }

    public UUID getUUID(String key, UUID nullValue) {
        if (!map.containsKey(key)) {
            put(key, nullValue);
        }

        return (UUID) map.get(key);
    }

    public RedKillerKey getRedKillerKey(String key) {
        return this.getRedKillerKey(key, RedKillerKey.randomKey("random"));
    }

    public RedKillerKey getRedKillerKey(String key, RedKillerKey nullValue) {
        if (!map.containsKey(key)) {
            put(key, nullValue);
        }

        return (RedKillerKey) map.get(key);
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }

    public DataMap set(String key, Object value) {
        map.put(key, value);
        return this;
    }
    public void remove(String key) {
        map.remove(key);
    }
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public NBTTagCompound toNBT() {
        return (NBTTagCompound) NBTHelper.objToNBT(this);
    }

    public Set<String> keySet() {
        return map.keySet();
    }

    public Collection<Object> values() {
        return map.values();
    }

    public Set<Map.Entry<String, Object>> entrySet() {
        return map.entrySet();
    }

    public String toString() {
        return map.toString();
    }

    public void save() {
        throw new UnsupportedOperationException("DataMap No Supported this method plz extend and use");
    }

    public void load() {
        throw new UnsupportedOperationException("DataMap No Supported this method plz extend and use");
    }
}
