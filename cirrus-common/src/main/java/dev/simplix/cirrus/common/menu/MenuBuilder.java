package dev.simplix.cirrus.common.menu;

import dev.simplix.cirrus.common.business.PlayerWrapper;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface MenuBuilder {

    <T> T build(@Nullable T prebuild, @NonNull Menu menu);

    <T> void open(@NonNull PlayerWrapper playerWrapper, @NonNull T inventoryImpl);

    @Nullable
    Menu menuByHandle(@Nullable Object handle);

    default Optional<Menu> findMenuByHandle(@Nullable Object handle) {
        return Optional.ofNullable(menuByHandle(handle));
    }

    void destroyMenusOfPlayer(@NonNull UUID uniqueId);

    Map.Entry<Menu, Long> lastBuildOfPlayer(@NonNull UUID uniqueId);

    void invalidate(@NonNull Menu menu);

}
