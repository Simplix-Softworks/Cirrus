package dev.simplix.cirrus.bungeecord.listeners;

import dev.simplix.cirrus.common.Cirrus;
import dev.simplix.cirrus.common.menu.MenuBuilder;
import lombok.NonNull;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(@NonNull PlayerDisconnectEvent event) {
        Cirrus.getService(MenuBuilder.class).destroyMenusOfPlayer(event.getPlayer().getUniqueId());
    }

}
