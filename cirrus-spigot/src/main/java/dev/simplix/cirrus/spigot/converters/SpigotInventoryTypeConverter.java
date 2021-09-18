package dev.simplix.cirrus.spigot.converters;

import dev.simplix.cirrus.api.converter.Converter;
import dev.simplix.protocolize.data.inventory.InventoryType;
import lombok.NonNull;

public class SpigotInventoryTypeConverter implements Converter<InventoryType, org.bukkit.event.inventory.InventoryType> {

  @Override
  public org.bukkit.event.inventory.InventoryType convert(@NonNull InventoryType src) {
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
