package dev.simplix.cirrus.api.business;

import dev.simplix.cirrus.api.model.MenuConfiguration;
import lombok.NonNull;

public interface ConfigurationFactory {

  default MenuConfiguration loadFile(@NonNull String file) {
    return loadFile(file, MenuConfiguration.class);
  }

  default MenuConfiguration loadResource(@NonNull String resourcePath, @NonNull Class<?> caller) {
    return loadResource(resourcePath, caller, MenuConfiguration.class);
  }

  <T extends MenuConfiguration> T loadFile(@NonNull String file, @NonNull Class<T> type);

  <T extends MenuConfiguration> T loadResource(
      @NonNull String resourcePath,
      @NonNull Class<?> caller,
      @NonNull Class<T> type);

}
