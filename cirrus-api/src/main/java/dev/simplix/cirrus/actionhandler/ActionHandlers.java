package dev.simplix.cirrus.actionhandler;

import dev.simplix.cirrus.actionhandlers.AsyncOpenMenuActionHandler;
import dev.simplix.cirrus.actionhandlers.OpenMenuActionHandler;
import dev.simplix.cirrus.menu.Menu;
import dev.simplix.cirrus.menus.AbstractBrowser;
import dev.simplix.cirrus.model.CallResult;
import dev.simplix.cirrus.model.Click;
import dev.simplix.protocolize.api.item.BaseItemStack;
import dev.simplix.protocolize.data.ItemType;
import java.util.function.Supplier;
import lombok.experimental.UtilityClass;

import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;

/**
 * The `ActionHandlers` class is a utility class that provides a set of common {@link ActionHandler}
 * implementations that can be used to handle menu item clicks. An {@link ActionHandler} is a
 * functional interface that is used to specify the behavior of a menu item when it is clicked.
 *
 * @see dev.simplix.cirrus.actionhandler.ActionHandler
 */
@UtilityClass
@Slf4j
public class ActionHandlers {

  /**
   * Returns an {@link ActionHandler} that changes the type of the clicked item.
   *
   * @param function A `Supplier` that provides the new `ItemType` for the clicked item
   * @return An {@link ActionHandler} that changes the type of the clicked item
   */
  public ActionHandler changeClickedItemType(Supplier<ItemType> function) {
    return (click) -> {
      final BaseItemStack baseItemStack = click.clickedItem();
      baseItemStack.itemType(function.get());
      click.clickedMenu().update();
      return CallResult.DENY_GRABBING;
    };
  }

  /**
   * Returns an {@link ActionHandler} that sends a message to the player who clicked the menu item.
   *
   * @param message The message to send to the player
   * @return An {@link ActionHandler} that sends a message to the player who clicked the menu item
   */
  public ActionHandler sendMessage(String message) {
    return click -> {
      click.player().sendMessage(message);
      return CallResult.DENY_GRABBING;
    };
  }

  /**
   * Returns an {@link ActionHandler} that asynchronously opens a new menu. The new menu to open is
   * determined by the provided {@link Function}.
   *
   * @param function A {@link Function} that provides the new {@link Menu} to open, based on the
   *                 {@link Click} event
   * @return An {@link ActionHandler} that asynchronously opens a new menu
   */
  public ActionHandler openAsync(Function<Click, Menu> function) {
    return new AsyncOpenMenuActionHandler(function);
  }

  /**
   * Returns an {@link ActionHandler} that opens the specified {@link Menu} when the menu item is
   * clicked.
   *
   * @param menu The {@link Menu} to open when the item is clicked
   * @return An {@link ActionHandler} that opens the specified {@link Menu} when the menu item is
   * clicked
   */

  public ActionHandler openMenu(Menu menu) {
    return new OpenMenuActionHandler(menu);
  }

  /**
   * Returns an {@link ActionHandler} that opens the specified `AbstractBrowser` when the menu item
   * is clicked.
   *
   * @param browser The `AbstractBrowser` to open when the item is clicked
   * @return An {@link ActionHandler} that opens the specified `AbstractBrowser` when the menu item
   * is clicked
   */
  public ActionHandler openMenu(AbstractBrowser<?> browser) {
    return click -> {
      browser.display(click.player());
      return CallResult.DENY_GRABBING;
    };
  }
}