package dev.simplix.cirrus.menus.example;

import com.google.common.collect.Iterators;
import dev.simplix.cirrus.actionhandler.ActionHandlers;
import dev.simplix.cirrus.item.CirrusItem;
import dev.simplix.cirrus.menus.SimpleMenu;
import dev.simplix.protocolize.data.ItemType;
import dev.simplix.protocolize.data.inventory.InventoryType;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NextMenu extends SimpleMenu {

  private final Iterator<ItemType> iterator = Iterators.cycle(
      ItemType.STONE,
      ItemType.EMERALD_BLOCK,
      ItemType.BONE_BLOCK,
      ItemType.IRON_BLOCK,
      ItemType.FIRE_CORAL_BLOCK,
      ItemType.COPPER_BLOCK);

  public NextMenu() {
    title("Next");
    type(InventoryType.GENERIC_9X5);

    set(CirrusItem.of(iterator.next(), "§aClick").slot(9 * 2 + 4).actionHandler("click"));

    row(5).get(8).set(CirrusItem.of(ItemType.DARK_OAK_DOOR, "§7Back").actionHandler("back"));

  }

  @Override
  protected void registerActionHandlers() {
    registerActionHandler("back", ActionHandlers.openMenu(new SelectMenu()));

    registerActionHandler(
        "click",
        ActionHandlers.changeClickedItemType(iterator::next));
  }
}
