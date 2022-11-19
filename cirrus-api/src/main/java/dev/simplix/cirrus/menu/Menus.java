package dev.simplix.cirrus.menu;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Menus {

  private final Map<UUID, DisplayedMenu> menuMap = new ConcurrentHashMap<>();

  public Optional<DisplayedMenu> of(UUID uuid) {
    return Optional.ofNullable(menuMap.get(uuid));
  }

  public DisplayedMenu register(UUID uuid, DisplayedMenu menu) {
    return menuMap.put(uuid, menu);
  }

  /**
   * Removes the menu from the map
   *
   * @return return = null -> Nothing was removed
   */
  public DisplayedMenu remove(UUID uuid) {
    final DisplayedMenu remove = menuMap.remove(uuid);
    if (remove != null) {
      remove.value().handleClose();
    }
    return remove;
  }

  public Collection<DisplayedMenu> all() {
    return Collections.unmodifiableCollection(menuMap.values());
  }
}
