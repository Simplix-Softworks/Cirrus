package dev.simplix.cirrus.spigot.converters;

import de.exceptionflug.protocolize.items.ItemType;
import dev.simplix.cirrus.spigot.util.ReflectionClasses;
import dev.simplix.core.common.converter.Converter;
import dev.simplix.core.common.converter.Converters;
import dev.simplix.core.minecraft.spigot.util.ReflectionUtil;
import lombok.NonNull;
import net.querz.nbt.tag.CompoundTag;
import org.bukkit.inventory.ItemStack;

public class BukkitItemStackConverter implements Converter<ItemStack, de.exceptionflug.protocolize.items.ItemStack> {

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
  public de.exceptionflug.protocolize.items.ItemStack convert(@NonNull ItemStack src) {
    try {
      de.exceptionflug.protocolize.items.ItemStack out = new de.exceptionflug.protocolize.items.ItemStack(
          Converters.convert(src.getData(), ItemType.class),
          src.getAmount(),
          src.getDurability());
      Object handle = ReflectionUtil.fieldValue(craftItemStackClass, src, "handle");
      out.setNBTTag(Converters.convert(
          itemStackNMSClass.getMethod("getTag").invoke(handle),
          CompoundTag.class));
      return out;
    } catch (Throwable throwable) {
      throwable.printStackTrace();
      return null;
    }
  }

}
