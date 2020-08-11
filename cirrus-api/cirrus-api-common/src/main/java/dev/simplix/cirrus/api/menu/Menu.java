package dev.simplix.cirrus.api.menu;

import de.exceptionflug.protocolize.inventory.InventoryType;
import dev.simplix.core.common.converter.Converters;
import java.util.Locale;
import java.util.function.Supplier;
import dev.simplix.cirrus.api.business.ItemStackWrapper;
import dev.simplix.cirrus.api.business.PlayerWrapper;

public interface Menu extends ErrorProne {

  /**
   * @return The inventory type
   */
  InventoryType inventoryType();

  /**
   * Registers a new action handler
   *
   * @param name          the name
   * @param actionHandler the action handler
   */
  void registerActionHandler(String name, ActionHandler actionHandler);

  /**
   * ActionHandler which handles clicks in empty space
   *
   * @param actionHandler the action handler
   */
  void customActionHandler(ActionHandler actionHandler);

  /**
   * This opens the menu for the specified player
   * @param menuBuilder The menu builder which should be used
   */
  void open(MenuBuilder menuBuilder);

  /**
   * This builds the menu
   */
  void build();

  /**
   * ActionHandler which handles clicks in empty space
   *
   * @return The custom action handler
   */
  ActionHandler customActionHandler();

  /**
   * Returns an action handler by its name
   *
   * @param name the name of the desired action handler
   * @return the action handler or null if not found
   */
  ActionHandler actionHandler(String name);

  /**
   * Returns the {@link Container} for the upper inventory.
   *
   * @return container
   */
  Container topContainer();

  /**
   * Returns the {@link Container} for the lower inventory.
   *
   * @return container
   */
  Container bottomContainer();

  /**
   * Returns the title of the menu
   *
   * @return title string
   */
  String title();

  /**
   * Sets the title of the menu
   *
   * @param title title
   */
  void title(String title);

  /**
   * @return The locale the menu is in
   */
  Locale locale();

  /**
   * Returns the owner of this menu. The current viewer.
   * @return The player
   */
  PlayerWrapper player();

  /**
   * Returns a {@link Supplier} which returns a string array used for placeholder replacement.
   * @return Supplier for string array
   */
  Supplier<String[]> replacements();

  /**
   * Sets a {@link Supplier} which returns a string array used for placeholder replacement.
   * @param supplier supplier
   */
  void replacements(Supplier<String[]> supplier);

  /**
   * Called when leaving the current menu.
   *
   * @param inventorySwitch true if a new menu was built within the last 55ms
   */
  default void handleClose(boolean inventorySwitch) {
  }

  /**
   * Converts an object into an ItemStackWrapper.
   *
   * @param object the object to convert
   * @return the wrapper object
   */
  default ItemStackWrapper wrapItemStack(Object object) {
    return Converters.convert(object, ItemStackWrapper.class);
  }

}
