package dev.simplix.cirrus.bungeecord.protocolize;

import com.google.common.collect.Sets;
import de.exceptionflug.protocolize.inventory.Inventory;
import de.exceptionflug.protocolize.inventory.InventoryModule;
import de.exceptionflug.protocolize.items.ItemStack;
import dev.simplix.cirrus.api.business.InventoryItemWrapper;
import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.menu.Container;
import dev.simplix.cirrus.api.menu.Menu;
import dev.simplix.cirrus.api.menu.MenuBuilder;
import dev.simplix.cirrus.bungeecord.BungeeCordCirrusModule;
import dev.simplix.cirrus.common.menu.AbstractMenu;
import dev.simplix.core.common.Replacer;
import dev.simplix.core.common.aop.Component;
import java.util.*;
import java.util.Map.Entry;
import lombok.NonNull;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.chat.ComponentSerializer;
import org.jetbrains.annotations.Nullable;

@Component(value = BungeeCordCirrusModule.class, parent = MenuBuilder.class)
public class ProtocolizeMenuBuilder implements MenuBuilder {

  private final Map<UUID, Map.Entry<Menu, Long>> buildMap = new LinkedHashMap<>();
  private final Set<Menu> menus = Sets.newConcurrentHashSet();

  @Override
  public <T> T build(@Nullable T prebuild, @NonNull Menu menu) {
    if (!(menu instanceof AbstractMenu)) {
      throw new IllegalArgumentException("This implementation can only build cirrus menus!");
    }
    final boolean register = prebuild == null;
    if (prebuild instanceof Inventory) {
      String title = Replacer.of(menu.title()).replaceAll((Object[]) menu.replacements().get())
          .replacedMessageJoined();
      final Inventory inventory = (Inventory) prebuild;
      if (!ComponentSerializer.toString(inventory.getTitle())
          .equals(ComponentSerializer.toString(new TextComponent(title))) ||
          inventory.getType().getTypicalSize(menu.player().protocolVersion()) != menu
              .topContainer()
              .capacity()
          || inventory.getType() != menu.inventoryType()) {
        prebuild = (T) makeInv(menu);
      }
    } else {
      prebuild = (T) makeInv(menu);
    }
    final Inventory inventory = (Inventory) prebuild;
    buildContainer(inventory, menu.topContainer());
    buildContainer(inventory, menu.bottomContainer());

    buildMap.put(
        menu.player().uniqueId(),
        new AbstractMap.SimpleEntry<>(menu, System.currentTimeMillis()));
    if (register) {
      menus.add(menu);
    }
    open(menu.player(), inventory);
    return prebuild;
  }

  private void buildContainer(@NonNull Inventory inventory, @NonNull Container container) {
    for (int i = container.baseSlot(); i < container.baseSlot() + container.capacity(); i++) {
      InventoryItemWrapper item = container.itemMap().get(i);
      ItemStack currentStack = inventory.getItem(i);
      if (item == null) {
        if (currentStack != null) {
          inventory.setItem(i, ItemStack.NO_DATA);
        }
      }
      if (item != null) {
        if (currentStack == null) {
          if (item.handle() == null) {
            ProxyServer.getInstance().getLogger()
                .severe("InventoryItem's ItemStackWrapper is null @ slot " + i);
            continue;
          }
          inventory.setItem(i, item.handle());
        } else {
          if (!currentStack.equals(item.handle())) {
            inventory.setItem(i, item.handle());
          }
        }
      }
    }
  }

  private Inventory makeInv(@NonNull Menu menu) {
    return new Inventory(menu.inventoryType(), new TextComponent(Replacer.of(menu.title())
        .replaceAll((Object[]) menu.replacements().get()).replacedMessageJoined()));
  }

  @Override
  public <T> void open(
      PlayerWrapper playerWrapper, T inventoryImpl) {
    InventoryModule
        .sendInventory(playerWrapper.handle(), (Inventory) inventoryImpl);
  }

  @Override
  @Nullable
  public Menu menuByHandle(Object handle) {
    if (handle == null) {
      return null;
    }
    for (final Menu menu : menus) {
      if (menu.equals(handle)) {
        return menu;
      }
    }
    return null;
  }

  @Override
  public void destroyMenusOfPlayer(@NonNull UUID uniqueId) {
    menus.removeIf(
        wrapper -> ((ProxiedPlayer) wrapper.player().handle()).getUniqueId().equals(uniqueId));
    buildMap.remove(uniqueId);
  }

  @Override
  public Entry<Menu, Long> lastBuildOfPlayer(@NonNull UUID uniqueId) {
    return buildMap.get(uniqueId);
  }

  @Override
  public void invalidate(@NonNull Menu menu) {
    menus.remove(menu);
  }

}
