package dev.simplix.cirrus.bungeecord.converters;

import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.converter.Converter;
import dev.simplix.cirrus.bungeecord.BungeeCordPlayerWrapper;
import java.util.UUID;
import lombok.NonNull;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerUniqueIdConverter implements Converter<UUID, PlayerWrapper> {

  @Override
  public PlayerWrapper convert(@NonNull UUID uuid) {
    ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
    if(player == null)  {
      throw new IllegalArgumentException("Player "+uuid+" is offline");
    }
    return new BungeeCordPlayerWrapper(player);
  }

}
