package dev.simplix.cirrus.spigot.services;

import dev.simplix.cirrus.Cirrus;
import dev.simplix.cirrus.service.ItemService;
import dev.simplix.protocolize.data.ItemType;
import java.util.Arrays;
import java.util.Set;
import org.bukkit.Material;

public class SpigotItemService extends ItemService {

  private final Set<String> materialNames = Arrays
      .stream(Material.values())
      .map(Material::name)
      .collect(java.util.stream.Collectors.toSet());

  @Override
  public boolean isItemAvailable(ItemType itemType, int protocolVersion) {
    final Material material = material(itemType.name());
    return material != null && material.isItem() && !material.isAir();
  }

  private Material material(String name) {
    if (!materialNames.contains(name)) {
      return null;
    }
    try {
      return Material.valueOf(name);
    } catch (IllegalArgumentException exception) {
      return null;
    }
  }

}
