package dev.simplix.cirrus.spigot.converters;

import dev.simplix.cirrus.api.business.InventoryItemWrapper;
import dev.simplix.cirrus.api.business.ItemStackWrapper;
import dev.simplix.cirrus.api.converter.Converter;
import dev.simplix.cirrus.api.converter.Converters;
import dev.simplix.cirrus.api.i18n.LocalizedItemStackModel;
import dev.simplix.protocolize.api.item.ItemStack;
import lombok.NonNull;

public class ItemModelConverter implements Converter<LocalizedItemStackModel, InventoryItemWrapper> {

  @Override
  public InventoryItemWrapper convert(@NonNull LocalizedItemStackModel model) {
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
