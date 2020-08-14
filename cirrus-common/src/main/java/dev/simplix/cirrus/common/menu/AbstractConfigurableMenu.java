package dev.simplix.cirrus.common.menu;

import java.util.Locale;
import lombok.Getter;
import lombok.experimental.Accessors;
import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.model.ItemStackModel;
import dev.simplix.cirrus.api.model.MenuConfiguration;
import dev.simplix.cirrus.api.i18n.LocalizedItemStackModel;
import dev.simplix.cirrus.api.i18n.Localizer;

@Getter
@Accessors(fluent = true)
public abstract class AbstractConfigurableMenu<T extends MenuConfiguration> extends AbstractMenu {

  private final T configuration;

  public AbstractConfigurableMenu(
      PlayerWrapper player,
      T configuration,
      Locale locale) {
    super(player, configuration.type(), locale);
    title(configuration.title().translated(locale));
    this.configuration = configuration;
    applyItems();
  }

  private void applyItems() {
    for(int slot : configuration.reservedSlots()) {
      if(slot > topContainer().capacity()-1) {
        bottomContainer().reservedSlots().add(slot);
      } else {
        topContainer().reservedSlots().add(slot);
      }
    }

    // Set placeholders
    set(configuration.placeholderItem());

    // Set other items
    for (ItemStackModel model : configuration.items()) {
      set(model);
    }
  }

}
