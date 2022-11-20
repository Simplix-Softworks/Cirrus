package dev.simplix.cirrus.service;

import dev.simplix.cirrus.menu.DisplayedMenu;
import dev.simplix.cirrus.menu.Menu;
import dev.simplix.cirrus.player.CirrusPlayerWrapper;

public interface MenuBuildService {

  default DisplayedMenu buildAndOpenMenu(
      Menu menu,
      CirrusPlayerWrapper playerWrapper) {
    menu.handleDisplay();
    if (menu.soundOnOpen() != null) {
      playerWrapper.play(menu.soundOnOpen());
    }
    return openAndBuildMenu0(menu, playerWrapper);
  }

  DisplayedMenu openAndBuildMenu0(Menu menu, CirrusPlayerWrapper playerWrapper);

  void updateMenu(DisplayedMenu displayedMenu);

  default void closeMenu(DisplayedMenu displayedMenu) {
    displayedMenu.closed().set(true);
    closeMenu0(displayedMenu);
    displayedMenu.value().handleClose();
  }

  void closeMenu0(DisplayedMenu displayedMenu);

}
