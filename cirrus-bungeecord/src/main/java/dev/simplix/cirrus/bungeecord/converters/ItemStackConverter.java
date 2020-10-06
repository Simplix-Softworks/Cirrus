package dev.simplix.cirrus.bungeecord.converters;

import de.exceptionflug.protocolize.items.ItemStack;
import dev.simplix.cirrus.api.business.ItemStackWrapper;
import dev.simplix.cirrus.common.item.ProtocolizeItemStackWrapper;
import dev.simplix.core.common.converter.Converter;
import lombok.NonNull;

public class ItemStackConverter implements Converter<ItemStack, ItemStackWrapper> {

  @Override
  public ItemStackWrapper convert(@NonNull ItemStack itemStack) {
    return new ProtocolizeItemStackWrapper(itemStack);
  }

}
