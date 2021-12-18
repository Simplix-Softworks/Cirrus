package dev.simplix.cirrus.spigot.converters;

import com.google.common.collect.Multimap;
import dev.simplix.cirrus.common.converter.Converter;
import dev.simplix.cirrus.spigot.util.ProtocolVersionUtil;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.mapping.ProtocolIdMapping;
import dev.simplix.protocolize.api.providers.MappingProvider;
import dev.simplix.protocolize.api.util.ProtocolVersions;
import dev.simplix.protocolize.data.ItemType;
import dev.simplix.protocolize.data.mapping.LegacyItemProtocolIdMapping;
import lombok.NonNull;
import org.bukkit.material.MaterialData;

public class MaterialDataItemTypeConverter implements Converter<MaterialData, ItemType> {

    private final MappingProvider mappingProvider = Protocolize.mappingProvider();

    @Override
    public ItemType convert(@NonNull MaterialData src) {
        if (ProtocolVersionUtil.serverProtocolVersion() >= ProtocolVersions.MINECRAFT_1_14) {
            return ItemType.valueOf(src.getItemType().name());
        }
        Multimap<ItemType, ProtocolIdMapping> mappings = this.mappingProvider.mappings(ItemType.class, ProtocolVersionUtil.serverProtocolVersion());
        for (ItemType type : mappings.keySet()) {
            for (ProtocolIdMapping mapping : mappings.get(type)) {
                if (mapping instanceof LegacyItemProtocolIdMapping) {
                    if (mapping.id()==src.getItemTypeId() && ((LegacyItemProtocolIdMapping) mapping).data()==src.getData()) {
                        return type;
                    }
                }
            }
        }
        return null;
    }

}
