package dev.simplix.cirrus.spigot.converters;

import de.exceptionflug.protocolize.api.util.ProtocolVersions;
import de.exceptionflug.protocolize.items.ItemIDMapping;
import de.exceptionflug.protocolize.items.ItemType;
import dev.simplix.cirrus.spigot.util.ProtocolVersionUtil;
import dev.simplix.core.common.converter.Converter;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class ItemTypeMaterialDataConverter implements Converter<ItemType, MaterialData> {

    @Override
    public MaterialData convert(@NonNull ItemType src) {
        ItemIDMapping applicableMapping = src.getApplicableMapping(ProtocolVersionUtil.serverProtocolVersion());
        if(applicableMapping == null) {
            return null;
        }
        if(ProtocolVersionUtil.serverProtocolVersion() >= ProtocolVersions.MINECRAFT_1_13) {
            return new MaterialData(Material.valueOf(src.name()));
        }
        return new MaterialData(applicableMapping.getId(), (byte) applicableMapping.getData());
    }

}
