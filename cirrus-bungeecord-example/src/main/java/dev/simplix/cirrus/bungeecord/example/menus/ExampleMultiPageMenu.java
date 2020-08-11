package dev.simplix.cirrus.bungeecord.example.menus;

import de.exceptionflug.protocolize.api.util.ProtocolVersions;
import de.exceptionflug.protocolize.items.ItemStack;
import de.exceptionflug.protocolize.items.ItemType;
import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.menu.CallResult;
import dev.simplix.cirrus.api.model.MultiPageMenuConfiguration;
import dev.simplix.cirrus.common.prefabs.menu.MultiPageMenu;
import java.util.Collections;
import java.util.Locale;

public class ExampleMultiPageMenu extends MultiPageMenu {

  public ExampleMultiPageMenu(
      PlayerWrapper player,
      MultiPageMenuConfiguration configuration) {
    super(player, configuration, Locale.ENGLISH);
    registerMyActionHandlers();
    addItems();
  }

  private void registerMyActionHandlers() {
    registerActionHandler("test", click -> {
      player().sendMessage("This is "+click.clickedItem().type().name());
      return CallResult.DENY_GRABBING;
    });
  }

  private void addItems() {
    for(ItemType type : ItemType.values()) {
      if(type.getApplicableMapping(player().protocolVersion()) != null) {
        add(wrapItemStack(new ItemStack(type)), "test", Collections.emptyList());
      }
    }
  }

}
