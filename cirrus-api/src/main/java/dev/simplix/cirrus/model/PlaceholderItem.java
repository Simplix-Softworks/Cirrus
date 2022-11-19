package dev.simplix.cirrus.model;

import dev.simplix.protocolize.api.item.BaseItemStack;
import java.util.Arrays;
import lombok.Data;

@Data
public class PlaceholderItem {

  private final BaseItemStack item;
  private final String actionHandler;
  private final int[] slots;

  public static PlaceholderItem of(BaseItemStack item, String actionHandler, int... slots) {
    return new PlaceholderItem(item, actionHandler, slots);
  }

  public PlaceholderItem copy() {
    return new PlaceholderItem(
        this.item,
        this.actionHandler,
        Arrays.copyOf(this.slots, this.slots.length));
  }
}
