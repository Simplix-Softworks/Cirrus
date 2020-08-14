package dev.simplix.cirrus.bungeecord.converters;

import dev.simplix.core.common.converter.Converter;
import java.util.UUID;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.bungeecord.BungeeCordPlayerWrapper;

public class PlayerUniqueIdConverter implements Converter<UUID, PlayerWrapper> {

  public PlayerWrapper convert(UUID uuid) {
    ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
    if(player == null)  {
      throw new IllegalArgumentException("Player "+uuid+" is offline");
    }
    return new BungeeCordPlayerWrapper(player);
  }

}
