package dev.simplix.cirrus.spigot;

import dev.simplix.cirrus.api.business.PlayerWrapper;
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
    handle.sendMessage(msg);
  }

  @Override
  public void closeInventory() {
    handle.closeInventory();
  }

  @Override
  public boolean hasPermission(@NonNull String permission) {
    return handle.hasPermission(permission);
  }

  @Override
  public UUID uniqueId() {
    return handle.getUniqueId();
  }

  @Override
  public String name() {
    return handle.getName();
  }

  @Override
  public int protocolVersion() {
    return ProtocolVersionUtil.serverProtocolVersion();
  }

  @Override
  public <T> T handle() {
    return (T) handle;
  }
}
