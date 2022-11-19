package dev.simplix.cirrus.schematic;

import com.google.common.base.Preconditions;
import dev.simplix.cirrus.actionhandler.ActionHandler;
import dev.simplix.cirrus.item.CirrusItem;
import dev.simplix.cirrus.menu.*;
import dev.simplix.cirrus.model.*;
import dev.simplix.protocolize.api.item.BaseItemStack;
import dev.simplix.protocolize.api.util.ProtocolVersions;
import dev.simplix.protocolize.data.Sound;
import dev.simplix.protocolize.data.inventory.InventoryType;
import java.util.*;
import javax.annotation.Nullable;

public interface MenuSchematic {

  /**
   * Get a business-item with the given key or a exception is thrown
   * See {@link BusinessItemMap}
   *
   * @param keyword key to get the business-item
   * @return business-item
   */
  default CirrusItem forceBusinessItem(String keyword) {
    return Preconditions.checkNotNull(businessItems().get(keyword), "No business-item named '" + keyword + "' found");
  }

  /**
   * Creates a copy of this menu
   *
   * @return copy of this menu
   */
  MenuSchematic copy();

  /**
   * Get the title of the menu
   *
   * @return title of the menu
   */
  @Nullable
  String title();

  /**
   * Set the title of the menu
   *
   * @param title New title of the menu
   * @return this
   */
  MenuSchematic title(String title);

  /**
   * Get the locale of the menu
   *
   * @return locale of the menu
   */
  Locale locale();

  /**
   * Get the type of the menu
   *
   * @return type of the menu
   */
  InventoryType type();

  /**
   * Get the typical size of the menu using the {@link ProtocolVersions#MINECRAFT_LATEST}
   *
   * @return typical size of the menu
   */
  default int typicalSize() {
    return typicalSize(ProtocolVersions.MINECRAFT_LATEST);
  }

  /**
   * Get the typical size of the menu
   *
   * @param protocolVersion Protocol version to use for the calculation
   * @return typical size of the menu
   */
  int typicalSize(int protocolVersion);

  /**
   * Returns the placeholder-item of the menu if it has any. Otherwise
   * {@link Optional#empty()} will be returned
   *
   * @return placeholder item
   */
  Optional<PlaceholderItem> placeholderItem();


  /**
   * Set of reserved slots that no item can be placed in
   */
  Set<Integer> reservedSlots();

  /**
   * Get the root-items of the menu
   * This is the core representation of the menu.
   * All items are placed into this map
   * {@link MenuContent} is a map of menu-items
   *
   * @return items of the menu
   */
  MenuContent rootItems();


  /**
   * Calculates the center element's slot
   * The center element is the element in the middle of the menu
   * <p>
   * <a href="https://www.spigotmc.org/threads/get-the-center-slot-of-a-menu.379586/">...</a>
   *
   * @return the center element
   */
  int centerSlot();

  /**
   * Calculates the center element
   * The center element is the element in the middle of the menu
   * <p>
   * <a href="https://www.spigotmc.org/threads/get-the-center-slot-of-a-menu.379586/">...</a>
   *
   * @return the center element
   */
  default MenuElement center() {
    return element(centerSlot());
  }

  /**
   * Return the row of the menu using the given row-index <p>
   * See {@link MenuRow}
   * <p>
   * Example: <p>
   * - row(0) returns the first row <p>
   * - row(1) returns the second row <p>
   *
   * @param row Row index
   * @return Menu row at the given index
   */
  @Nullable
  MenuRow row(int row);


  /**
   * Return the first row of the menu using the given row-index <p>
   */
  default MenuRow firstRow() {
    return row(0);
  }

  default MenuRow middleRow() {
    if (typicalSize()==9) {
      return row(1);
    }
    return row(typicalSize() / 18);
  }


  /**
   * Return the business-item-map of the menu
   * See {@link BusinessItemMap}
   *
   * @return business-item-map of the menu
   */
  BusinessItemMap businessItems();

  /**
   * Set the type of the menu.
   * See {@link InventoryType}
   *
   * @param type type to set
   * @return this
   */
  MenuSchematic type(InventoryType type);

  /**
   * Set the placeholder-item of the menu.
   * See {@link PlaceholderItem}
   *
   * @param placeholderItem placeholder-item to set
   * @return this
   */
  MenuSchematic placeholderItem(PlaceholderItem placeholderItem);


  /**
   * Set the reservedSlots of the menu.
   *
   * @param reservedSlots reservedSlots to set
   * @return this
   */
  MenuSchematic reservedSlots(Set<Integer> reservedSlots);

  /**
   * Set the business-item-map of the menu.
   * See {@link BusinessItemMap}
   *
   * @param businessItems business-items to set
   * @return this
   */
  MenuSchematic businessItems(BusinessItemMap businessItems);

  /**
   * Remove item at the given slot from the menu
   *
   * @param slot Slot to remove the item from
   * @return this
   */
  MenuSchematic remove(int slot);

  /**
   * Returns an action-handler-string for the given slot if there is any
   * registered or else {@link Optional#empty()}
   *
   * @param slot Slot to get the action-handler-string from
   * @return this
   */
  Optional<String> actionHandlerString(int slot);

  /**
   * Returns an action-handler for the given slot if there is any
   * registered or else {@link Optional#empty()}
   * <p>
   * Internally uses {@link #actionHandlerString(int)} and then get the action-handler
   *
   * @param slot Slot to get the action-handler from
   * @return this
   */
  Optional<ActionHandler> actionHandler(int slot);

  /**
   * Set an item to the menu
   * See {@link BaseItemStack}
   *
   * @param item          Item to set
   * @param slot          slot to set the item to
   * @param actionHandler Action-handler of the item
   * @return this
   */
  MenuSchematic set(BaseItemStack item, int slot, @Nullable String actionHandler);

  /**
   * Set an item to the menu
   * See {@link CirrusItem}
   *
   * @param item Item to set
   * @return this
   */
  default MenuSchematic set(CirrusItem item) {
    return this.set(item, item.slot(), item.actionHandler());
  }

  /**
   * Get the element of the menu at the given slot.
   * Can not be null.
   * See {@link MenuElement}
   *
   * @param slot Slot to get the element from
   * @return this
   */
  MenuElement element(int slot);

  /**
   * Get the element of the menu at the given slot.
   * Will return null if there is o item at the given slot
   * See {@link MenuElement}
   *
   * @param slot Slot to get the item from
   * @return this
   */
  @Nullable
  BaseItemStack get(int slot);

  /**
   * Set an item to the menu
   * See {@link BaseItemStack}
   *
   * @param item          Item to set
   * @param actionHandler Action-handler of the item
   * @return this
   */
  int add(BaseItemStack item, @Nullable String actionHandler);

  /**
   * Add an item to the menu at the next free slot
   *
   * @param item Item to add
   * @return this
   */
  default int add(CirrusItem item) {
    return add(item, item.actionHandler());
  }



  @Nullable
  Sound soundOnOpen();
}
