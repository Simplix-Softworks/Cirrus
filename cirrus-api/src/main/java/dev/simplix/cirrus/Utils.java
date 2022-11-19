package dev.simplix.cirrus;

import dev.simplix.protocolize.data.inventory.InventoryType;
import java.util.*;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.querz.nbt.tag.*;

@UtilityClass
public class Utils {
  private static final String STEVE_TEXTURE = "ewogICJ0aW1lc3RhbXAiIDogMTU5MTU3NDcyMzc4MywKICAicHJvZmlsZUlkIiA6ICI4NjY3YmE3MWI4NWE0MDA0YWY1NDQ1N2E5NzM0ZWVkNyIsCiAgInByb2ZpbGVOYW1lIiA6ICJTdGV2ZSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82ZDNiMDZjMzg1MDRmZmMwMjI5Yjk0OTIxNDdjNjlmY2Y1OWZkMmVkNzg4NWY3ODUwMjE1MmY3N2I0ZDUwZGUxIgogICAgfSwKICAgICJDQVBFIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS85NTNjYWM4Yjc3OWZlNDEzODNlNjc1ZWUyYjg2MDcxYTcxNjU4ZjIxODBmNTZmYmNlOGFhMzE1ZWE3MGUyZWQ2IgogICAgfQogIH0KfQ==";

//  public void removeItalic(BaseComponent[] components) {
//    for (BaseComponent component : components) {
//      component.setItalic(false);
//    }
//  }


  public Optional<UUID> fromString(final String uuidString) {
    if (uuidString==null || uuidString.isEmpty()) {
      return Optional.empty();
    }

    final String[] components = uuidString.split("-");
    if (components.length!=5) {
      return Optional.empty();
    }

    return Optional.of(UUID.fromString(uuidString));
  }

  public InventoryType calculateTypeForContent(final int size) {


    if (size <= 9) {
      return InventoryType.GENERIC_9X1;
    }

    if (size <= 9*2) {
      return InventoryType.GENERIC_9X2;
    }

    if (size <= 9*3) {
      return InventoryType.GENERIC_9X3;
    }

    if (size <= 9*4) {
      return InventoryType.GENERIC_9X4;
    }

    if (size <= 9*5) {
      return InventoryType.GENERIC_9X5;
    }

    return InventoryType.GENERIC_9X6;
  }

  public int calculateSizeForContent(final int size) {
    if (size <= 9) {
      return 9;
    }

    if (size <= 9*2) {
      return 9*2;
    }

    if (size <= 9*3) {
      return 9*3;
    }

    if (size <= 9*4) {
      return 9*4;
    }

    if (size <= 9*5) {
      return 9*5;
    }

    return 9*6;
  }

  //public InventoryType
  public InventoryType typeOfSize(final int size) {
    return switch (size) {
      case 9 -> InventoryType.GENERIC_9X1;
      case 18 -> InventoryType.GENERIC_9X2;
      case 27 -> InventoryType.GENERIC_9X3;
      case 36 -> InventoryType.GENERIC_9X4;
      case 45 -> InventoryType.GENERIC_9X5;
      case 54 -> InventoryType.GENERIC_9X6;
      default -> throw new IllegalArgumentException("Invalid size: " + size);
    };
  }

  //Returns the usable size of an InventoryType
  //Usable means without the lowest row of the GUI where we put our back/info items
  public int sizeOfType(@NonNull final InventoryType type) {
    return switch (type) {
      case GENERIC_9X1 -> 9;
      case GENERIC_9X2 -> 18;
      case GENERIC_9X3 -> 27;
      case GENERIC_9X4 -> 36;
      case GENERIC_9X5 -> 45;
      case GENERIC_9X6 -> 54;
      default -> 0;
    };
  }

  public Optional<Long> toLong(final String string) {
    try {
      return Optional.of(Long.parseLong(string));
    } catch (final NumberFormatException ex) {
      return Optional.empty();
    }
  }

  public Optional<Integer> toIntOrNull(final String string) {
    try {
      return Optional.of(Integer.parseInt(string));
    } catch (final NumberFormatException ex) {
      return Optional.empty();
    }
  }

  public static void hideNbtFlags(CompoundTag tag) {
    tag.put("HideFlags", new IntTag(127));
  }

  public static List<String> colorize(final List<String> toColorize) {
    final List<String> out = new ArrayList<>();

    for (final String lore : toColorize) {
      out.add(lore.replace("&", "§"));
    }
    return out;
  }

  public static String colorize(final String name) {
    return name.replace("&", "§");
  }

  public static void glow(CompoundTag tag) {
    hideNbtFlags(tag);

    final ListTag<CompoundTag> enchantments = new ListTag<>(CompoundTag.class);
    final ListTag<CompoundTag> enchs = new ListTag<>(CompoundTag.class);

    final CompoundTag exampleEnchantment = new CompoundTag();

    exampleEnchantment.put("id", new StringTag("minecraft:efficiency"));
    exampleEnchantment.put("lvl", new ShortTag((short) 1));

    final CompoundTag exampleEnch = new CompoundTag();
    exampleEnch.put("id", new ShortTag((short) 1));
    exampleEnch.put("lvl", new ShortTag((short) 1));

    enchantments.add(exampleEnchantment);
    enchs.add(exampleEnch);

    tag.put("ench", enchs);
    tag.put("Enchantments", enchantments);
  }

  public static void texture(CompoundTag tag, String textureHash) {
    if (!(tag.get("SkullOwner") instanceof CompoundTag)) {
      tag.put("SkullOwner", new CompoundTag());
    }

    CompoundTag skullOwner = tag.getCompoundTag("SkullOwner");

    if (skullOwner==null) {
      skullOwner = new CompoundTag();
    }

    skullOwner.put("Name", new StringTag(textureHash));
    CompoundTag properties = skullOwner.getCompoundTag("Properties");
    if (properties==null) {
      properties = new CompoundTag();
    }

    CompoundTag texture = new CompoundTag();
    texture.put(
            "Value",
            new StringTag(textureHash.isEmpty() ? STEVE_TEXTURE:textureHash));
    ListTag<CompoundTag> textures = new ListTag<>(CompoundTag.class);
    textures.add(texture);
    properties.put("textures", textures);
    skullOwner.put("Properties", properties);
    tag.put("SkullOwner", skullOwner);

  }
}
