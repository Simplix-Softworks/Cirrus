package dev.simplix.cirrus.spigot.converters;

import dev.simplix.cirrus.api.converter.Converter;
import dev.simplix.cirrus.spigot.util.ProtocolVersionUtil;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.mapping.ProtocolIdMapping;
import dev.simplix.protocolize.api.providers.MappingProvider;
import dev.simplix.protocolize.api.util.ProtocolVersions;
import dev.simplix.protocolize.data.ItemType;
import dev.simplix.protocolize.data.mapping.LegacyItemProtocolIdMapping;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class ItemTypeMaterialDataConverter implements Converter<ItemType, MaterialData> {

    private final MappingProvider mappingProvider = Protocolize.mappingProvider();

    @Override
    public MaterialData convert(@NonNull ItemType src) {
        ProtocolIdMapping mapping = mappingProvider.mapping(src, ProtocolVersionUtil.serverProtocolVersion());
        if (mapping == null) {
            return null;
        }
        if (ProtocolVersionUtil.serverProtocolVersion() >= ProtocolVersions.MINECRAFT_1_13) {
            return new MaterialData(Material.valueOf(src.name()));
        }
        if (mapping instanceof LegacyItemProtocolIdMapping) {
            return new MaterialData(mapping.id(), (byte) ((LegacyItemProtocolIdMapping) mapping).data());
        }
        return new MaterialData(mapping.id(), (byte) 0);
    }

}
