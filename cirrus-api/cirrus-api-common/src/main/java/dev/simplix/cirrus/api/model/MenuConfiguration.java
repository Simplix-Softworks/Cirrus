package dev.simplix.cirrus.api.model;

import java.util.HashMap;
import java.util.Map;

import dev.simplix.protocolize.data.inventory.InventoryType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import dev.simplix.cirrus.api.i18n.LocalizedString;

@Data
@Accessors(fluent = true)
@NoArgsConstructor
public class MenuConfiguration {

  private LocalizedString title;
  private InventoryType type;
  private ItemStackModel placeholderItem;
  private int[] reservedSlots = new int[0];
  private ItemStackModel[] items = new ItemStackModel[0];
  private Map<String, ItemStackModel> businessItems = new HashMap<>();

}
