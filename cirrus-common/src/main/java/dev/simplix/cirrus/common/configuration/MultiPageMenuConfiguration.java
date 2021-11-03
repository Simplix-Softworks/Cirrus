package dev.simplix.cirrus.common.configuration;

import dev.simplix.cirrus.common.i18n.LocalizedString;
import dev.simplix.cirrus.common.model.ItemStackModel;

public interface MultiPageMenuConfiguration extends MenuConfiguration {
    ItemStackModel nextPageItem();

    ItemStackModel previousPageItem();

    MultiPageMenuConfiguration title(LocalizedString title);

    MultiPageMenuConfiguration type(dev.simplix.protocolize.data.inventory.InventoryType type);

    MultiPageMenuConfiguration placeholderItem(ItemStackModel placeholderItem);

    MultiPageMenuConfiguration reservedSlots(int[] reservedSlots);

    MultiPageMenuConfiguration items(ItemStackModel[] items);

    MultiPageMenuConfiguration businessItems(java.util.Map<String, ItemStackModel> businessItems);

}
