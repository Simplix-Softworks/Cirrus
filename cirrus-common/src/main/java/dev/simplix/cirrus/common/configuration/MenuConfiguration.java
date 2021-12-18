package dev.simplix.cirrus.common.configuration;

import dev.simplix.cirrus.common.i18n.LocalizedString;
import dev.simplix.cirrus.common.model.ItemStackModel;

public interface MenuConfiguration {
    LocalizedString title();

    dev.simplix.protocolize.data.inventory.InventoryType type();

    ItemStackModel placeholderItem();

    int[] reservedSlots();

    ItemStackModel[] items();

    java.util.Map<String, ItemStackModel> businessItems();

    MenuConfiguration title(LocalizedString title);

    MenuConfiguration type(dev.simplix.protocolize.data.inventory.InventoryType type);

    MenuConfiguration placeholderItem(ItemStackModel placeholderItem);

    MenuConfiguration reservedSlots(int[] reservedSlots);

    MenuConfiguration items(ItemStackModel[] items);

    MenuConfiguration businessItems(java.util.Map<String, ItemStackModel> businessItems);
}
