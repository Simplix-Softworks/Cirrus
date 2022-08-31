package dev.simplix.cirrus.spigot.converters;

import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.common.converter.Converter;
import dev.simplix.cirrus.spigot.SpigotPlayerWrapper;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class PlayerConverter implements Converter<Player, PlayerWrapper> {

    @Override
    public PlayerWrapper convert(@NonNull @NotNull Player proxiedPlayer) {
        return new SpigotPlayerWrapper(proxiedPlayer);
    }

}
