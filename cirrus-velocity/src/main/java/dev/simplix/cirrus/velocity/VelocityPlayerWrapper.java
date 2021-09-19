package dev.simplix.cirrus.velocity;

import com.velocitypowered.api.proxy.Player;
import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import lombok.NonNull;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.UUID;

public class VelocityPlayerWrapper implements PlayerWrapper {

    private final LegacyComponentSerializer legacyComponentSerializer = LegacyComponentSerializer.legacySection();
    private final ProtocolizePlayer protocolizePlayer;
    private final Player handle;

    public VelocityPlayerWrapper(@NonNull Player handle) {
        this.handle = handle;
        this.protocolizePlayer = Protocolize.playerProvider().player(handle.getUniqueId());
    }

    @Override
    public UUID uniqueId() {
        return handle.getUniqueId();
    }

    @Override
    public String name() {
        return handle.getUsername();
    }

    @Override
    public int protocolVersion() {
        return protocolizePlayer.protocolVersion();
    }

    @Override
    public void sendMessage(@NonNull String msg) {
        handle.sendMessage(legacyComponentSerializer.deserialize(msg));
    }

    @Override
    public void closeInventory() {
        protocolizePlayer.closeInventory();
    }

    @Override
    public boolean hasPermission(@NonNull String permission) {
        return handle.hasPermission(permission);
    }

    @Override
    public <T> T handle() {
        return (T) handle;
    }

    public ProtocolizePlayer protocolizePlayer() {
        return protocolizePlayer;
    }
}
