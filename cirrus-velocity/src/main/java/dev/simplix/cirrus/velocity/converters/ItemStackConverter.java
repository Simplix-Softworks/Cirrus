package dev.simplix.cirrus.velocity.converters;

import dev.simplix.cirrus.common.business.ItemStackWrapper;
import dev.simplix.cirrus.common.converter.Converter;
import dev.simplix.cirrus.common.item.ProtocolizeItemStackWrapper;
import dev.simplix.protocolize.api.item.ItemStack;
import lombok.NonNull;

public class ItemStackConverter implements Converter<ItemStack, ItemStackWrapper> {

    @Override
    public ItemStackWrapper convert(@NonNull ItemStack itemStack) {
        return new ProtocolizeItemStackWrapper(itemStack);
    }

}
