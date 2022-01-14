package dev.simplix.cirrus.common.container.impl;

import dev.simplix.cirrus.common.business.InventoryMenuItemWrapper;
import dev.simplix.cirrus.common.container.Container;
import dev.simplix.cirrus.common.util.InventoryContentMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.NonNull;

public class ItemContainer implements Container {

    private final InventoryContentMap inventoryContentMap = new InventoryContentMap();
    private final Set<Integer> reservedSlots = new HashSet<>();
    private final int baseSlotIndex;
    private final int capacity;

    public ItemContainer(int baseSlotIndex, int capacity) {
        this.baseSlotIndex = baseSlotIndex;
        this.capacity = capacity;
    }

    @Override
    public void set(int slot, @NonNull InventoryMenuItemWrapper inventoryItemWrapper) {
        itemMap().put(slot + this.baseSlotIndex, inventoryItemWrapper);
    }

    @Override
    public Map<Integer, InventoryMenuItemWrapper> itemMap() {
        return this.inventoryContentMap;
    }

    @Override
    public Set<Integer> reservedSlots() {
        return this.reservedSlots;
    }

    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public int baseSlot() {
        return this.baseSlotIndex;
    }

    @Override
    public int nextFreeSlot() {
        for (int i = this.baseSlotIndex; i < this.capacity + this.baseSlotIndex; i++) {
            if (!itemMap().containsKey(i) && !reservedSlots().contains(i)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int nextFreeSlot(int base) {
        for (int i = base; i < this.capacity + this.baseSlotIndex; i++) {
            if (!itemMap().containsKey(i) && !reservedSlots().contains(i)) {
                return i;
            }
        }
        return -1;
    }

}
