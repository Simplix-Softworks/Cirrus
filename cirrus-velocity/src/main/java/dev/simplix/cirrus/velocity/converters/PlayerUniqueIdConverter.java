package dev.simplix.cirrus.velocity.converters;

import com.velocitypowered.api.proxy.ProxyServer;
import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.converter.Converter;
import dev.simplix.cirrus.velocity.VelocityPlayerWrapper;
import lombok.NonNull;

import java.util.UUID;

public class PlayerUniqueIdConverter implements Converter<UUID, PlayerWrapper> {

    private final ProxyServer proxyServer;

    public PlayerUniqueIdConverter(ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Override
    public PlayerWrapper convert(@NonNull UUID uuid) {
        return new VelocityPlayerWrapper(proxyServer.getPlayer(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Player " + uuid + " is offline")));
    }

}
