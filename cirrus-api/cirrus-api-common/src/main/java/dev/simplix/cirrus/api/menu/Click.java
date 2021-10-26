package dev.simplix.cirrus.api.menu;

import dev.simplix.cirrus.api.business.InventoryItemWrapper;
import dev.simplix.cirrus.api.business.ItemStackWrapper;
import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.protocolize.api.ClickType;
import lombok.NonNull;

import java.util.List;

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
        return clickedMenu.player();
    }

    public ClickType clickType() {
        return clickType;
    }

    public Menu clickedMenu() {
        return clickedMenu;
    }

    public List<String> arguments() {
        return clickedItem.actionArguments();
    }

    public <T extends ItemStackWrapper> T clickedItem() {
        return (T) clickedItem;
    }

    public int slot() {
        return slot;
    }

}