package dev.simplix.cirrus.bungeecord.converters;

import de.exceptionflug.protocolize.items.ItemStack;
import dev.simplix.cirrus.api.business.InventoryItemWrapper;
import dev.simplix.cirrus.api.business.ItemStackWrapper;
import dev.simplix.cirrus.api.i18n.LocalizedItemStackModel;
import dev.simplix.core.common.converter.Converter;
import dev.simplix.core.common.converter.Converters;

public class ItemModelConverter implements Converter<LocalizedItemStackModel, InventoryItemWrapper> {

  public InventoryItemWrapper convert(LocalizedItemStackModel model) {
    ItemStack itemStack = new ItemStack(model.itemType(), model.amount(), model.durability());
    itemStack.setNBTTag(model.nbt());
    itemStack.setDisplayName(model.displayName());
    itemStack.setLore(model.lore());

    return InventoryItemWrapper.builder()
        .handle(Converters.convert(itemStack, ItemStackWrapper.class))
        .actionArguments(model.actionArguments())
        .actionHandler(model.actionHandler())
        .build();
  }

}
