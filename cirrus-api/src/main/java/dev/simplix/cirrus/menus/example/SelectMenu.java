package dev.simplix.cirrus.menus.example;

import dev.simplix.cirrus.actionhandler.ActionHandlers;
import dev.simplix.cirrus.item.Items;
import dev.simplix.cirrus.menus.SimpleMenu;
import dev.simplix.protocolize.api.util.ProtocolVersions;
import dev.simplix.protocolize.data.ItemType;

public class SelectMenu extends SimpleMenu {

  public SelectMenu() {
    title("select item");

    set(Items.withSpectrumEffect(
            ItemType.STONE,
            "Next Menu",
            "§7Click here to go",
            "§7to the next menu")
        .slot(12)
        .actionHandler("next")
       );

    set(Items.withSpectrumEffect(
            ItemType.STONE,
            "Item Type Browser",
            "§7Click here to go",
            "§7to the item-type menu")
        .actionHandler("browser")
        .slot(14)
       );
  }

  @Override
  protected void registerActionHandlers() {
    registerActionHandler("next", ActionHandlers.openMenu(new NextMenu()));
    registerActionHandler(
        "browser",
        ActionHandlers.openMenu(new ItemTypeBrowser(ProtocolVersions.MINECRAFT_1_17)));
  }

  @Override
  public int updateTicks() {
    return 2;
  }
}