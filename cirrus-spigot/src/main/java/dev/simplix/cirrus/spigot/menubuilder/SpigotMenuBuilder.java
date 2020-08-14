package dev.simplix.cirrus.spigot.menubuilder;

import dev.simplix.cirrus.api.business.InventoryItemWrapper;
import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.menu.Container;
import dev.simplix.cirrus.api.menu.Menu;
import dev.simplix.cirrus.api.menu.MenuBuilder;
import dev.simplix.cirrus.common.CirrusSimplixModule;
import dev.simplix.cirrus.spigot.util.ProtocolVersionUtil;
import dev.simplix.cirrus.spigot.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;

import dev.simplix.core.common.aop.Component;
import dev.simplix.core.common.converter.Converters;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

@Component(value = CirrusSimplixModule.class, parent=MenuBuilder.class)
public final class SpigotMenuBuilder implements MenuBuilder {

  private final Map<UUID, Map.Entry<Menu, Long>> buildMap = new LinkedHashMap<>();
  private final List<Menu> menus = new LinkedList<>();

  private static Class<?> minecraftInventoryClass;
  private static Field titleField;

  static {
    try {
      minecraftInventoryClass = ReflectionUtil.getClass(
              "{obc}.inventory.CraftInventoryCustom$MinecraftInventory");
      titleField = minecraftInventoryClass.getDeclaredField("title");
      titleField.setAccessible(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public <T> T build(T prebuild, Menu menu) {
    boolean reopen = false;
    boolean register = prebuild == null;
    if (prebuild instanceof InventoryView) {
      InventoryView inventoryView = (InventoryView) prebuild;
      if (!inventoryView.getTitle().equals(menu.title())
              || inventoryView.getTopInventory().getSize() != menu.inventoryType()
              .getTypicalSize(ProtocolVersionUtil.protocolVersion())
              || inventoryView.getTopInventory().getType() != Converters
              .convert(menu.inventoryType(), org.bukkit.event.inventory.InventoryType.class)) {
        prebuild = (T) makeView(menu);
        reopen = true;
      }
    } else {
      prebuild = (T) makeView(menu);
    }
    InventoryView view = (InventoryView) prebuild;
    buildContainer(view.getTopInventory(), menu.topContainer());
    buildContainer(view.getBottomInventory(), menu.bottomContainer());

    if(register) {
      menus.add(menu);
    }
    if(reopen) {
      open(menu.player(), prebuild);
    }
    return prebuild;
  }

  private void buildContainer(Inventory inventory, Container container) {
    for (int i = container.baseSlot(); i < container.baseSlot()+container.capacity(); i++) {
      InventoryItemWrapper item = container.itemMap().get(i);
      ItemStack currentStack = inventory.getItem(i);
      if (item == null) {
        if (currentStack != null) {
          inventory.setItem(i, null);
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
          inventory.setItem(i, bukkitItemStack);
        } else {
          if (!currentStack.equals(bukkitItemStack)) {
            inventory.setItem(i, bukkitItemStack);
          }
        }
      }
    }
  }

  private InventoryView makeView(Menu menu) {
    Inventory top;
    if (menu.inventoryType().isChest()) {
      top = Bukkit.createInventory(menu.player().handle(),
              menu.inventoryType().getTypicalSize(ProtocolVersionUtil.protocolVersion()), menu.title());
    } else {
      top = Bukkit.createInventory(menu.player().handle(), Converters
              .convert(menu.inventoryType(), org.bukkit.event.inventory.InventoryType.class), menu.title());
    }
    return new InventoryView() {

      private final Inventory topInventory = top;
      private final Inventory bottomInventory = Bukkit.createInventory(menu.player().handle(), InventoryType.PLAYER);

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
        return Converters.convert(menu.inventoryType(), org.bukkit.event.inventory.InventoryType.class);
      }
    };
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
