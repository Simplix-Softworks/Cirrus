package dev.simplix.cirrus.velocity;

import com.velocitypowered.api.proxy.Player;
import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import java.util.UUID;
import lombok.NonNull;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

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
        return this.handle.getUniqueId();
    }

    @Override
    public String name() {
        return this.handle.getUsername();
    }

    @Override
    public int protocolVersion() {
        return this.protocolizePlayer.protocolVersion();
    }

    @Override
    public void sendMessage(@NonNull String msg) {
        this.handle.sendMessage(this.legacyComponentSerializer.deserialize(msg));
    }

    @Override
    public void closeInventory() {
        this.protocolizePlayer.closeInventory();
    }

    @Override
    public boolean hasPermission(@NonNull String permission) {
        return this.handle.hasPermission(permission);
    }

    @Override
    public <T> T handle() {
        return (T) this.handle;
    }

    public ProtocolizePlayer protocolizePlayer() {
        return this.protocolizePlayer;
    }
}
