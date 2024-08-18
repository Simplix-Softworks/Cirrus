package dev.simplix.cirrus.model;

import dev.simplix.cirrus.item.CirrusItem;
import dev.simplix.cirrus.item.Items;
import dev.simplix.cirrus.schematic.impl.SimpleMenuSchematic;
import dev.simplix.protocolize.api.chat.ChatElement;
import dev.simplix.protocolize.data.ItemType;

public class ExampleMenuSchematic extends SimpleMenuSchematic {

  public ExampleMenuSchematic() {
    set(CirrusItem
        .of(ItemType.ITEM_FRAME)
        .displayName(ChatElement.ofLegacyText("item-1"))
        .lore(ChatElement.ofLegacyText("test123"))
        .actionHandler("sheep")
        .slot(0));
    set(Items
        .withWaveEffect(ItemType.ITEM_FRAME, "test", ChatElement.ofLegacyText("test123"))
        .displayName(ChatElement.ofLegacyText("item-2"))
        .lore(ChatElement.ofLegacyText("test123"))
        .actionHandler("sheep")
        .slot(1));
    set(Items
        .withSpectrumEffect(ItemType.ITEM_FRAME, "test", ChatElement.ofLegacyText("test123"))
        .displayName(ChatElement.ofLegacyText("item-3"))
        .lore(ChatElement.ofLegacyText("test123"))
        .actionHandler("sheep")
        .slot(2));
  }
}
