package dev.simplix.cirrus.spigot;

import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.spigot.util.ProtocolVersionUtil;
import java.util.UUID;
import lombok.NonNull;
import org.bukkit.entity.Player;

public class SpigotPlayerWrapper implements PlayerWrapper {

    private final Player handle;

    public SpigotPlayerWrapper(@NonNull Player handle) {
        this.handle = handle;
    }

    @Override
    public void sendMessage(@NonNull String msg) {
        this.handle.sendMessage(msg);
    }

    @Override
    public void closeInventory() {
        this.handle.closeInventory();
    }

    @Override
    public boolean hasPermission(@NonNull String permission) {
        return this.handle.hasPermission(permission);
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

    @Override
    public <T> T handle() {
        return (T) this.handle;
    }
  @Override
  public int protocolVersion() {
    return Math.min(ProtocolVersionUtil.serverProtocolVersion(), ProtocolVersions.MINECRAFT_1_17_1);
  }
}
