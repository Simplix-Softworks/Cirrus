package dev.simplix.cirrus.spigot.converters;

import de.exceptionflug.protocolize.api.util.ProtocolVersions;
import de.exceptionflug.protocolize.items.ItemIDMapping;
import de.exceptionflug.protocolize.items.ItemType;
import dev.simplix.cirrus.spigot.util.ProtocolVersionUtil;
import dev.simplix.core.common.converter.Converter;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class ItemTypeMaterialDataConverter implements Converter<ItemType, MaterialData> {

    @Override
    public MaterialData convert(ItemType src) {
        if(src == null)
            return null;
        ItemIDMapping applicableMapping = src.getApplicableMapping(ProtocolVersionUtil.protocolVersion());
        if(applicableMapping == null) {
            return null;
        }
        if(ProtocolVersionUtil.protocolVersion() >= ProtocolVersions.MINECRAFT_1_13) {
            return new MaterialData(Material.valueOf(src.name()));
        }
        return new MaterialData(applicableMapping.getId(), (byte) applicableMapping.getData());
    }

}
