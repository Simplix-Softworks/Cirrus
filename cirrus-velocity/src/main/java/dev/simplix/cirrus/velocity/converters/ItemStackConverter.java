package dev.simplix.cirrus.velocity.converters;

import dev.simplix.cirrus.common.business.MenuItemWrapper;
import dev.simplix.cirrus.common.converter.Converter;
import dev.simplix.cirrus.common.item.ProtocolizeMenuItemWrapper;
import dev.simplix.protocolize.api.item.ItemStack;
import lombok.NonNull;

public class ItemStackConverter implements Converter<ItemStack, MenuItemWrapper> {

    @Override
    public MenuItemWrapper convert(@NonNull ItemStack itemStack) {
        return new ProtocolizeMenuItemWrapper(itemStack);
    }

}
