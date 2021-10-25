package dev.simplix.cirrus.bungeecord.converters;

import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.converter.Converter;
import dev.simplix.cirrus.bungeecord.BungeeCordPlayerWrapper;
import lombok.NonNull;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public final class PlayerConverter implements Converter<ProxiedPlayer, PlayerWrapper> {

    @Override
    public PlayerWrapper convert(@NonNull ProxiedPlayer proxiedPlayer) {
        return new BungeeCordPlayerWrapper(proxiedPlayer);
    }

}
