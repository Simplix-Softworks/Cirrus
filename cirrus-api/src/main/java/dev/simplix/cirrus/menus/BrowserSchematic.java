package dev.simplix.cirrus.menus;

import dev.simplix.cirrus.menu.MenuRow;
import dev.simplix.cirrus.model.BusinessItemMap;
import dev.simplix.cirrus.model.CallResult;
import dev.simplix.protocolize.data.inventory.InventoryType;
import javax.annotation.Nullable;

public record BrowserSchematic(
        String title,
        @Nullable CallResult standardResult,
        @Nullable InventoryType fixedSize,
        @Nullable MenuRow bottomRow,
        @Nullable BusinessItemMap businessItemMap
) {
}
