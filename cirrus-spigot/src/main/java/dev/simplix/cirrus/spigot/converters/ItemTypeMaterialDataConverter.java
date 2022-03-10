package dev.simplix.cirrus.spigot.converters;

import dev.simplix.cirrus.common.converter.Converter;
import dev.simplix.cirrus.spigot.util.ProtocolVersionUtil;
import dev.simplix.cirrus.spigot.util.XMaterial;
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
        ProtocolIdMapping mapping = this.mappingProvider.mapping(src, ProtocolVersionUtil.serverProtocolVersion());
        if (mapping == null) {
            return null;
        }
        if (ProtocolVersionUtil.serverProtocolVersion() >= ProtocolVersions.MINECRAFT_1_13) {
            return new MaterialData(Material.valueOf(src.name()));
        }
        if (mapping instanceof LegacyItemProtocolIdMapping) {
            final Material material = XMaterial
                    .matchXMaterial(mapping.id(), (byte) ((LegacyItemProtocolIdMapping) mapping).data())
                    .map(XMaterial::parseMaterial)
                    .orElse(null);
            if (material == null) {
                return null;
            }
        }
        final Material material = XMaterial.matchXMaterial(mapping.id(), (byte) 0).map(XMaterial::parseMaterial).orElse(null);
        if (material == null) {
            return null;
        }
        return material.getNewData((byte) 0);
    }

}
