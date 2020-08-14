package dev.simplix.cirrus.api.menu;

import de.exceptionflug.protocolize.api.ClickType;
import java.util.List;
import dev.simplix.cirrus.api.business.InventoryItemWrapper;
import dev.simplix.cirrus.api.business.ItemStackWrapper;

/**
 * A click contains information about a click performed on an {@link InventoryItemWrapper}.
 */
public class Click {

  private final ClickType clickType;
  private final Menu clickedMenu;
  private final InventoryItemWrapper clickedItem;
  private final int slot;

  public Click(ClickType clickType, Menu clickedMenu, InventoryItemWrapper clickedItem, int slot) {
    this.clickType = clickType;
    this.clickedMenu = clickedMenu;
    this.clickedItem = clickedItem;
    this.slot = slot;
  }

  public ClickType clickType() {
    return clickType;
  }

  public Menu clickedMenu() {
    return clickedMenu;
  }

  public List<String> getArguments() {
    return clickedItem.actionArguments();
  }

  public <T extends ItemStackWrapper> T clickedItem() {
    return (T) clickedItem;
  }

  public int slot() {
    return slot;
  }

}