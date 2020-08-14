package dev.simplix.cirrus.spigot;

import de.exceptionflug.protocolize.items.ItemStack;
import de.exceptionflug.protocolize.items.ItemType;
import dev.simplix.cirrus.api.business.InventoryItemWrapper;
import dev.simplix.cirrus.api.business.ItemStackWrapper;
import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.i18n.LocalizedItemStackModel;
import dev.simplix.cirrus.common.item.ProtocolizeItemStackWrapper;
import dev.simplix.cirrus.spigot.converters.*;
import dev.simplix.cirrus.spigot.util.ReflectionUtil;
import dev.simplix.core.common.aop.AbstractSimplixModule;
import dev.simplix.core.common.aop.InjectorModule;
import dev.simplix.core.common.converter.Converters;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import net.querz.nbt.tag.CompoundTag;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

@InjectorModule("Cirrus")
@Slf4j
public class SpigotCirrusModule extends AbstractSimplixModule {

  static {
    try {
      // Players
      Converters.register(
          Player.class,
          PlayerWrapper.class,
          o -> new SpigotPlayerWrapper((Player) o));
      Converters.register(UUID.class, PlayerWrapper.class, o -> Bukkit.getPlayer((UUID) o));

      // Items
      Converters.register( // Protocolize ----> Bukkit
          ItemStack.class,
          org.bukkit.inventory.ItemStack.class,
          new ProtocolizeItemStackConverter());
      Converters.register( // Bukkit ----> Protocolize
          org.bukkit.inventory.ItemStack.class,
          ItemStack.class,
          new BukkitItemStackConverter());
      Converters.register(
          ItemStack.class,
          ItemStackWrapper.class,
          o -> new ProtocolizeItemStackWrapper(
              (de.exceptionflug.protocolize.items.ItemStack) o));
      Converters.register(ItemType.class, MaterialData.class, new ItemTypeMaterialDataConverter());
      Converters.register(MaterialData.class, ItemType.class, new MaterialDataItemTypeConverter());
      Converters.register(
          LocalizedItemStackModel.class,
          InventoryItemWrapper.class,
          new ItemModelConverter());

      // NBT
      Converters.register( // Querz ----> NMS
          CompoundTag.class,
          ReflectionUtil.getClass("{nms}.NBTTagCompound"),
          new QuerzNbtNmsNbtConverter());
      Converters.register( // NMS ----> Querz
          ReflectionUtil.getClass("{nms}.NBTTagCompound"),
          CompoundTag.class,
          new NmsNbtQuerzNbtConverter());
    } catch (Exception e) {
      log.error("Cannot register cirrus converters", e);
    }
  }

}
