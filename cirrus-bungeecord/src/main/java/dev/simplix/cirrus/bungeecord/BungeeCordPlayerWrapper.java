package dev.simplix.cirrus.bungeecord;

import dev.simplix.cirrus.api.business.PlayerWrapper;
import java.util.UUID;

import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
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
    return handle.getUniqueId();
  }

  @Override
  public String name() {
    return handle.getName();
  }

  @Override
  public int protocolVersion() {
    return protocolizePlayer.protocolVersion();
  }

  @Override
  public void sendMessage(@NonNull String msg) {
    handle.sendMessage(msg);
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
