package dev.simplix.cirrus.bungeecord;

import de.exceptionflug.protocolize.api.util.ReflectionUtil;
import de.exceptionflug.protocolize.inventory.InventoryModule;
import java.util.UUID;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import dev.simplix.cirrus.api.business.PlayerWrapper;

public class BungeeCordPlayerWrapper implements PlayerWrapper {

  private final ProxiedPlayer handle;

  public BungeeCordPlayerWrapper(ProxiedPlayer handle) {
    this.handle = handle;
  }

  public UUID uniqueId() {
    return handle.getUniqueId();
  }

  public String name() {
    return handle.getName();
  }

    @Override
    public int protocolVersion() {
        return ReflectionUtil.getProtocolVersion(handle);
    }

    public void sendMessage(String s) {
    handle.sendMessage(s);
  }

  public void closeInventory() {
    InventoryModule.closeAllInventories(handle);
  }

  public boolean hasPermission(String s) {
    return handle.hasPermission(s);
  }

  public <T> T handle() {
    return (T) handle;
  }

}
