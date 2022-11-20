package dev.simplix.cirrus.spigot.services.converters;

import dev.simplix.protocolize.data.inventory.InventoryType;
import lombok.NonNull;

import java.util.function.Function;

public class SpigotInventoryTypeConverter implements Function<InventoryType, org.bukkit.event.inventory.InventoryType> {

  @Override
  public org.bukkit.event.inventory.InventoryType apply(@NonNull InventoryType src) {
    switch (src) {

      case ANVIL:
        return org.bukkit.event.inventory.InventoryType.ANVIL;

      case BEACON:
        return org.bukkit.event.inventory.InventoryType.BEACON;

      case BREWING_STAND:
        return org.bukkit.event.inventory.InventoryType.BREWING;

      case CRAFTING:
        return org.bukkit.event.inventory.InventoryType.WORKBENCH;

      case GENERIC_3X3:
        return org.bukkit.event.inventory.InventoryType.DISPENSER;

      case ENCHANTMENT:
        return org.bukkit.event.inventory.InventoryType.ENCHANTING;

      case FURNACE:
        return org.bukkit.event.inventory.InventoryType.FURNACE;

      case HOPPER:
        return org.bukkit.event.inventory.InventoryType.HOPPER;

      case MERCHANT:
        return org.bukkit.event.inventory.InventoryType.MERCHANT;

    }
    if (src.name().startsWith("GENERIC")) {
      return org.bukkit.event.inventory.InventoryType.CHEST;
    }
    return null;
  }
}
