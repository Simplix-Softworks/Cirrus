package dev.simplix.cirrus.common.menu;

import dev.simplix.cirrus.api.business.InventoryItemWrapper;
import dev.simplix.cirrus.api.menu.Container;
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
  public void set(int slot, @NonNull InventoryItemWrapper inventoryItemWrapper) {
    itemMap().put(slot + baseSlotIndex, inventoryItemWrapper);
  }

  @Override
  public Map<Integer, InventoryItemWrapper> itemMap() {
    return inventoryContentMap;
  }

  @Override
  public Set<Integer> reservedSlots() {
    return reservedSlots;
  }

  @Override
  public int capacity() {
    return capacity;
  }

    @Override
    public int baseSlot() {
        return baseSlotIndex;
    }

    @Override
  public int nextFreeSlot() {
    for (int i = baseSlotIndex; i < capacity + baseSlotIndex; i++) {
      if (!itemMap().containsKey(i) && !reservedSlots().contains(i)) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public int nextFreeSlot(int base) {
    for (int i = base; i < capacity + baseSlotIndex; i++) {
      if (!itemMap().containsKey(i) && !reservedSlots().contains(i)) {
        return i;
      }
    }
    return -1;
  }

}
