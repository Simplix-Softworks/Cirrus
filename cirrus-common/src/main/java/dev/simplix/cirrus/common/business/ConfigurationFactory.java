package dev.simplix.cirrus.common.business;

import dev.simplix.cirrus.common.configuration.MenuConfiguration;
import dev.simplix.cirrus.common.configuration.MultiPageMenuConfiguration;
import dev.simplix.cirrus.common.configuration.impl.SimpleMenuConfiguration;
import dev.simplix.cirrus.common.configuration.impl.SimpleMultiPageMenuConfiguration;
import lombok.NonNull;

public interface ConfigurationFactory {

    default MenuConfiguration loadFile(@NonNull String file) {
        return loadFile(file, SimpleMenuConfiguration.class);
    }

    default MenuConfiguration loadResource(@NonNull String resourcePath, @NonNull Class<?> caller) {
        return loadResource(resourcePath, caller, SimpleMenuConfiguration.class);
    }

    default MultiPageMenuConfiguration loadFileForMultiPageMenu(@NonNull String file) {
        return loadFile(file, SimpleMultiPageMenuConfiguration.class);
    }

    default MultiPageMenuConfiguration loadResourcesForMultiPageMenu(
            @NonNull String resourcePath,
            @NonNull Class<?> caller) {
        return loadResource(resourcePath, caller, SimpleMultiPageMenuConfiguration.class);
    }

    <T extends MenuConfiguration> T loadFile(@NonNull String file, @NonNull Class<T> type);

    <T extends MenuConfiguration> T loadResource(
            @NonNull String resourcePath,
            @NonNull Class<?> caller,
            @NonNull Class<T> type);

}
