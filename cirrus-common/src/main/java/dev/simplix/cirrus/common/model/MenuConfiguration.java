package dev.simplix.cirrus.common.model;

import dev.simplix.cirrus.common.i18n.LocalizedString;

public interface MenuConfiguration {
    LocalizedString title();

    dev.simplix.protocolize.data.inventory.InventoryType type();

    ItemStackModel placeholderItem();

    int[] reservedSlots();

    ItemStackModel[] items();

    java.util.Map<String, ItemStackModel> businessItems();

    SimpleMenuConfiguration title(LocalizedString title);

    SimpleMenuConfiguration type(dev.simplix.protocolize.data.inventory.InventoryType type);

    SimpleMenuConfiguration placeholderItem(ItemStackModel placeholderItem);

    SimpleMenuConfiguration reservedSlots(int[] reservedSlots);

    SimpleMenuConfiguration items(ItemStackModel[] items);

    SimpleMenuConfiguration businessItems(java.util.Map<String, ItemStackModel> businessItems);
}
