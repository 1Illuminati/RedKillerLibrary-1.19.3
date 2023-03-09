package org.redkiller.util.file.nbt;

import net.minecraft.nbt.*;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R2.inventory.CraftItemStack;
import org.bukkit.util.BoundingBox;
import org.redkiller.util.key.RedKillerKey;
import org.redkiller.util.map.dataMap.DataMap;


import java.util.*;

public class NBTHelper {
    private static final String COMPOUND_TYPE = "redKillerLibrary_CompoundType";

    private NBTHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static void saveDataMap(String path, DataMap dataMap) {
        NBTTagCompound nbtTagCompound = (NBTTagCompound) objToNBT(dataMap);
        NBTFile nbtFile = new NBTFile(path, nbtTagCompound);
        nbtFile.write();
    }

    public static DataMap loadDataMap(String path) {
        NBTFile nbtFile = new NBTFile(path);
        nbtFile.read();
        return DataMap.fromNBT(nbtFile.getCompound());
    }

    public static Object nbtToObj(NBTBase nbtBase) {
        if (nbtBase instanceof NBTTagInt nbtTagInt) {
            return nbtTagInt.g();
        } else if (nbtBase instanceof NBTTagDouble nbtTagDouble) {
            return nbtTagDouble.j();
        } else if (nbtBase instanceof NBTTagByte nbtTagByte) {
            return nbtTagByte.i();
        } else if (nbtBase instanceof NBTTagLong nbtTagLong) {
            return nbtTagLong.f();
        } else if (nbtBase instanceof NBTTagFloat nbtTagFloat) {
            return nbtTagFloat.k();
        } else if (nbtBase instanceof NBTTagString) {
            String result = nbtBase.f_();

            if (result.equals("tRuE"))
                return true;
            else if (result.equals("fAlSe"))
                return false;

            return nbtBase.f_();
        } else if (nbtBase instanceof NBTTagShort nbtTagShort) {
            return nbtTagShort.h();
        } else if (nbtBase instanceof NBTTagByteArray nbtTagByteArray) {
            return nbtTagByteArray.e();
        } else if (nbtBase instanceof NBTTagIntArray nbtTagIntArray) {
            return nbtTagIntArray.g();
        } else if (nbtBase instanceof NBTTagLongArray nbtTagLongArray) {
            return nbtTagLongArray.g();
        } else if (nbtBase instanceof NBTTagCompound nbtTagCompound) {
            String type = "";
            if (nbtTagCompound.e(COMPOUND_TYPE)) {
                type = nbtTagCompound.l(COMPOUND_TYPE);
                nbtTagCompound.r(COMPOUND_TYPE);
            }

            switch (type) {
                case "itemStack" -> {
                    return CraftItemStack.asBukkitCopy(ItemStack.a(nbtTagCompound));
                }
                case "map" -> {
                    Iterator<String> iteratorMap = nbtTagCompound.e().iterator();
                    HashMap<String, Object> map = new HashMap<>();
                    while (iteratorMap.hasNext()) {
                        String key = iteratorMap.next();
                        map.put(key, nbtToObj(nbtTagCompound.c(key)));
                    }
                    return map;
                }
                case "location" -> {
                    World world = Bukkit.getWorld(nbtTagCompound.l("world"));
                    if (world == null)
                        world = Bukkit.getWorlds().get(0);
                    return new Location(world, nbtTagCompound.k("x"), nbtTagCompound.k("y"), nbtTagCompound.k("z"),
                            nbtTagCompound.j("yaw"), nbtTagCompound.j("pitch"));
                }
                case "boundingBox" -> {
                    return new BoundingBox(nbtTagCompound.k("minX"), nbtTagCompound.k("minY"), nbtTagCompound.k("minZ"),
                            nbtTagCompound.k("maxX"), nbtTagCompound.k("maxY"), nbtTagCompound.k("maxZ"));
                }
                case "uuid" -> {
                    return UUID.fromString(nbtTagCompound.l("uuid"));
                }
                case "redKillerKey" -> {
                    return new RedKillerKey(nbtTagCompound.l("key"), nbtTagCompound.l("value"));
                }
                default -> {
                    Iterator<String> iteratorDataMap = nbtTagCompound.e().iterator();
                    DataMap dataMap = new DataMap();
                    while (iteratorDataMap.hasNext()) {
                        String key = iteratorDataMap.next();
                        dataMap.put(key, nbtToObj(nbtTagCompound.c(key)));
                    }
                    return dataMap;
                }
            }
        } else if (nbtBase instanceof NBTTagList nbtTagList) {
            List<Object> list = new ArrayList<>();
            for (NBTBase base : nbtTagList) {
                list.add(nbtToObj(base));
            }

            return list;
        }

        throw new UnsupportedClassVersionError();
    }

