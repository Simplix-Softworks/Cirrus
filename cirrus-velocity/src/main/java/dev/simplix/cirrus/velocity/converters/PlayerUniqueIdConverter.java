package dev.simplix.cirrus.velocity.converters;

import com.velocitypowered.api.proxy.ProxyServer;
import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.common.converter.Converter;
import dev.simplix.cirrus.velocity.VelocityPlayerWrapper;
import java.util.UUID;
import lombok.NonNull;

public class PlayerUniqueIdConverter implements Converter<UUID, PlayerWrapper> {

    private final ProxyServer proxyServer;

    public PlayerUniqueIdConverter(ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
    }

    @Override
    public PlayerWrapper convert(@NonNull UUID uuid) {
        return new VelocityPlayerWrapper(this.proxyServer.getPlayer(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Player " + uuid + " is offline")));
    }

}
