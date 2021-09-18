package dev.simplix.cirrus.spigot.converters;

import dev.simplix.cirrus.api.converter.Converter;
import dev.simplix.cirrus.api.converter.Converters;
import dev.simplix.cirrus.spigot.util.ReflectionClasses;
import dev.simplix.cirrus.spigot.util.ReflectionUtil;
import dev.simplix.protocolize.data.ItemType;
import lombok.NonNull;
import net.querz.nbt.tag.CompoundTag;
import org.bukkit.inventory.ItemStack;

public class BukkitItemStackConverter implements Converter<ItemStack, dev.simplix.protocolize.api.item.ItemStack> {

  private static Class<?> craftItemStackClass;
  private static Class<?> itemStackNMSClass;

  static {
    try {
      craftItemStackClass = ReflectionUtil.getClass("{obc}.inventory.CraftItemStack");
      itemStackNMSClass = ReflectionClasses.itemStackClass();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  @Override
  public dev.simplix.protocolize.api.item.ItemStack convert(@NonNull ItemStack src) {
    try {
      dev.simplix.protocolize.api.item.ItemStack out = new dev.simplix.protocolize.api.item.ItemStack(
          Converters.convert(src.getData(), ItemType.class),
          src.getAmount(),
          src.getDurability());
      Object handle = ReflectionUtil.fieldValue(craftItemStackClass, src, "handle");
      out.nbtData(Converters.convert(
          itemStackNMSClass.getMethod("getTag").invoke(handle),
          CompoundTag.class));
      return out;
    } catch (Throwable throwable) {
      throwable.printStackTrace();
      return null;
    }
  }

}
