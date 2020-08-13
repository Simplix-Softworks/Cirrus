package dev.simplix.cirrus.spigot.menubuilder;

import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.menu.Menu;
import dev.simplix.cirrus.api.menu.MenuBuilder;
import dev.simplix.cirrus.spigot.util.ReflectionUtil;
import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;

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
    if(prebuild instanceof InventoryView) {
      InventoryView inventoryView = (InventoryView) prebuild;
      // TODO
    }
    return null;
  }

  @Override
  public <T> void open(PlayerWrapper playerWrapper, T inventoryImpl) {
    ((Player)playerWrapper.handle()).openInventory((InventoryView) inventoryImpl);
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
