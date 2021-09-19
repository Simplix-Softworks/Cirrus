package dev.simplix.cirrus.velocity;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.simplix.cirrus.api.business.InventoryItemWrapper;
import dev.simplix.cirrus.api.business.ItemStackWrapper;
import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.converter.Converters;
import dev.simplix.cirrus.api.i18n.LocalizedItemStackModel;
import dev.simplix.cirrus.api.menu.MenuBuilder;
import dev.simplix.cirrus.common.Cirrus;
import dev.simplix.cirrus.velocity.converters.ItemModelConverter;
import dev.simplix.cirrus.velocity.converters.ItemStackConverter;
import dev.simplix.cirrus.velocity.converters.PlayerConverter;
import dev.simplix.cirrus.velocity.converters.PlayerUniqueIdConverter;
import dev.simplix.cirrus.velocity.listener.QuitListener;
import dev.simplix.cirrus.velocity.protocolize.ProtocolizeMenuBuilder;
import dev.simplix.protocolize.api.item.ItemStack;

import java.util.UUID;

/**
 * Date: 18.09.2021
 *
 * @author Exceptionflug
 */
public class CirrusVelocity {

    private static ProxyServer proxyServer;

    public static void init(ProxyServer proxyServer, Object plugin) {
        if (CirrusVelocity.proxyServer != null) {
            return;
        }
        CirrusVelocity.proxyServer = proxyServer;
        Cirrus.registerService(MenuBuilder.class, new ProtocolizeMenuBuilder());
        proxyServer.getEventManager().register(plugin, new QuitListener());

        // Players
        Converters.register(Player.class, PlayerWrapper.class, new PlayerConverter());
        Converters.register(UUID.class, PlayerWrapper.class, new PlayerUniqueIdConverter(proxyServer));

        // Items
        Converters.register(ItemStack.class, ItemStackWrapper.class, new ItemStackConverter());
        Converters.register(LocalizedItemStackModel.class, InventoryItemWrapper.class, new ItemModelConverter());
    }

}
