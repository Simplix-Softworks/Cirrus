package dev.simplix.cirrus.spigot.converters;

import dev.simplix.cirrus.item.CirrusItem;
import dev.simplix.cirrus.spigot.util.ProtocolVersionUtil;
import dev.simplix.protocolize.api.util.ProtocolVersions;
import dev.simplix.protocolize.data.ItemType;

import java.util.function.Function;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Material;

@Slf4j
public class ItemTypeMaterialConverter implements Function<ItemType, Material> {

  @Override
  public Material apply(@NonNull ItemType src) {
    if (ProtocolVersionUtil.serverProtocolVersion() >= ProtocolVersions.MINECRAFT_1_13) {
      return Material.valueOf(src.name());
    }

    CirrusItem.of(ItemType.ITEM_FRAME, "Test", "test321");
    throw new IllegalArgumentException("Unsupported type "
                                       + src.name()
                                       + " on protocol version: "
                                       + ProtocolVersionUtil.serverProtocolVersion());
  }

}
