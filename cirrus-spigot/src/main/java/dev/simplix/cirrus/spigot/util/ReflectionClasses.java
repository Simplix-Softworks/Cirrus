package dev.simplix.cirrus.spigot.util;

import dev.simplix.core.minecraft.spigot.util.ReflectionUtil;

public class ReflectionClasses {

  public static Class<?> itemStackClass() throws ClassNotFoundException {
    if (ProtocolVersionUtil.serverProtocolVersion() >= ProtocolVersions.MINECRAFT_1_17) {
      return ReflectionUtil
          .getClass("{nm}.world.item.ItemStack");

    } else {
      return ReflectionUtil
          .getClass("{nms}.ItemStack");
    }
  }

  public static Class<?> nbtCompressedStreamTools() throws ClassNotFoundException {

    if (ProtocolVersionUtil.serverProtocolVersion() >= ProtocolVersions.MINECRAFT_1_17) {
      return ReflectionUtil
          .getClass("{nm}.nbt.NBTCompressedStreamTools");

    } else {
      return ReflectionUtil
          .getClass("{nms}.NBTCompressedStreamTools");
    }
  }

  public static Class<?> nbtTagCompound() throws ClassNotFoundException {
    if (ProtocolVersionUtil.serverProtocolVersion() >= ProtocolVersions.MINECRAFT_1_17) {
      return ReflectionUtil
          .getClass("{nm}.nbt.NBTTagCompound");

    } else {
      return ReflectionUtil
          .getClass("{nms}.NBTTagCompound");
    }
  }

}
