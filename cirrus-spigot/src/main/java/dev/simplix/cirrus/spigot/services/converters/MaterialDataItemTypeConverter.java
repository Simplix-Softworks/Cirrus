package dev.simplix.cirrus.spigot.services.converters;

import dev.simplix.cirrus.spigot.util.ProtocolVersionUtil;
import dev.simplix.protocolize.api.util.ProtocolVersions;
import dev.simplix.protocolize.data.ItemType;
import lombok.NonNull;
import org.bukkit.material.MaterialData;

import java.util.function.Function;

public class MaterialDataItemTypeConverter implements Function<MaterialData, ItemType> {

  @Override
  public ItemType apply(@NonNull MaterialData src) {
    // Modern versioning
    if (ProtocolVersionUtil.serverProtocolVersion() >= ProtocolVersions.MINECRAFT_1_14) {
      return ItemType.valueOf(src.getItemType().name());
    }

    throw new IllegalStateException("Version not supported");
  }

}
