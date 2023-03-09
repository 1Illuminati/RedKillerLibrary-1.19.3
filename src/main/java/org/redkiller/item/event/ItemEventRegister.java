package org.redkiller.item.event;

import org.bukkit.NamespacedKey;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.redkiller.RedKillerLibrary;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ItemEventRegister {
    private static final HashMap<String, ItemEventInfo> map = new HashMap<>();
    private static final NamespacedKey key = new NamespacedKey(RedKillerLibrary.getPlugin(), "RedKillerLibrary_ItemEvent");

    public static void registerItemEvent(ItemEvent itemEvent) {
        String code = itemEvent.getCode();
        map.put(code, new ItemEventInfo(itemEvent));
        RedKillerLibrary.sendDebugLog("ItemEvent Register : " + code);
    }

    public static void setItemInEvent(ItemStack itemStack, String code) {
        if (itemStack == null) {
            throw new NullPointerException("ItemStack is Null");
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        persistentDataContainer.set(key, PersistentDataType.STRING, code);
        itemStack.setItemMeta(itemMeta);
    }

    public static boolean runItemEvent(ItemStack itemStack, ItemEventAnnotation.Act act, Event event) {
        if (itemStack == null) {
            return false;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) {
            return false;
        }

        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();

        if (!persistentDataContainer.has(key, PersistentDataType.STRING))
            return false;

        String code = persistentDataContainer.get(key, PersistentDataType.STRING);

        if (!map.containsKey(code))
            return false;

        ItemEventInfo info = map.get(code);
        return info.runEvent(act, event);
    }

    private static class ItemEventInfo {
        private final ItemEvent itemEvent;

        private final HashMap<ItemEventAnnotation.Act, Method> map = new HashMap<>();

        public ItemEventInfo(ItemEvent itemEvent) {
            this.itemEvent = itemEvent;
            Method[] methods = itemEvent.getClass().getMethods();

            for (Method method : methods) {
                Annotation[] annotations = method.getAnnotations();

                for (Annotation annotation : annotations) {
                    if (annotation.annotationType().isAssignableFrom(ItemEventAnnotation.class)) {
                        ItemEventAnnotation eventAnnotation = (ItemEventAnnotation) annotation;
                        ItemEventAnnotation.Act act = eventAnnotation.act();
                        Class<?>[] classes = method.getParameterTypes();

                        if (classes.length == 1) {
                            Class<?> clazz = classes[0];
                            if ((clazz.isAssignableFrom(PlayerSwapHandItemsEvent.class) && (act == ItemEventAnnotation.Act.SWAP_HAND || act == ItemEventAnnotation.Act.SHIFT_SWAP_HAND)) ||
                                    (clazz.isAssignableFrom(PlayerDropItemEvent.class) && (act == ItemEventAnnotation.Act.DROP || act == ItemEventAnnotation.Act.SHIFT_DROP)) ||
                                    (clazz.isAssignableFrom(EntityDamageByEntityEvent.class) && act == ItemEventAnnotation.Act.HIT) ||
                                    (clazz.isAssignableFrom(BlockBreakEvent.class) && act == ItemEventAnnotation.Act.BREAK) ||
                                    (clazz.isAssignableFrom(PlayerFishEvent.class) && act == ItemEventAnnotation.Act.FISHING) ||
                                    (clazz.isAssignableFrom(PlayerInteractEvent.class))) {
                                map.put(act, method);
                            }
                        }
                    }
                }
            }
        }


        public boolean runEvent(ItemEventAnnotation.Act act, Event event) {
            if (!map.containsKey(act))
                return false;

            try {
                map.get(act).invoke(this.getItemEvent(), event);
                RedKillerLibrary.sendDebugLog("ItemEvent : " + act.name());
                return true;
            } catch(Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public ItemEvent getItemEvent() {
            return this.itemEvent;
        }
    }
}
