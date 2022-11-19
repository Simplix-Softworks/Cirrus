package dev.simplix.cirrus.velocity.wrapper;

import com.velocitypowered.api.proxy.Player;
import dev.simplix.cirrus.Utils;
import dev.simplix.cirrus.player.CirrusPlayerWrapper;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.util.ProtocolVersions;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

@RequiredArgsConstructor
public class VelocityPlayerWrapper implements CirrusPlayerWrapper {
  private final Player player;

  @Override
  public UUID uuid() {
    return player.getUniqueId();
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T handle() {
    return (T) player;
  }

  @Override
  public int protocolVersion() {
    int i;
    try {
      i = Protocolize.playerProvider().player(uuid()).protocolVersion();
    } catch (Throwable throwable) {
      i = ProtocolVersions.MINECRAFT_LATEST;
    }
    return i;
  }

  @Override
  public void sendMessage(String message) {
    player.sendMessage(LegacyComponentSerializer.legacy('§').deserialize(Utils.colorize(message)));
  }
}
