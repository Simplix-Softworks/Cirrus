package dev.simplix.cirrus.actionhandler;

import dev.simplix.cirrus.actionhandlers.AsyncOpenMenuActionHandler;
import dev.simplix.cirrus.actionhandlers.OpenMenuActionHandler;
import dev.simplix.cirrus.menu.Menu;
import dev.simplix.cirrus.menus.AbstractBrowser;
import dev.simplix.cirrus.model.CallResult;
import dev.simplix.cirrus.model.Click;
import dev.simplix.protocolize.api.item.BaseItemStack;
import dev.simplix.protocolize.data.ItemType;
import lombok.experimental.UtilityClass;

import java.util.function.Function;

/**
 * Utility class for action-handlers, providing the most common actions in a streamlined form
 */
@UtilityClass
public class ActionHandlers {

  public ActionHandler changeClickedItemType(ItemType newType) {
    return (click) -> {
      final BaseItemStack baseItemStack = click.clickedItem();
      baseItemStack.itemType(newType);
      click.clickedMenu().update();
      return CallResult.DENY_GRABBING;
    };
  }

  public ActionHandler sendMessage(String message) {
    return click -> {
      click.player().sendMessage(message);
      return CallResult.DENY_GRABBING;
    };
  }

  public ActionHandler openAsync(Function<Click, Menu> function) {
    return new AsyncOpenMenuActionHandler(function);
  }

  public ActionHandler openMenu(Menu menu) {
    return new OpenMenuActionHandler(menu);
  }

  public ActionHandler openMenu(AbstractBrowser<?> browser) {
    return click -> {
      browser.display(click.player());
      return CallResult.DENY_GRABBING;
    };
  }
}