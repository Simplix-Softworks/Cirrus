package dev.simplix.cirrus.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import dev.simplix.cirrus.api.menu.MenuBuilder;
import dev.simplix.cirrus.common.Cirrus;

/**
 * Date: 18.09.2021
 *
 * @author Exceptionflug
 */
public class QuitListener {

    private final MenuBuilder menuBuilder = Cirrus.getService(MenuBuilder.class);

    @Subscribe
    public void onQuit(DisconnectEvent event) {
        menuBuilder.destroyMenusOfPlayer(event.getPlayer().getUniqueId());
    }

}
