package dev.simplix.cirrus.menus.example;

import dev.simplix.cirrus.actionhandler.ActionHandlers;
import dev.simplix.cirrus.item.Items;
import dev.simplix.cirrus.menus.SimpleMenu;
import dev.simplix.cirrus.model.SimpleSound;
import dev.simplix.protocolize.api.chat.ChatElement;
import dev.simplix.protocolize.api.util.ProtocolVersions;
import dev.simplix.protocolize.data.ItemType;
import dev.simplix.protocolize.data.Sound;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SelectMenu extends SimpleMenu {

  public SelectMenu() {
    title("select item");

    set(Items.withSpectrumEffect(
            ItemType.STONE, "Next Menu",
            ChatElement.ofLegacyText("§7Click here to go"),
            ChatElement.ofLegacyText("§7to the next menu"))
        .slot(12)
        .actionHandler("next")
       );

    set(Items.withSpectrumEffect(
            ItemType.STONE,
            "Item Type Browser",
            ChatElement.ofLegacyText("§7Click here to go"),
            ChatElement.ofLegacyText("§7to the item-type menu"))
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