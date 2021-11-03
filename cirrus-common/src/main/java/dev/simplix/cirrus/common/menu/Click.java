package dev.simplix.cirrus.common.menu;

import dev.simplix.cirrus.common.business.InventoryItemWrapper;
import dev.simplix.cirrus.common.business.ItemStackWrapper;
import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.protocolize.api.ClickType;
import java.util.List;
import lombok.NonNull;

/**
 * A click contains information about a click performed on an {@link InventoryItemWrapper}.
 */
public class Click {

    private final ClickType clickType;
    private final Menu clickedMenu;
    private final InventoryItemWrapper clickedItem;
    private final int slot;

    public Click(
            @NonNull ClickType clickType,
            @NonNull Menu clickedMenu,
            @NonNull InventoryItemWrapper clickedItem,
            int slot) {
        this.clickType = clickType;
        this.clickedMenu = clickedMenu;
        this.clickedItem = clickedItem;
        this.slot = slot;
    }

    public PlayerWrapper player() {
        return this.clickedMenu.player();
    }

    public ClickType clickType() {
        return this.clickType;
    }

    public Menu clickedMenu() {
        return this.clickedMenu;
    }

    public List<String> arguments() {
        return this.clickedItem.actionArguments();
    }

    public <T extends ItemStackWrapper> T clickedItem() {
        return (T) this.clickedItem;
    }

    public int slot() {
        return this.slot;
    }

}