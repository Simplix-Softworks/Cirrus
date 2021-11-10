package dev.simplix.cirrus.spigot.menus;

import dev.simplix.cirrus.common.business.ItemStackWrapper;
import dev.simplix.cirrus.common.menu.Menu;
import dev.simplix.cirrus.spigot.util.SpigotItemsUtils;
import dev.simplix.protocolize.data.ItemType;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public interface SpigotMenu extends Menu {

    default ItemType typeFromMaterial(@NonNull MaterialData material) {
        return SpigotItemsUtils.typeFromMaterial(material);
    }

    default ItemType typeFromMaterial(@NonNull Material material) {
        return SpigotItemsUtils.typeFromMaterial(material);
    }

    default ItemStackWrapper wrapBukkitItemStack(@NonNull ItemStack itemStack) {
        return SpigotItemsUtils.wrapBukkitItemStack(itemStack);
    }

}
