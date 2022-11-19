package dev.simplix.cirrus.menu;

import dev.simplix.cirrus.schematic.MenuSchematic;
import dev.simplix.cirrus.schematic.impl.SimpleMenuSchematic;
import lombok.*;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractMenu extends SimpleMenuSchematic implements Menu {

  protected final MenuSchematic schematic;

  protected AbstractMenu() {
    this(new SimpleMenuSchematic());
  }

  @Override
  public final void handleDisplay() {
    registerActionHandlers();
    try {
      handleDisplay0();
    } catch (Exception exception) {
      throw new IllegalStateException("Could not handle display", exception);
    }
  }

  /**
   * Only register action-handlers in this method. Also don't write any code that can't be run on
   * the main thread. (Except in the action-handlers themselves)
   */
  protected void registerActionHandlers() {
  }

  protected void handleDisplay0() {

  }

  @Override
  public int updateTicks() {
    return -1;
  }

}
