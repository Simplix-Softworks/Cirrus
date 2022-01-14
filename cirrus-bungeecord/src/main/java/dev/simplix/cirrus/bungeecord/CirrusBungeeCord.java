package dev.simplix.cirrus.bungeecord;

import dev.simplix.cirrus.bungeecord.converters.ItemModelConverter;
import dev.simplix.cirrus.bungeecord.converters.ItemStackConverter;
import dev.simplix.cirrus.bungeecord.converters.PlayerConverter;
import dev.simplix.cirrus.bungeecord.converters.PlayerUniqueIdConverter;
import dev.simplix.cirrus.bungeecord.listeners.QuitListener;
import dev.simplix.cirrus.bungeecord.protocolize.ProtocolizeMenuBuilder;
import dev.simplix.cirrus.common.Cirrus;
import dev.simplix.cirrus.common.business.InventoryMenuItemWrapper;
import dev.simplix.cirrus.common.business.MenuItemWrapper;
import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.common.converter.Converters;
import dev.simplix.cirrus.common.item.MenuItem;
import dev.simplix.cirrus.common.menu.MenuBuilder;
import dev.simplix.protocolize.api.item.ItemStack;
import java.util.UUID;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class CirrusBungeeCord {

    private static Plugin plugin;

    static {
        // Players
        Converters.register(ProxiedPlayer.class, PlayerWrapper.class, new PlayerConverter());
        Converters.register(UUID.class, PlayerWrapper.class, new PlayerUniqueIdConverter());

        // Items
        Converters.register(ItemStack.class, MenuItemWrapper.class, new ItemStackConverter());
        Converters.register(MenuItem.class, InventoryMenuItemWrapper.class, new ItemModelConverter());
    }

    public static void init(Plugin plugin) {
        if (CirrusBungeeCord.plugin!=null) {
            return;
        }
        CirrusBungeeCord.plugin = plugin;
        Cirrus.registerService(MenuBuilder.class, new ProtocolizeMenuBuilder());
        ProxyServer.getInstance().getPluginManager().registerListener(plugin, new QuitListener());
    }

    public static Plugin plugin() {
        if (plugin==null) {
            throw new IllegalStateException("Cirrus is not initialized. Please call CirrusBungeeCord#init during onEnable.");
        }
        return plugin;
    }

}
