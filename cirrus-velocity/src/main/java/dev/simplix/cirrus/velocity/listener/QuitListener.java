package dev.simplix.cirrus.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import dev.simplix.cirrus.menu.Menus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuitListener {
  @Subscribe
  public void onQuit(DisconnectEvent event) {
    Menus.remove(event.getPlayer().getUniqueId());
  }

}
