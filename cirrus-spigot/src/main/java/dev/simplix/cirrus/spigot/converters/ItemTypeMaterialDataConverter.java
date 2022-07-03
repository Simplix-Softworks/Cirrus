package dev.simplix.cirrus.spigot.converters;

import dev.simplix.cirrus.common.converter.Converter;
import dev.simplix.cirrus.spigot.util.ProtocolVersionUtil;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.mapping.ProtocolIdMapping;
import dev.simplix.protocolize.api.providers.MappingProvider;
import dev.simplix.protocolize.api.util.ProtocolVersions;
import dev.simplix.protocolize.data.ItemType;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class ItemTypeMaterialDataConverter implements Converter<ItemType, MaterialData> {

  private final MappingProvider mappingProvider = Protocolize.mappingProvider();

  @Override
  public MaterialData convert(@NonNull ItemType src) {
    ProtocolIdMapping mapping = this.mappingProvider.mapping(src, ProtocolVersionUtil.serverProtocolVersion());
    if (mapping==null) {
      return null;
    }
    if (ProtocolVersionUtil.serverProtocolVersion() >= ProtocolVersions.MINECRAFT_1_13) {
      return new MaterialData(Material.valueOf(src.name()));
    }

    return null;
  }

}
