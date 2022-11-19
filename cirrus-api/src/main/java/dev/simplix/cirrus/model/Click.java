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
 * A click contains information about a click performed on an {@link DisplayedMenu}.
 */
public final class Click {

  private final ClickType clickType;
  private final DisplayedMenu clickedMenu;
  private final BaseItemStack clickedItem;
  private final int slot;

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

  public CirrusPlayerWrapper player() {
    return this.clickedMenu.player();
  }

  public ClickType clickType() {
    return this.clickType;
  }

  public DisplayedMenu clickedMenu() {
    return this.clickedMenu;
  }

  public List<String> arguments() {
    if (this.clickedItem!=null && this.clickedItem instanceof CirrusItem) {
      return ((CirrusItem) this.clickedItem).actionArguments();
    }
    return new ArrayList<>();
  }

  @SuppressWarnings("unchecked")
  public <T extends BaseItemStack> T clickedItem() {
    return (T) this.clickedItem;
  }

  public int slot() {
    return this.slot;
  }

}