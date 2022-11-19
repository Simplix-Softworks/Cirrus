package dev.simplix.cirrus.service;

import dev.simplix.protocolize.data.inventory.InventoryType;

public interface CapacityService {
  int capacity(InventoryType inventoryType);
}
