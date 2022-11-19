package dev.simplix.cirrus.menus.example;

import dev.simplix.cirrus.actionhandler.ActionHandlers;
import dev.simplix.cirrus.item.CirrusItem;
import dev.simplix.cirrus.item.Items;
import dev.simplix.cirrus.menu.MenuRow;
import dev.simplix.cirrus.menus.AbstractBrowser;
import dev.simplix.cirrus.model.Click;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.data.ItemType;
import java.util.Arrays;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemTypeBrowser extends AbstractBrowser<ItemType> {

  private final int protocolVersion;

  public ItemTypeBrowser(int protocolVersion) {
    this.protocolVersion = protocolVersion;
    title("§7Item Browser");
  }

  @Override
  protected int updateTicks() {
    return 2;
  }

  @Override
  protected void registerActionHandlers() {
    registerActionHandler("back", ActionHandlers.openMenu(new SelectMenu()));
  }

  @Override
  protected void interceptBottomRow(MenuRow bottomRow) {

    bottomRow.get(8).set(
            CirrusItem
                    .of(ItemType.ACACIA_DOOR,
                            "§7Back",
                            "§7Go back to the previous menu")
                    .actionHandler("back")
    );
  }



  @Override
  protected void handleClick(Click click, ItemType value) {
    click.player().sendMessage("§7You clicked on " + value.name());
  }

  @Override
  protected Collection<ItemType> elements() {
    return Arrays.stream(ItemType.values()).filter(item -> {
      return Protocolize.mappingProvider().mapping(item, protocolVersion)!=null;
    }).toList();
  }

  @Override
  protected CirrusItem map(ItemType element) {
    return Items.withWaveEffect(element,  element.name());
  }
}
