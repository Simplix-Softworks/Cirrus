package dev.simplix.cirrus.common.model;

import dev.simplix.cirrus.common.business.InventoryMenuItemWrapper;
import dev.simplix.cirrus.common.business.MenuItemWrapper;
import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.common.menu.Menu;
import dev.simplix.protocolize.api.ClickType;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A click contains information about a click performed on an {@link InventoryMenuItemWrapper}.
 */
public final class Click {

    private final ClickType clickType;
    private final Menu clickedMenu;
    private final InventoryMenuItemWrapper clickedItem;
    private final int slot;

    public Click(
            @NonNull ClickType clickType,
            @NonNull Menu clickedMenu,
            @Nullable InventoryMenuItemWrapper clickedItem,
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
        if (this.clickedItem != null) {
            return this.clickedItem.actionArguments();
        }
        return new ArrayList<>();
    }

    public <T extends MenuItemWrapper> T clickedItem() {
        return (T) this.clickedItem;
    }

    public int slot() {
        return this.slot;
    }

}