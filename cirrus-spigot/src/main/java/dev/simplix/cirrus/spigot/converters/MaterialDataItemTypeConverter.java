package dev.simplix.cirrus.spigot.converters;

import de.exceptionflug.protocolize.api.util.ProtocolVersions;
import de.exceptionflug.protocolize.items.ItemType;
import dev.simplix.cirrus.spigot.util.ProtocolVersionUtil;
import dev.simplix.core.common.converter.Converter;
import lombok.NonNull;
import org.bukkit.material.MaterialData;

public class MaterialDataItemTypeConverter implements Converter<MaterialData, ItemType> {

  @Override
  public ItemType convert(@NonNull MaterialData src) {
    if (ProtocolVersionUtil.serverProtocolVersion() >= ProtocolVersions.MINECRAFT_1_14) {
      return ItemType.valueOf(src.getItemType().name());
    }
    return ItemType.getType(
        src.getItemTypeId(),
        src.getData(),
        ProtocolVersionUtil.serverProtocolVersion(),
        null); // This will throw NPE when converting SpawnEggs
  }

}
