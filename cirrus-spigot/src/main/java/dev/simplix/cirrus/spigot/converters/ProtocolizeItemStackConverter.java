package dev.simplix.cirrus.spigot.converters;

import static de.exceptionflug.protocolize.api.util.ProtocolVersions.MINECRAFT_1_13;
import static de.exceptionflug.protocolize.api.util.ProtocolVersions.MINECRAFT_1_14;

import de.exceptionflug.protocolize.items.ItemStack;
import de.exceptionflug.protocolize.items.ItemType;
import dev.simplix.cirrus.spigot.util.ProtocolVersionUtil;
import dev.simplix.cirrus.spigot.util.ReflectionUtil;
import dev.simplix.core.common.converter.Converter;
import dev.simplix.core.common.converter.Converters;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.IntTag;
import net.querz.nbt.tag.ListTag;
import net.querz.nbt.tag.StringTag;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class ProtocolizeItemStackConverter implements Converter<ItemStack, org.bukkit.inventory.ItemStack> {

  private static Class<?> craftItemStackClass;
  private static Class<?> nbtTagCompoundClass;
  private static Class<?> itemStackNMSClass;
  private static Method nmsCopyMethod;
  private static Method bukkitCopyMethod;

  static {
    try {
      craftItemStackClass = ReflectionUtil.getClass("{obc}.inventory.CraftItemStack");
      nbtTagCompoundClass = ReflectionUtil.getClass("{nms}.NBTTagCompound");
      itemStackNMSClass = ReflectionUtil.getClass("{nms}.ItemStack");
      nmsCopyMethod = craftItemStackClass.getMethod(
          "asNMSCopy",
          org.bukkit.inventory.ItemStack.class);
      bukkitCopyMethod = craftItemStackClass.getMethod("asBukkitCopy", itemStackNMSClass);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public org.bukkit.inventory.ItemStack convert(ItemStack src) {
    if(src.getType() == ItemType.NO_DATA) {
      return null;
    }
    if(src.getType() == ItemType.AIR) {
      return new org.bukkit.inventory.ItemStack(Material.AIR);
    }
    MaterialData data = Converters.convert(src.getType(), MaterialData.class);
    org.bukkit.inventory.ItemStack out;
    if(ProtocolVersionUtil.serverProtocolVersion() < MINECRAFT_1_13) {
      out = new org.bukkit.inventory.ItemStack(
          data.getItemType(),
          src.getAmount(),
          src.getDurability(),
          data.getData());
    } else {
      out = new org.bukkit.inventory.ItemStack(
          data.getItemType(),
          src.getAmount(),
          src.getDurability());
    }

    if (src.getNBTTag() == null) {
      src.setNBTTag(new CompoundTag());
    }
    writeDataToNbt(src);
    try {
      Object nmsItemStack = nmsCopyMethod.invoke(null, out);
      Method setTag = itemStackNMSClass.getMethod("setTag", nbtTagCompoundClass);
      setTag.invoke(nmsItemStack, Converters.convert(src.getNBTTag(), nbtTagCompoundClass));
      return (org.bukkit.inventory.ItemStack) bukkitCopyMethod.invoke(null, nmsItemStack);
    } catch (final Exception e) {
      e.printStackTrace(); // Setting nbt to nms item is also pain in the ass
    }
    return out;
  }

  private void writeDataToNbt(ItemStack stack) {
    if (stack.getDisplayName() != null) {
      if (ProtocolVersionUtil.serverProtocolVersion() >= MINECRAFT_1_13) {
        ((CompoundTag) stack.getNBTTag()).put("Damage", new IntTag(stack.getDurability()));
        setDisplayNameTag(
            (CompoundTag) stack.getNBTTag(),
            ComponentSerializer.toString(stack.getDisplayNameComponents()));
      } else {
        setDisplayNameTag(
            (CompoundTag) stack.getNBTTag(),
            TextComponent.toLegacyText(stack.getDisplayNameComponents()));
      }
    }
    if (stack.getLoreComponents() != null) {
      setLoreTag(
          (CompoundTag) stack.getNBTTag(),
          stack.getLoreComponents(),
          ProtocolVersionUtil.serverProtocolVersion());
    }
  }

  private void setDisplayNameTag(CompoundTag nbtData, String name) {
    if (name == null)
      return;
    CompoundTag display = (CompoundTag) nbtData.get("display");
    if (display == null) {
      display = new CompoundTag();
    }
    final StringTag tag = new StringTag(name);
    display.put("Name", tag);
    nbtData.put("display", display);
  }

  private void setLoreTag(CompoundTag nbtData, List<BaseComponent[]> lore, int protocolVersion) {
    if (lore == null)
      return;
    CompoundTag display = (CompoundTag) nbtData.get("display");
    if (display == null) {
      display = new CompoundTag();
    }
    if (protocolVersion < MINECRAFT_1_14) {
      final ListTag<StringTag> tag = new ListTag<>(StringTag.class);
      tag.addAll(lore.stream().map(i -> new StringTag(TextComponent.toLegacyText(i))).collect(
          Collectors.toList()));
      display.put("Lore", tag);
      nbtData.put("display", display);
    } else {
      final ListTag<StringTag> tag = new ListTag<>(StringTag.class);
      tag.addAll(lore.stream().map(components -> {
        for (BaseComponent component : components) {
          if (!component.isItalic()) {
            component.setItalic(false);
          }
        }
        return new StringTag(ComponentSerializer.toString(components));
      }).collect(Collectors.toList()));
      display.put("Lore", tag);
      nbtData.put("display", display);
    }
  }

}
