package dev.simplix.cirrus.spigot.converters;

import dev.simplix.cirrus.Cirrus;
import dev.simplix.cirrus.spigot.util.ReflectionClasses;
import dev.simplix.cirrus.spigot.util.ReflectionUtil;
import dev.simplix.protocolize.api.Protocolize;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.inventory.ItemStack;
import java.util.function.Function;

@Slf4j
public class BukkitItemStackConverter implements Function<ItemStack, dev.simplix.protocolize.api.item.ItemStack> {

  private static Class<?> craftItemStackClass;
  private static Class<?> itemStackNMSClass;

  static {
    try {
      craftItemStackClass = ReflectionUtil.getClass("{obc}.inventory.CraftItemStack");
      itemStackNMSClass = ReflectionClasses.itemStackClass();
    } catch (Exception exception) {
      log.error("Could not get required classes", exception);
    }
  }

  @Override
  public dev.simplix.protocolize.api.item.ItemStack apply(@NonNull ItemStack src) {
    try {
      dev.simplix.protocolize.api.item.ItemStack out = new dev.simplix.protocolize.api.item.ItemStack(
          Cirrus.service(MaterialDataItemTypeConverter.class).apply(src.getData()),
          src.getAmount(),
          src.getDurability());
      Object handle = ReflectionUtil.fieldValue(craftItemStackClass, src, "handle");
      out.nbtData(Cirrus
          .service(NmsNbtQuerzNbtConverter.class)
          .apply(itemStackNMSClass.getMethod("getTag").invoke(handle)));
      return out;
    } catch (Exception exception) {
      throw new IllegalArgumentException("Could not convert item stack", exception);
    } catch (Throwable throwable) {
      throw new IllegalArgumentException("Fatal error converting item stack", throwable);
    }
  }

}
