package dev.simplix.cirrus.model;

import dev.simplix.protocolize.api.item.BaseItemStack;
import java.util.Arrays;
import lombok.Data;

/**

 The PlaceholderItem class is a record that represents a placeholder item in a menu. It has three
 fields: an instance of {@link BaseItemStack} representing the item, a string representing the action
 handler for the item, and an array of integers representing the slots in the menu where the item
 should be placed. It has a static method called of(), which creates and returns a new instance of
 PlaceholderItem with the given values for the item, action handler, and slots. It also has a copy()
 method, which creates and returns a new instance of PlaceholderItem with the same values as the
 original instance.
 */
public record PlaceholderItem(BaseItemStack item, String actionHandler, int[] slots) {
  /**

   Static factory method that creates and returns a new instance of PlaceholderItem with the given
   values for the item, action handler, and slots.
   @param item the item for the placeholder
   @param actionHandler the action handler for the item
   @param slots the slots in the menu where the item should be placed
   @return a new instance of PlaceholderItem
   */
  public static PlaceholderItem of(BaseItemStack item, String actionHandler, int... slots) {
    return new PlaceholderItem(item, actionHandler, slots);
  }

  /**

   Creates and returns a new instance of PlaceholderItem with the same values as the original
   instance.
   @return a new instance of PlaceholderItem with the same values as the original instance
   */
  public PlaceholderItem copy() {
    return new PlaceholderItem(
        this.item,
        this.actionHandler,
        Arrays.copyOf(this.slots, this.slots.length));
  }
}