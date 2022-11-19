package dev.simplix.cirrus.menu;

import dev.simplix.cirrus.util.ToStringUtil;
import java.util.*;
import lombok.NonNull;

/**
 * Represents a row of a menu
 * Using {@link MenuElement} as a wrapper for the ItemStack, actionHandler &
 * slot
 */
public class MenuRow implements Iterable<MenuElement> {
  private final List<MenuElement> items;

  public MenuRow() {
    this(elements());
  }

  public MenuRow(List<MenuElement> items) {
    if (items.size()!=9) {
      throw new IllegalArgumentException("Items#size must be 9 but is " + items.size() + ". There are 9 items in a menu-row");
    }
    this.items = items;
  }

  private static List<MenuElement> elements() {
    final List<MenuElement> menuElements = new ArrayList<>(9);
    for (int i = 0; i < 9; i++) {
      menuElements.add(new MenuElement());
    }
    return menuElements;
  }

  /**
   * This method will get the element at the given slot.
   *
   * @param indexOfItemInRow The index of the item in the row (not the index
   *                         of the item in the whole menu)
   * @return The item at the given index
   */
  @NonNull
  public MenuElement get(int indexOfItemInRow) {
    MenuElement menuElement = items.get(indexOfItemInRow);

    if (menuElement==null) {
      menuElement = new MenuElement();
      items.set(indexOfItemInRow, menuElement);
    }

    return menuElement;
  }

  @Override
  public String toString() {
    return ToStringUtil.of(this).add("items", items).toString();
  }

  @Override
  public Iterator<MenuElement> iterator() {
    return items.iterator();
  }
}
