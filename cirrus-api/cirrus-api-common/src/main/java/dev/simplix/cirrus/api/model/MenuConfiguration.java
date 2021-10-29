package dev.simplix.cirrus.api.model;

public interface MenuConfiguration {
    dev.simplix.cirrus.api.i18n.LocalizedString title();

    dev.simplix.protocolize.data.inventory.InventoryType type();

    ItemStackModel placeholderItem();

    int[] reservedSlots();

    ItemStackModel[] items();

    java.util.Map<String, ItemStackModel> businessItems();

    SimpleMenuConfiguration title(dev.simplix.cirrus.api.i18n.LocalizedString title);

    SimpleMenuConfiguration type(dev.simplix.protocolize.data.inventory.InventoryType type);

    SimpleMenuConfiguration placeholderItem(ItemStackModel placeholderItem);

    SimpleMenuConfiguration reservedSlots(int[] reservedSlots);

    SimpleMenuConfiguration items(ItemStackModel[] items);

    SimpleMenuConfiguration businessItems(java.util.Map<String, ItemStackModel> businessItems);
}