    public static NBTBase objToNBT(Object object) {
        if (object == null) {
            throw new NullPointerException("Object is null");
        } else if (object instanceof String value) {
            return NBTTagString.a(value);
        } else if (object instanceof Integer value) {
            return NBTTagInt.a(value);
        } else if (object instanceof Double value) {
            return NBTTagDouble.a(value);
        } else if (object instanceof Float value) {
            return NBTTagFloat.a(value);
        } else if (object instanceof Boolean value) {
            return NBTTagString.a(value ? "tRuE" : "fAlSe");
        } else if (object instanceof Short value) {
            return NBTTagShort.a(value);
        } else if (object instanceof Long value) {
            return NBTTagLong.a(value);
        } else if (object instanceof Byte value) {
            return NBTTagByte.a(value);
        } else if (object instanceof int[] value) {
            return new NBTTagIntArray(value);
        } else if (object instanceof byte[] value) {
            return new NBTTagByteArray(value);
        } else if (object instanceof long[] value) {
            return new NBTTagLongArray(value);
        } else if (object instanceof Map value) {
            NBTTagCompound nbtTagCompound = new NBTTagCompound();

            for (Object key : value.entrySet()) {
                if (key instanceof String strKey)
                    nbtTagCompound.a(strKey, objToNBT(value.get(strKey)));
                else
                    throw new IllegalStateException("Map key must be a String");
            }

            nbtTagCompound.a(COMPOUND_TYPE, "map");
            return nbtTagCompound;
        } else if (object instanceof DataMap value) {
            NBTTagCompound nbtTagCompound = new NBTTagCompound();

            for (String key : value.getMap().keySet()) {
                nbtTagCompound.a(key, objToNBT(value.get(key)));
            }

            return nbtTagCompound;
        } else if (object instanceof List value) {
            NBTTagList tagList = new NBTTagList();

            for (Object obj : value) {
                tagList.add(objToNBT(obj));
            }

            return tagList;
        } else if (object instanceof org.bukkit.inventory.ItemStack value) {
            NBTTagCompound itemStackCompound = new NBTTagCompound();
            CraftItemStack.asNMSCopy(value).b(itemStackCompound);

            itemStackCompound.a(COMPOUND_TYPE, "itemStack");
            return itemStackCompound;
        } else if (object instanceof Location value) {
            NBTTagCompound locationCompound = new NBTTagCompound();

            locationCompound.a("x", value.getX());
            locationCompound.a("y", value.getY());
            locationCompound.a("z", value.getZ());
            locationCompound.a("yaw", value.getYaw());
            locationCompound.a("pitch", value.getPitch());
            locationCompound.a("world", value.getWorld().getName());

            locationCompound.a(COMPOUND_TYPE, "location");
            return locationCompound;
        } else if (object instanceof BoundingBox value) {
            NBTTagCompound boundingBoxCompound = new NBTTagCompound();

            boundingBoxCompound.a("minX", value.getMinX());
            boundingBoxCompound.a("minY", value.getMinY());
            boundingBoxCompound.a("minZ", value.getMinZ());
            boundingBoxCompound.a("maxX", value.getMaxX());
            boundingBoxCompound.a("maxY", value.getMaxY());
            boundingBoxCompound.a("maxZ", value.getMaxZ());

            boundingBoxCompound.a(COMPOUND_TYPE, "boundingBox");
            return boundingBoxCompound;
        } else if (object instanceof UUID value) {
            NBTTagCompound uuidCompound = new NBTTagCompound();

            uuidCompound.a("uuid", value.toString());

            uuidCompound.a(COMPOUND_TYPE, "uuid");
            return uuidCompound;
        } else if (object instanceof RedKillerKey redKillerKey) {
            NBTTagCompound keyCompound = new NBTTagCompound();
            keyCompound.a("key", redKillerKey.key());
            keyCompound.a("value" , redKillerKey.value());
            keyCompound.a(COMPOUND_TYPE, "redKillerKey");
            return keyCompound;
        }

        throw new UnsupportedClassVersionError();
    }
}
