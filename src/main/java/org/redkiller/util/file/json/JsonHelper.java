package org.redkiller.util.file.json;

import com.google.gson.Gson;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonHelper {
    private static final String parse = "%§§§%";
    private static final String invParse = "§§%§§";

    public String mapToJson(Map map) {
        HashMap<String, String> hashMap = new HashMap<>();

        for (Object key : map.keySet()) {
            Object value = map.get(key);
            hashMap.put(objToStr(key), objToStr(value));
        }

        return new Gson().toJson(hashMap);
    }

    public HashMap jsonToMap(String jsonStr) {
        HashMap result = new HashMap();
        HashMap<String, String> strMap = new Gson().fromJson(jsonStr, HashMap.class);

        for (String key : strMap.keySet()) {
            result.put(this.strToObj(key), this.strToObj(strMap.get(key)));
        }

        return result;
    }

    private String objToStr(Object obj) {
        Class<?> clazz = obj.getClass();
        if (checkClass(clazz)) {
            return clazz.getName() + parse + obj;
        } else {
            return clazz.getName() + parse + this.bukkitObjOut(obj);
        }
    }

    private Object strToObj(String str) {
        String[] split = str.split(parse);
        Class<?> clazz = null;

        try {
            clazz = Class.forName(split[0]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (String.class.equals(clazz)) {
            return split[1];
        } else if (Integer.class.equals(clazz)) {
            return Integer.parseInt(split[1]);
        } else if (Long.class.equals(clazz)) {
            return Long.parseLong(split[1]);
        } else if (Double.class.equals(clazz)) {
            return Double.parseDouble(split[1]);
        } else if (Float.class.equals(clazz)) {
            return Float.parseFloat(split[1]);
        } else if (Boolean.class.equals(clazz)) {
            return Boolean.parseBoolean(split[1]);
        } else if (Byte.class.equals(clazz)) {
            return Byte.parseByte(split[1]);
        } else if (Short.class.equals(clazz)) {
            return Short.parseShort(split[1]);
        } else if (Character.class.equals(clazz)) {
            return split[1].charAt(0);
        } else {
            return bukkitObjIn(split[1], clazz);
        }
    }

    public <T> T bukkitObjIn(String byteBukkitStr, Class<T> clazz) {
        T result = null;
        if(clazz == Inventory.class) {
            result = (T) this.stringToInventory(byteBukkitStr);
        } else {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(byteBukkitStr));
            try {
                BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
                result = (T) dataInput.readObject();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public <T> String bukkitObjOut(Object bukkitClass) {
        if(bukkitClass.getClass() == Inventory.class) {
            return inventoryToString((Inventory) bukkitClass);
        }

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(bukkitClass);
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String inventoryToString(Inventory inventory) {
        StringBuilder result = new StringBuilder(inventory.getType().name()).append(invParse).append(inventory.getSize());
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack is = inventory.getItem(i);
            String itemStackStr = bukkitObjOut(is);
            result.append(invParse).append(itemStackStr);
        }

        return result.toString();
    }

    public Inventory stringToInventory (String invString) {
        String[] serializedBlocks = invString.split(invParse);
        InventoryType invType = InventoryType.valueOf(serializedBlocks[0]);
        int invSize = Integer.parseInt(serializedBlocks[1]);
        Inventory deserializedInventory;

        if (invType == InventoryType.CHEST)  {
            deserializedInventory = Bukkit.createInventory(null, invSize);
        } else {
            deserializedInventory = Bukkit.getServer().createInventory(null, invType);
        }

        for (int i = 2; i < serializedBlocks.length; i++) {
            deserializedInventory.setItem(i - 2, bukkitObjIn(invString, ItemStack.class));
        }

        return deserializedInventory;
    }

    private boolean checkClass(Class<?> clazz) {
        return clazz == String.class || clazz == Integer.class || clazz == Double.class || clazz == Float.class || clazz == Boolean.class || clazz == Long.class
                || clazz == Character.class || clazz == Byte.class || clazz == Short.class;
    }
}
