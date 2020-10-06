package dev.simplix.cirrus.bungeecord;

import de.exceptionflug.protocolize.api.util.ReflectionUtil;
import de.exceptionflug.protocolize.inventory.InventoryModule;
import dev.simplix.cirrus.api.business.PlayerWrapper;
import java.util.UUID;
import lombok.NonNull;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeCordPlayerWrapper implements PlayerWrapper {

  private final ProxiedPlayer handle;

  public BungeeCordPlayerWrapper(@NonNull ProxiedPlayer handle) {
    this.handle = handle;
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
    return ReflectionUtil.getProtocolVersion(handle);
  }

  @Override
  public void sendMessage(@NonNull String msg) {
    handle.sendMessage(msg);
  }

  @Override
  public void closeInventory() {
    InventoryModule.closeAllInventories(handle);
  }

  @Override
  public boolean hasPermission(@NonNull String permission) {
    return handle.hasPermission(permission);
  }

  @Override
  public <T> T handle() {
    return (T) handle;
  }

}
