package dev.simplix.cirrus.menu;

import dev.simplix.cirrus.player.CirrusPlayerWrapper;
import dev.simplix.cirrus.service.MenuBuildService;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public record DisplayedMenu(
        Menu value,
        Object nativeMenu,
        CirrusPlayerWrapper player,
        MenuBuildService menuBuildService,
        Long id,
        AtomicBoolean closed

) {


  public DisplayedMenu(Menu value, Object nativeMenu, CirrusPlayerWrapper player, MenuBuildService menuBuildService, Long id) {
    this(value, nativeMenu, player, menuBuildService, id, new AtomicBoolean(false));
    Menus.register(player.uuid(), this);
  }

  public void update() {
    this.menuBuildService.updateMenu(this);
  }

  public void close() {
    this.menuBuildService.closeMenu(this);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, menuBuildService, id);
  }

  @Override
  public boolean equals(Object other) {
    if (other==null) {
      return false;
    }
    if (other==this) {
      return true;
    }

    if (other instanceof DisplayedMenu otherMenu) {
      return otherMenu.id.equals(this.id);
    }

    return false;
  }
}
