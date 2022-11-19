package dev.simplix.cirrus.model;

import dev.simplix.cirrus.item.CirrusItem;
import dev.simplix.cirrus.item.Items;
import dev.simplix.cirrus.schematic.impl.SimpleMenuSchematic;
import dev.simplix.protocolize.data.ItemType;

public class ExampleMenuSchematic extends SimpleMenuSchematic {

  public ExampleMenuSchematic() {
    set(CirrusItem
        .of(ItemType.ITEM_FRAME)
        .displayName("item-1")
        .lore("test123")
        .actionHandler("sheep")
        .slot(0));
    set(Items
        .withWaveEffect(ItemType.ITEM_FRAME, "test", "test123")
        .displayName("item-2")
        .lore("test123")
        .actionHandler("sheep")
        .slot(1));
    set(Items
        .withSpectrumEffect(ItemType.ITEM_FRAME, "test", "test123")
        .displayName("item-3")
        .lore("test123")
        .actionHandler("sheep")
        .slot(2));
  }
}
