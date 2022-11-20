package dev.simplix.cirrus.bungee.wrapper;

import dev.simplix.cirrus.Utils;
import dev.simplix.cirrus.model.SimpleSound;
import dev.simplix.cirrus.player.CirrusPlayerWrapper;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.util.ProtocolVersions;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

@RequiredArgsConstructor
public class BungeePlayerWrapper implements CirrusPlayerWrapper {

  private final ProxiedPlayer handle;

  @Override
  public UUID uuid() {
    return handle.getUniqueId();
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T handle() {
    return (T) handle;
  }

  @Override
  public void sendMessage(String message) {
    handle.sendMessage(Utils.colorize(message));
  }

  @Override
  public void play(SimpleSound sound) {
    Protocolize.playerProvider().player(uuid()).playSound(sound.sound(), sound.soundCategory(), sound.volume(), sound.pitch());
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
}