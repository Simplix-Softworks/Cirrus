package dev.simplix.cirrus.bungeecord.converters;

import de.exceptionflug.protocolize.items.ItemStack;
import dev.simplix.cirrus.api.business.ItemStackWrapper;
import dev.simplix.cirrus.bungeecord.item.ProtocolizeItemStackWrapper;
import dev.simplix.core.common.converter.Converter;

public class ItemStackConverter implements Converter<ItemStack, ItemStackWrapper> {

  public ItemStackWrapper convert(ItemStack itemStack) {
    return new ProtocolizeItemStackWrapper(itemStack);
  }

}
