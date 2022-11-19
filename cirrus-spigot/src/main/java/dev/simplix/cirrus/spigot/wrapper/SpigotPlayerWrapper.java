package dev.simplix.cirrus.spigot.wrapper;

import dev.simplix.cirrus.Utils;
import dev.simplix.cirrus.player.CirrusPlayerWrapper;
import dev.simplix.cirrus.spigot.util.ProtocolVersionUtil;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class SpigotPlayerWrapper implements CirrusPlayerWrapper {

  private final Player handle;

  @Override
  public UUID uuid() {
    return handle.getUniqueId();
  }

  @Override
  public <T> T handle() {
    return (T) handle;
  }

  @Override
  public int protocolVersion() {
    return ProtocolVersionUtil.serverProtocolVersion();
  }

  @Override
  public void sendMessage(String message) {
    handle.sendMessage(Utils.colorize(message));
  }
}