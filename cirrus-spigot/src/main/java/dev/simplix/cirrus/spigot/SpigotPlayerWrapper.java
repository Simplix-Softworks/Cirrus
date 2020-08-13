package dev.simplix.cirrus.spigot;

import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.spigot.util.ProtocolVersionUtil;
import java.util.UUID;
import org.bukkit.entity.Player;

public class SpigotPlayerWrapper implements PlayerWrapper {

  private final Player handle;

  public SpigotPlayerWrapper(Player handle) {
    this.handle = handle;
  }

  public void sendMessage(String msg) {
    handle.sendMessage(msg);
  }

  public void closeInventory() {
    handle.closeInventory();
  }

  public boolean hasPermission(String permission) {
    return handle.hasPermission(permission);
  }

  public UUID uniqueId() {
    return handle.getUniqueId();
  }

  public String name() {
    return handle.getName();
  }

  public int protocolVersion() {
    return ProtocolVersionUtil.protocolVersion();
  }

  public <T> T handle() {
    return (T) handle;
  }
}
