package dev.simplix.cirrus.common.model;

import dev.simplix.cirrus.common.i18n.LocalizedString;
import dev.simplix.protocolize.data.inventory.InventoryType;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@NoArgsConstructor
public class SimpleMenuConfiguration implements MenuConfiguration {

    private LocalizedString title;
    private InventoryType type;
    private ItemStackModel placeholderItem;
    private int[] reservedSlots = new int[0];
    private ItemStackModel[] items = new ItemStackModel[0];
    private Map<String, ItemStackModel> businessItems = new HashMap<>();

}
