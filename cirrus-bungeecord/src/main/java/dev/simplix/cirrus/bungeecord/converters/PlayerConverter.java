package dev.simplix.cirrus.bungeecord.converters;

import dev.simplix.cirrus.bungeecord.BungeeCordPlayerWrapper;
import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.common.converter.Converter;
import lombok.NonNull;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.NotNull;

public final class PlayerConverter implements Converter<ProxiedPlayer, PlayerWrapper> {

    @Override
    public PlayerWrapper convert(@NonNull @NotNull ProxiedPlayer proxiedPlayer) {
        return new BungeeCordPlayerWrapper(proxiedPlayer);
    }

}
