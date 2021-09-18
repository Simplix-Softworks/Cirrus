package dev.simplix.cirrus.bungeecord;

import dev.simplix.cirrus.api.converter.Converters;
import dev.simplix.cirrus.api.menu.MenuBuilder;
import dev.simplix.cirrus.bungeecord.converters.ItemModelConverter;
import dev.simplix.cirrus.bungeecord.converters.PlayerUniqueIdConverter;
import java.util.UUID;

import dev.simplix.cirrus.bungeecord.listeners.QuitListener;
import dev.simplix.cirrus.bungeecord.protocolize.ProtocolizeMenuBuilder;
import dev.simplix.cirrus.common.Cirrus;
import dev.simplix.protocolize.api.item.ItemStack;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import dev.simplix.cirrus.api.business.InventoryItemWrapper;
import dev.simplix.cirrus.api.business.ItemStackWrapper;
import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.bungeecord.converters.ItemStackConverter;
import dev.simplix.cirrus.bungeecord.converters.PlayerConverter;
import dev.simplix.cirrus.api.i18n.LocalizedItemStackModel;
import net.md_5.bungee.api.plugin.Plugin;

public class CirrusBungeeCord {

  private static Plugin plugin;

  static {
    // Players
    Converters.register(ProxiedPlayer.class, PlayerWrapper.class, new PlayerConverter());
    Converters.register(UUID.class, PlayerWrapper.class, new PlayerUniqueIdConverter());

    // Items
    Converters.register(ItemStack.class, ItemStackWrapper.class, new ItemStackConverter());
    Converters.register(LocalizedItemStackModel.class, InventoryItemWrapper.class, new ItemModelConverter());
  }

  public static void init(Plugin plugin) {
    if (CirrusBungeeCord.plugin != null) {
      return;
    }
    CirrusBungeeCord.plugin = plugin;
    Cirrus.registerService(MenuBuilder.class, new ProtocolizeMenuBuilder());
    ProxyServer.getInstance().getPluginManager().registerListener(plugin, new QuitListener());
  }

  public static Plugin plugin() {
    if (plugin == null) {
      throw new IllegalStateException("Cirrus is not initialized. Please call CirrusBungeeCord#init during onEnable.");
    }
    return plugin;
  }

}
