package dev.simplix.cirrus.spigot.menubuilder;

import de.exceptionflug.protocolize.api.util.ProtocolVersions;
import dev.simplix.cirrus.api.business.InventoryItemWrapper;
import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.menu.Container;
import dev.simplix.cirrus.api.menu.Menu;
import dev.simplix.cirrus.api.menu.MenuBuilder;
import dev.simplix.cirrus.common.CirrusSimplixModule;
import dev.simplix.cirrus.common.prefabs.menu.MultiPageMenu;
import dev.simplix.cirrus.spigot.modern.ModernInventoryView;
import dev.simplix.cirrus.spigot.util.ProtocolVersionUtil;
import dev.simplix.cirrus.spigot.util.ReflectionUtil;
import dev.simplix.core.common.aop.Component;
import dev.simplix.core.common.converter.Converters;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.Map.Entry;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

@Component(value = CirrusSimplixModule.class, parent = MenuBuilder.class)
@Slf4j
public final class SpigotMenuBuilder implements MenuBuilder {

  private final Map<UUID, Map.Entry<Menu, Long>> buildMap = new LinkedHashMap<>();
  private final List<Menu> menus = new LinkedList<>();

  @Override
  public <T> T build(T prebuild, Menu menu) {
    boolean reopen = false;
    boolean register = prebuild == null;
    if (prebuild instanceof InventoryView) {
      InventoryView inventoryView = (InventoryView) prebuild;
      if (!inventoryView.getTitle().equals(menu.title())
          || inventoryView.getTopInventory().getSize() != menu.inventoryType()
          .getTypicalSize(ProtocolVersionUtil.serverProtocolVersion())
          || inventoryView.getTopInventory().getType() != Converters
          .convert(menu.inventoryType(), org.bukkit.event.inventory.InventoryType.class)) {
        prebuild = (T) makeView(menu);
        reopen = true;
      }
    } else {
      prebuild = (T) makeView(menu);
    }
    InventoryView view = (InventoryView) prebuild;
    buildContainer(view.getTopInventory(), menu.topContainer(), false);
    buildContainer(view.getBottomInventory(), menu.bottomContainer(), true);
    buildMap.put(
        menu.player().uniqueId(),
        new AbstractMap.SimpleEntry<>(menu, System.currentTimeMillis()));
    if (register) {
      menus.add(menu);
    }
    if (reopen || menu instanceof MultiPageMenu) {
      open(menu.player(), prebuild);
    }
    return prebuild;
  }

  private void buildContainer(Inventory inventory, Container container, boolean bottom) {
    for (int i = 0; i < container.capacity(); i++) {
      InventoryItemWrapper item = container.itemMap().get(i + container.baseSlot());
      ItemStack currentStack = inventory.getItem(i);
      if (item == null) {
        if (currentStack != null) {
          if (bottom) {
            inventory.setItem(
                container.baseSlot() + container.capacity() - 1 - (
                    i
                    + container.baseSlot()),
                null);
          } else {
            inventory.setItem(i, null);
          }
        }
      }
      if (item != null) {
        ItemStack bukkitItemStack = Converters.convert(item.handle(), ItemStack.class);
        if (currentStack == null) {
          if (item.handle() == null) {
            Bukkit.getLogger()
                .severe("InventoryItem's ItemStackWrapper is null @ slot " + i);
            continue;
          }
          if (bottom) {
            inventory.setItem(
                container.baseSlot() + container.capacity() - 1 - (
                    i
                    + container.baseSlot()),
                bukkitItemStack);
          } else {
            inventory.setItem(i, bukkitItemStack);
          }
        } else {
          if (!currentStack.equals(bukkitItemStack)) {
            if (bottom) {
              inventory.setItem(
                  container.baseSlot() + container.capacity() - 1 - (
                      i
                      + container.baseSlot()),
                  bukkitItemStack);
            } else {
              inventory.setItem(i, bukkitItemStack);
            }
          }
        }
      }
    }
  }

  private InventoryView makeView(Menu menu) {
    Inventory top;
    if (menu.inventoryType().isChest()) {
      top = Bukkit.createInventory(
          menu.player().handle(),
          menu.inventoryType().getTypicalSize(ProtocolVersionUtil.serverProtocolVersion()),
          menu.title());
    } else {
      top = Bukkit.createInventory(
          menu.player().handle(),
          Converters
              .convert(menu.inventoryType(), org.bukkit.event.inventory.InventoryType.class),
          menu.title());
    }
    if(ProtocolVersionUtil.serverProtocolVersion() > ProtocolVersions.MINECRAFT_1_13_2) {
      return new ModernInventoryView(menu, top, createPlayerInventory(menu.player().handle()));
    } else {
      return legacyView(menu, top);
    }
  }

  private InventoryView legacyView(Menu menu, Inventory top) {
    return new InventoryView() {

      private final Inventory topInventory = top;
      private final Inventory bottomInventory = createPlayerInventory(menu.player().handle());

      @Override
      public Inventory getTopInventory() {
        return topInventory;
      }

      @Override
      public Inventory getBottomInventory() {
        return bottomInventory;
      }

      @Override
      public HumanEntity getPlayer() {
        return menu.player().handle();
      }

      @Override
      public InventoryType getType() {
        return Converters.convert(
            menu.inventoryType(),
            org.bukkit.event.inventory.InventoryType.class);
      }
    };
  }

  private Inventory createPlayerInventory(Player player) {
    try {
      Class<?> craftPlayerInventoryClass =
          ReflectionUtil.getClass("{obc}.inventory.CraftInventoryPlayer");
      Class<?> nmsPlayerInventoryClass =
          ReflectionUtil.getClass("{nms}.PlayerInventory");
      Class<?> nmsEntityHumanClass =
          ReflectionUtil.getClass("{nms}.EntityHuman");
      Constructor<?> nmsPlayerInventoryConstructor =
          nmsPlayerInventoryClass.getConstructor(nmsEntityHumanClass);
      Object nmsPlayerInventory = nmsPlayerInventoryConstructor.newInstance(
          nmsEntityHumanClass.cast(ReflectionUtil.getNMSPlayer(player)));
      Constructor<?> craftPlayerInventoryConstructor =
          craftPlayerInventoryClass.getConstructor(nmsPlayerInventoryClass);
      return (Inventory) craftPlayerInventoryConstructor.newInstance(nmsPlayerInventory);
    } catch (ReflectiveOperationException exception) {
      log.error("Cannot create player inventory", exception);
    }
    return null;
  }

  @Override
  public <T> void open(PlayerWrapper playerWrapper, T inventoryImpl) {
    ((Player) playerWrapper.handle()).openInventory((InventoryView) inventoryImpl);
  }

  @Override
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
  public void destroyMenusOfPlayer(UUID uniqueId) {
    menus.removeIf(
        wrapper -> ((Player) wrapper.player().handle()).getUniqueId().equals(uniqueId));
    buildMap.remove(uniqueId);
  }

  @Override
  public Entry<Menu, Long> lastBuildOfPlayer(UUID uniqueId) {
    return buildMap.get(uniqueId);
  }

  @Override
  public void invalidate(Menu menu) {
    menus.remove(menu);
  }

}
