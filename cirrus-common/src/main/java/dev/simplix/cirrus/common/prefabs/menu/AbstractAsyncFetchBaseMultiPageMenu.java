package dev.simplix.cirrus.common.prefabs.menu;

import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.model.MultiPageMenuConfiguration;
import java.util.Locale;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
public abstract class AbstractAsyncFetchBaseMultiPageMenu<T> extends MultiPageMenu {

  private boolean fetching;

  public AbstractAsyncFetchBaseMultiPageMenu(
      @NonNull PlayerWrapper player,
      @NonNull MultiPageMenuConfiguration configuration,
      @NonNull Locale locale) {
    super(player, configuration, locale);
  }

}
