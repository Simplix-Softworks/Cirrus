package dev.simplix.cirrus.model;

import dev.simplix.cirrus.item.CirrusItem;
import dev.simplix.cirrus.menu.DisplayedMenu;
import dev.simplix.cirrus.player.CirrusPlayerWrapper;
import dev.simplix.protocolize.api.ClickType;
import dev.simplix.protocolize.api.item.BaseItemStack;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import lombok.NonNull;

/**
 * A click contains information about a click performed on an {@link DisplayedMenu}. The information
 * include the type of click, the menu that was clicked, the item that was clicked, and the slot
 * where the click occurred. This information can be used by other classes to handle the click and
 * respond accordingly.
 */
public final class Click {

  private final ClickType clickType;
  private final DisplayedMenu clickedMenu;
  private final BaseItemStack clickedItem;
  private final int slot;

  /**
   * Create a new click with the given click type, menu, item, and slot.
   *
   * @param clickType The type of click performed
   * @param clickedMenu The menu on which the click was performed
   * @param clickedItem The item that was clicked (if any)
   * @param slot The slot that was clicked
   */
  public Click(
      @NonNull ClickType clickType,
      @NonNull DisplayedMenu clickedMenu,
      @Nullable BaseItemStack clickedItem,
      int slot) {
    this.clickType = clickType;
    this.clickedMenu = clickedMenu;
    this.clickedItem = clickedItem;
    this.slot = slot;
  }

  /**
   * Get the player who performed the click.
   *
   * @return The player who performed the click
   */
  public CirrusPlayerWrapper player() {
    return this.clickedMenu.player();
  }

  /**
   * Get the type of click performed.
   *
   * @return The type of click performed
   */
  public ClickType clickType() {
    return this.clickType;
  }

  /**
   * Returns the {@link DisplayedMenu} that was clicked by the player.
   *
   * @return the clicked menu
   */
  public DisplayedMenu clickedMenu() {
    return this.clickedMenu;
  }

  /**
   * Returns a list of arguments provided by the clicked item. If no arguments were provided,
   * an empty list is returned.
   *
   * @return the list of arguments
   */
  public List<String> arguments() {
    if (this.clickedItem != null && this.clickedItem instanceof CirrusItem) {
      return ((CirrusItem) this.clickedItem).actionArguments();
    }
    return new ArrayList<>();
  }

  /**
   * Returns the clicked item, or null if no item was clicked.
   *
   * @param <T> the type of the clicked item
   * @return the clicked item
   */
  @SuppressWarnings("unchecked")
  public <T extends BaseItemStack> T clickedItem() {
    return (T) this.clickedItem;
  }

  /**
   * Returns the slot number of the clicked item.
   *
   * @return the slot number
   */
  public int slot() {
    return this.slot;
  }


}