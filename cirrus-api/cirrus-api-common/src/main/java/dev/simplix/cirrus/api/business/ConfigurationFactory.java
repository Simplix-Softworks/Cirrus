package dev.simplix.cirrus.api.business;

import dev.simplix.cirrus.api.model.MenuConfiguration;

public interface ConfigurationFactory {

  default MenuConfiguration loadFile(String file) {
     return loadFile(file, MenuConfiguration.class);
  }

  default MenuConfiguration loadResource(String resourcePath) {
    return loadResource(resourcePath, MenuConfiguration.class);
  }

  <T extends MenuConfiguration> T loadFile(String file, Class<T> type);

  <T extends MenuConfiguration> T loadResource(String resourcePath, Class<T> type);

}
