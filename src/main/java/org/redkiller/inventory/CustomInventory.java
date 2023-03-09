package org.redkiller.inventory;

import org.bukkit.craftbukkit.v1_19_R2.inventory.CraftInventoryCustom;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;

public class CustomInventory extends CraftInventoryCustom {
    private final HashMap<Integer, Button> buttons = new HashMap<>();

    public CustomInventory(String title, int size, InventoryHolder holder) {
        super(holder, size, title);
    }

    public CustomInventory(String title, InventoryType type, InventoryHolder holder) {
        super(holder, type, title);
    }

    public CustomInventory(String title, InventoryType type) {
        this(title, type, null);
    }

    public CustomInventory(String title, int size) {
        this(title, size, null);
    }

    public void putButton(int slot, Button button) {
        buttons.put(slot, button);
    }

    public Button getButton(int slot) {
        return buttons.get(slot);
    }

    public boolean hasButton(int slot) {
        return buttons.containsKey(slot);
    }

    /**
     * 인벤토리가 닫힐때 호출하는 함수
     * 재정의 해서 사용하기 위한 함수
     * @param event InventoryCloseEvent
     */
    public void close(InventoryCloseEvent event) {}

    /**
     * 인벤토리가 열릴때 호출하는 함수
     * 재정의 해서 사용하기 위한 함수
     * @param event InventoryCloseEvent
     */
    public void open(InventoryOpenEvent event) {}

    /**
     * 인벤토리가 클릭될때 호출하는 함수
     * 재정의 해서 사용하기 위한 함수
     * 버튼 이벤트를 우선적으로 처리한다.
     * @param event InventoryCloseEvent
     */
    public void click(InventoryClickEvent event) {}

    public interface Button {
        void onClick(InventoryClickEvent event);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
