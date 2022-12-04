package dev.simplix.cirrus.menu;

import dev.simplix.cirrus.item.CirrusItem;
import dev.simplix.cirrus.schematic.MenuSchematic;
import dev.simplix.protocolize.api.item.BaseItemStack;

import java.util.Optional;
import javax.annotation.Nullable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * A {@code MenuElement} represents a single item in a menu, containing the item and an optional
 * action handler that will be executed when the item is clicked. {@code MenuElement}s can be
 * applied to a {@link MenuSchematic} to update the item in the menu.
 *
 * @see MenuSchematic
 * @see CirrusItem
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Accessors(fluent = true)
public class MenuElement {

  /**
   * The menu schematic that the {@code MenuElement} will be applied to.
   */
  @Nullable private transient MenuSchematic menuSchematic;

  /**
   * The item that the {@code MenuElement} represents.
   */
  @Nullable private BaseItemStack item;

  /**
   * The slot in the menu that the {@code MenuElement} will be applied to.
   */
  @Nullable @Getter private Integer slot;

  /**
   * The action handler that will be executed when the item is clicked.
   */
  @Nullable private String actionHandler;

  public void set(@NonNull CirrusItem cirrusItem) {
    this.set(cirrusItem, cirrusItem.actionHandler());
  }

  /**
   * Sets the {@link BaseItemStack} and action handler of this {@code MenuElement}.
   *
   * @param item the {@code BaseItemStack} to set
   */
  public void set(@NonNull BaseItemStack item) {
    this.set(item, null);
  }

  /**
   * Sets the {@link BaseItemStack} and action handler of this {@code MenuElement}.
   *
   * @param item          the {@code BaseItemStack} to set
   * @param actionHandler the action handler to set, or {@code null} to remove the action handler
   */
  public void set(@NonNull BaseItemStack item, @Nullable String actionHandler) {
    this.actionHandler = actionHandler;
    this.item = item;
    if (this.menuSchematic != null && this.slot != null) {
      applyChanges(this.menuSchematic, this.slot);
    }
  }

  /**
   * Returns the menu schematic that this menu element belongs to.
   *
   * @return An {@link Optional} containing the menu schematic that this menu element belongs to, or
   * an empty optional if this menu element does not belong to any menu schematic.
   */
  public Optional<MenuSchematic> menuSchematic() {
    return Optional.ofNullable(menuSchematic);
  }

  /**
   * Returns the action handler associated with this menu element.
   *
   * @return An {@link Optional} containing the action handler associated with this menu element, or
   * an empty optional if this menu element does not have an associated action handler.
   */
  public Optional<String> actionHandlerString() {
    return Optional.ofNullable(actionHandler);
  }

  /**
   * Applies the changes made to this menu element to the given menu schematic.
   *
   * @param menuSchematic The menu schematic to apply the changes to.
   * @param slot          The slot in the menu schematic to apply the changes to.
   */
  public void applyChanges(@NonNull MenuSchematic menuSchematic, int slot) {
    this.slot = slot;
    menuSchematic.set(item, slot, actionHandler);
  }

  public Optional<BaseItemStack> item() {
    return Optional.ofNullable(this.item);
  }

}