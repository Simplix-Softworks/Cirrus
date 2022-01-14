package dev.simplix.cirrus.common.configuration;

import dev.simplix.cirrus.common.i18n.LocalizedString;
import dev.simplix.cirrus.common.i18n.LocalizedItemStackModel;

public interface MultiPageMenuConfiguration extends MenuConfiguration {
    LocalizedItemStackModel nextPageItem();

    LocalizedItemStackModel previousPageItem();

    MultiPageMenuConfiguration title(LocalizedString title);

    MultiPageMenuConfiguration type(dev.simplix.protocolize.data.inventory.InventoryType type);

    MultiPageMenuConfiguration placeholderItem(LocalizedItemStackModel placeholderItem);

    MultiPageMenuConfiguration reservedSlots(int[] reservedSlots);

    MultiPageMenuConfiguration items(LocalizedItemStackModel[] items);

    MultiPageMenuConfiguration businessItems(java.util.Map<String, LocalizedItemStackModel> businessItems);

}
