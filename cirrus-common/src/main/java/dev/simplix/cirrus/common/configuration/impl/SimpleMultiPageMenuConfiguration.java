package dev.simplix.cirrus.common.configuration.impl;

import dev.simplix.cirrus.common.configuration.MultiPageMenuConfiguration;
import dev.simplix.cirrus.common.i18n.LocalizedString;
import dev.simplix.cirrus.common.i18n.LocalizedItemStackModel;
import dev.simplix.protocolize.data.inventory.InventoryType;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(fluent = true)
@AllArgsConstructor
@NoArgsConstructor
public class SimpleMultiPageMenuConfiguration implements MultiPageMenuConfiguration {

    private LocalizedItemStackModel nextPageItem;
    private LocalizedItemStackModel previousPageItem;
    private LocalizedString title;
    private InventoryType type;
    private LocalizedItemStackModel placeholderItem;
    private int[] reservedSlots = new int[0];
    private LocalizedItemStackModel[] items = new LocalizedItemStackModel[0];
    private Map<String, LocalizedItemStackModel> businessItems = new HashMap<>();
}
