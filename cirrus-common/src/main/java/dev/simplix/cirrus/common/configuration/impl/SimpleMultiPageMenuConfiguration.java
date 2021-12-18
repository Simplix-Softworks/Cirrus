package dev.simplix.cirrus.common.configuration.impl;

import dev.simplix.cirrus.common.configuration.MultiPageMenuConfiguration;
import dev.simplix.cirrus.common.i18n.LocalizedString;
import dev.simplix.cirrus.common.model.ItemStackModel;
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

    private ItemStackModel nextPageItem;
    private ItemStackModel previousPageItem;
    private LocalizedString title;
    private InventoryType type;
    private ItemStackModel placeholderItem;
    private int[] reservedSlots = new int[0];
    private ItemStackModel[] items = new ItemStackModel[0];
    private Map<String, ItemStackModel> businessItems = new HashMap<>();
}
