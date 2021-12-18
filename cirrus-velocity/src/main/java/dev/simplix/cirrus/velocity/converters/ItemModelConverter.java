package dev.simplix.cirrus.velocity.converters;

import dev.simplix.cirrus.common.business.InventoryItemWrapper;
import dev.simplix.cirrus.common.business.ItemStackWrapper;
import dev.simplix.cirrus.common.converter.Converter;
import dev.simplix.cirrus.common.converter.Converters;
import dev.simplix.cirrus.common.i18n.LocalizedItemStackModel;
import dev.simplix.protocolize.api.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemModelConverter implements Converter<LocalizedItemStackModel, InventoryItemWrapper> {

    @Override
    public InventoryItemWrapper convert(@NotNull LocalizedItemStackModel model) {
        ItemStack itemStack = new ItemStack(model.itemType(), model.amount(), model.durability());
        itemStack.nbtData(model.nbt());
        itemStack.displayName(model.displayName());
        itemStack.lore(model.lore(), true);

        return InventoryItemWrapper.builder()
                .handle(Converters.convert(itemStack, ItemStackWrapper.class))
                .actionArguments(model.actionArguments())
                .actionHandler(model.actionHandler())
                .build();
    }

}
