package dev.simplix.cirrus.bungeecord.converters;

import dev.simplix.core.common.converter.Converter;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.bungeecord.BungeeCordPlayerWrapper;

public final class PlayerConverter implements Converter<ProxiedPlayer, PlayerWrapper> {

  public PlayerWrapper convert(ProxiedPlayer proxiedPlayer) {
    return new BungeeCordPlayerWrapper(proxiedPlayer);
  }

}
