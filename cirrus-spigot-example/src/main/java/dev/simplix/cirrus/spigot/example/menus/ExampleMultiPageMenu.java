package dev.simplix.cirrus.spigot.example.menus;

import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.menu.CallResult;
import dev.simplix.cirrus.api.model.MultiPageMenuConfiguration;
import dev.simplix.cirrus.common.prefabs.menu.MultiPageMenu;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.item.ItemStack;
import dev.simplix.protocolize.api.providers.MappingProvider;
import dev.simplix.protocolize.data.ItemType;

import java.util.Collections;
import java.util.Locale;

public class ExampleMultiPageMenu extends MultiPageMenu {

  private final MappingProvider mappingProvider = Protocolize.mappingProvider();

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
      if(mappingProvider.mapping(type, player().protocolVersion()) != null) {
        add(wrapItemStack(new ItemStack(type)), "test", Collections.emptyList());
      }
    }
  }

}
