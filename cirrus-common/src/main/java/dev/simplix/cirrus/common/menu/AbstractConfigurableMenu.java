package dev.simplix.cirrus.common.menu;

import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.model.ItemStackModel;
import dev.simplix.cirrus.api.model.MenuConfiguration;
import java.util.Locale;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public abstract class AbstractConfigurableMenu<T extends MenuConfiguration> extends AbstractMenu {

  private final T configuration;

  public AbstractConfigurableMenu(
      @NonNull PlayerWrapper player,
      @NonNull T configuration,
      @NonNull Locale locale) {
    super(player, configuration.type(), locale);
    title(configuration.title().translated(locale));
    this.configuration = configuration;
    applyItems();
  }

  @Override
  protected void updateReplacements() {
    super.updateReplacements();
    topContainer().itemMap().clear();
    bottomContainer().itemMap().clear();
    topContainer().reservedSlots().clear();
    bottomContainer().reservedSlots().clear();
    applyItems();
  }

  private void applyItems() {
    for (int slot : configuration.reservedSlots()) {
      if (slot > topContainer().capacity() - 1) {
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
