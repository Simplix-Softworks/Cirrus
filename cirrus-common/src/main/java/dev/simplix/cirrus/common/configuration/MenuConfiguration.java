package dev.simplix.cirrus.common.configuration;

import com.google.common.base.Preconditions;
import dev.simplix.cirrus.common.i18n.LocalizedItemStackModel;
import dev.simplix.cirrus.common.i18n.LocalizedString;

public interface MenuConfiguration {

    default LocalizedItemStackModel forceBusinessItem(String keyword) {
        return Preconditions.checkNotNull(
                businessItems().get(keyword),
                "No business-item named '" + keyword + "' found"
        );
    }

    LocalizedString title();

    dev.simplix.protocolize.data.inventory.InventoryType type();

    LocalizedItemStackModel placeholderItem();

    int[] reservedSlots();

    LocalizedItemStackModel[] items();

    java.util.Map<String, LocalizedItemStackModel> businessItems();

    MenuConfiguration title(LocalizedString title);

    MenuConfiguration type(dev.simplix.protocolize.data.inventory.InventoryType type);

    MenuConfiguration placeholderItem(LocalizedItemStackModel placeholderItem);

    MenuConfiguration reservedSlots(int[] reservedSlots);

    MenuConfiguration items(LocalizedItemStackModel[] items);

    MenuConfiguration businessItems(java.util.Map<String, LocalizedItemStackModel> businessItems);
}
