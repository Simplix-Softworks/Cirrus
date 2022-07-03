package dev.simplix.cirrus.spigot.converters;

import dev.simplix.cirrus.common.business.InventoryMenuItemWrapper;
import dev.simplix.cirrus.common.business.MenuItemWrapper;
import dev.simplix.cirrus.common.converter.Converter;
import dev.simplix.cirrus.common.converter.Converters;
import dev.simplix.cirrus.common.item.CirrusItem;
import dev.simplix.protocolize.api.item.ItemStack;
import lombok.NonNull;

public class ItemModelConverter implements Converter<CirrusItem, InventoryMenuItemWrapper> {

    @Override
    public InventoryMenuItemWrapper convert(@NonNull CirrusItem model) {
        ItemStack itemStack = new ItemStack(model.itemType(), model.amount(), model.durability());
        itemStack.nbtData(model.nbt());
        itemStack.displayName(model.displayName());
        itemStack.lore(model.lore(), true);

        return InventoryMenuItemWrapper.builder()
                .handle(Converters.convert(itemStack, MenuItemWrapper.class))
                .actionArguments(model.actionArguments())
                .actionHandler(model.actionHandler())
                .build();
    }

}
