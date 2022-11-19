package dev.simplix.cirrus.menu;

import dev.simplix.cirrus.actionhandler.ActionHandler;
import dev.simplix.cirrus.model.Click;
import dev.simplix.cirrus.player.CirrusPlayerWrapper;
import dev.simplix.cirrus.schematic.MenuSchematic;

import java.util.function.Consumer;

public interface Menu extends MenuSchematic {

  /**
   * Register an action that should apply when an item with the given actionhandler String
   * is clicked
   *
   * @param actionHandlerString actionhandlerstring of the menu
   */
  void registerActionHandler(String actionHandlerString, Consumer<Click> clickConsumer);

  /**
   * Register an action that should apply when an item with the given actionhandler String
   * is clicked
   *
   * @param actionHandlerString actionhandlerstring of the menu
   */
  void registerActionHandler(String actionHandlerString, ActionHandler actionHandler);

  void loadFrom(MenuSchematic menuSchematic);

  void handleDisplay();

  /**
   * Displays the menu to a player
   *
   * @param player player to display menu to
   * @return {@link DisplayedMenu} instance
   */
  DisplayedMenu display(CirrusPlayerWrapper player);

  // TODO: Invoke on close in platform implementations
  void handleClose();

  /**
   * @return -1 to disable automatic rebuilds of the menu
   */
  int updateTicks();

}
