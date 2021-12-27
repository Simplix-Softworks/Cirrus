package dev.simplix.cirrus.common.menus;

import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.common.configuration.MenuConfiguration;
import dev.simplix.cirrus.common.handler.ActionHandler;
import dev.simplix.cirrus.common.menu.AbstractConfigurableMenu;
import java.util.Locale;
import java.util.Map;
import lombok.NonNull;

public class SimpleMenu extends AbstractConfigurableMenu<MenuConfiguration> {
  public SimpleMenu(@NonNull PlayerWrapper player,
                    @NonNull MenuConfiguration configuration,
                    @NonNull Locale locale) {
    super(player, configuration, locale);
  }

  public SimpleMenu(@NonNull PlayerWrapper player,
                    @NonNull MenuConfiguration configuration,
                    @NonNull Locale locale,
                    @NonNull Map<String, ActionHandler> actionHandlerMap) {
    super(player, configuration, locale, actionHandlerMap);
  }
}
