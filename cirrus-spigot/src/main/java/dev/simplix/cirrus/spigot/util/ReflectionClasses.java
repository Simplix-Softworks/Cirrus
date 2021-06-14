package dev.simplix.cirrus.spigot.util;

import dev.simplix.core.common.updater.Version;
import dev.simplix.core.minecraft.spigot.util.ReflectionUtil;
import org.bukkit.Bukkit;

public class ReflectionClasses {

  private static final Version minecraftVersion = Version.parse(Bukkit.getBukkitVersion().split("-")[0] + ".1");
  private static final Version mc_1_17 = Version.parse("1.17.0");

  public static Class<?> itemStackClass() throws ClassNotFoundException {
    if (minecraftVersion.newerThen(mc_1_17)) {
      return ReflectionUtil
          .getClass("{nm}.world.item.ItemStack");

    } else {
      return ReflectionUtil
          .getClass("{nms}.ItemStack");
    }
  }

  public static Class<?> nbtCompressedStreamTools() throws ClassNotFoundException {

    if (minecraftVersion.newerThen(mc_1_17)) {
      return ReflectionUtil
          .getClass("{nm}.nbt.NBTCompressedStreamTools");

    } else {
      return ReflectionUtil
          .getClass("{nms}.NBTCompressedStreamTools");
    }
  }

  public static Class<?> nbtTagCompound() throws ClassNotFoundException {
    if (minecraftVersion.newerThen(mc_1_17)) {
      return ReflectionUtil
          .getClass("{nm}.nbt.NBTTagCompound");

    } else {
      return ReflectionUtil
          .getClass("{nms}.NBTTagCompound");
    }
  }

}
