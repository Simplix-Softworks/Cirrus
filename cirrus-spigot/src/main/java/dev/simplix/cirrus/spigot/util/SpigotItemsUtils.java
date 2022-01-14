package dev.simplix.cirrus.spigot.util;

import dev.simplix.cirrus.common.business.MenuItemWrapper;
import dev.simplix.cirrus.common.converter.Converters;
import dev.simplix.cirrus.common.item.ProtocolizeMenuItemWrapper;
import dev.simplix.protocolize.data.ItemType;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

@UtilityClass
public class SpigotItemsUtils {

    public ItemType typeFromMaterial(@NonNull MaterialData material) {
        return Converters.convert(material, ItemType.class);
    }

    public ItemType typeFromMaterial(@NonNull Material material) {
        return ItemType.valueOf(material.name());
    }

    public MenuItemWrapper wrapBukkitItemStack(@NonNull ItemStack itemStack) {
        dev.simplix.protocolize.api.item.ItemStack protocolizeStack = Converters.convert(itemStack, dev.simplix.protocolize.api.item.ItemStack.class);
        return new ProtocolizeMenuItemWrapper(protocolizeStack);
    }
}
