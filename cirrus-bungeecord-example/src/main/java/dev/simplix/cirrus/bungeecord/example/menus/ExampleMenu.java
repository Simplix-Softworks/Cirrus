package dev.simplix.cirrus.bungeecord.example.menus;

import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.menu.CallResult;
import dev.simplix.cirrus.api.model.MenuConfiguration;
import dev.simplix.cirrus.common.menu.AbstractConfigurableMenu;
import java.util.Locale;

public class ExampleMenu extends AbstractConfigurableMenu<MenuConfiguration> {

  public ExampleMenu(PlayerWrapper player, MenuConfiguration configuration) {
    super(player, configuration, Locale.ENGLISH);

    registerActionHandler("tnt", click -> {
      topContainer().itemMap().remove(click.slot());
      title("Hello, {viewer}");
      build();
      player().sendMessage("It simply works :)");
      return CallResult.DENY_GRABBING;
    });

    set(configuration.businessItems().get("test"));
  }

}
