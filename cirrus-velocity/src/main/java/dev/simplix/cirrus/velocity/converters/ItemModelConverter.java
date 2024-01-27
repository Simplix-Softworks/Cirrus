package dev.simplix.cirrus.velocity.converters;

import dev.simplix.cirrus.common.business.InventoryMenuItemWrapper;
import dev.simplix.cirrus.common.business.MenuItemWrapper;
import dev.simplix.cirrus.common.converter.Converter;
import dev.simplix.cirrus.common.converter.Converters;
import dev.simplix.cirrus.common.item.CirrusItem;
import dev.simplix.protocolize.api.chat.ChatElement;
import dev.simplix.protocolize.api.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import java.util.stream.Collectors;

public class ItemModelConverter implements Converter<CirrusItem, InventoryMenuItemWrapper> {

    @Override
    public InventoryMenuItemWrapper convert(@NotNull CirrusItem model) {
        ItemStack itemStack = new ItemStack(model.itemType(), model.amount(), model.durability());
        itemStack.nbtData(model.nbt());
        itemStack.displayName(ChatElement.ofLegacyText(model.displayName()));
        itemStack.lore(model.lore().stream().map(ChatElement::ofLegacyText).collect(Collectors.toList()));

        return InventoryMenuItemWrapper.builder()
                .handle(Converters.convert(itemStack, MenuItemWrapper.class))
                .actionArguments(model.actionArguments())
                .actionHandler(model.actionHandler())
                .build();
    }

}
