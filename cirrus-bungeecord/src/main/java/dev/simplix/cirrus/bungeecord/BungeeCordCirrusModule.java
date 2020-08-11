package dev.simplix.cirrus.bungeecord;

import de.exceptionflug.protocolize.items.ItemStack;
import dev.simplix.cirrus.bungeecord.converters.ItemModelConverter;
import dev.simplix.cirrus.bungeecord.converters.PlayerUniqueIdConverter;
import dev.simplix.core.common.aop.AbstractSimplixModule;
import dev.simplix.core.common.aop.InjectorModule;
import dev.simplix.core.common.converter.Converters;
import java.util.UUID;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import dev.simplix.cirrus.api.business.InventoryItemWrapper;
import dev.simplix.cirrus.api.business.ItemStackWrapper;
import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.bungeecord.converters.ItemStackConverter;
import dev.simplix.cirrus.bungeecord.converters.PlayerConverter;
import dev.simplix.cirrus.api.i18n.LocalizedItemStackModel;

@InjectorModule("Cirrus")
public class BungeeCordCirrusModule extends AbstractSimplixModule {

  static {
    // Players
    Converters.register(ProxiedPlayer.class, PlayerWrapper.class, new PlayerConverter());
    Converters.register(UUID.class, PlayerWrapper.class, new PlayerUniqueIdConverter());

    // Items
    Converters.register(ItemStack.class, ItemStackWrapper.class, new ItemStackConverter());
    Converters.register(LocalizedItemStackModel.class, InventoryItemWrapper.class, new ItemModelConverter());
  }

}
