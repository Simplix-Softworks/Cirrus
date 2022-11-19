package dev.simplix.cirrus.bungee.listeners;

import dev.simplix.cirrus.menu.Menus;
import lombok.NonNull;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class QuitListener implements Listener {

  @EventHandler
  public void onQuit(@NonNull PlayerDisconnectEvent event) {
    Menus.remove(event.getPlayer().getUniqueId());
  }

}
