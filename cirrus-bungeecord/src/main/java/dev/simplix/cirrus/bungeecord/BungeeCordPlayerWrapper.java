package dev.simplix.cirrus.bungeecord;

import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import java.util.UUID;
import lombok.NonNull;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeCordPlayerWrapper implements PlayerWrapper {

    private final ProtocolizePlayer protocolizePlayer;
    private final ProxiedPlayer handle;

    public BungeeCordPlayerWrapper(@NonNull ProxiedPlayer handle) {
        this.handle = handle;
        this.protocolizePlayer = Protocolize.playerProvider().player(handle.getUniqueId());
    }

    @Override
    public UUID uniqueId() {
        return this.handle.getUniqueId();
    }

    @Override
    public String name() {
        return this.handle.getName();
    }

    @Override
    public int protocolVersion() {
        return this.protocolizePlayer.protocolVersion();
    }

    @Override
    public void sendMessage(@NonNull String msg) {
        this.handle.sendMessage(msg);
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
