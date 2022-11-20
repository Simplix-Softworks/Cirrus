package dev.simplix.cirrus.spigot.menubuilder;

import dev.simplix.cirrus.Cirrus;
import dev.simplix.cirrus.menu.Menu;
import dev.simplix.cirrus.menu.Menus;
import dev.simplix.cirrus.menu.DisplayedMenu;
import dev.simplix.cirrus.player.CirrusPlayerWrapper;
import dev.simplix.cirrus.service.MenuBuildService;
import dev.simplix.cirrus.spigot.services.converters.ItemStackConverter;
import dev.simplix.cirrus.spigot.services.converters.SpigotInventoryTypeConverter;
import dev.simplix.cirrus.spigot.util.ProtocolVersionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

import static dev.simplix.protocolize.data.inventory.InventoryType.*;

@Slf4j
@RequiredArgsConstructor
public class SpigotMenuBuildService implements MenuBuildService {

  private final JavaPlugin plugin;

  private final Set<Long> usedIDs = new HashSet<>();

  private static boolean isChest(dev.simplix.protocolize.data.inventory.InventoryType type) {
    if (type == GENERIC_3X3) {
      return false;
    } else {
      return type.name().startsWith("GENERIC") || type.name().contains("CHEST");
    }
  }

  @Override
  public DisplayedMenu openAndBuildMenu0(Menu menu, CirrusPlayerWrapper playerWrapper) {
    Player player = playerWrapper.handle(); // TODO

    InventoryView inventoryView = makeView(menu, player);
    Inventory top = inventoryView.getTopInventory();
    if (top == null) {
      throw new IllegalStateException("Top inventory must not be null");
    }
    buildMenuIntoInventory(top, menu);
    player.openInventory(top);

    final long id = generateID();
    final DisplayedMenu displayedMenu = new DisplayedMenu(
        menu,
        inventoryView,
        playerWrapper,
        this,
        id);
    return displayedMenu;
  }

  @Override
  public void updateMenu(DisplayedMenu displayedMenu) {
    InventoryView inventoryView = (InventoryView) displayedMenu.nativeMenu();
    Inventory top = inventoryView.getTopInventory();

    if (top.getSize() == displayedMenu
        .value()
        .type()
        .getTypicalSize(ProtocolVersionUtil.serverProtocolVersion())) {
      buildMenuIntoInventory(top, displayedMenu.value());
    } else {
      buildAndOpenMenu(displayedMenu.value(), displayedMenu.player());
    }
  }

  @Override
  public void closeMenu0(DisplayedMenu displayedMenu) {
    Menus.remove(displayedMenu.player().uuid());
    if (displayedMenu.player().handle() instanceof Player player) {
      player.closeInventory();
    }
  }

  public void buildMenuIntoInventory(Inventory inventory, Menu menu) {
    menu.rootItems().forEach((slot, item) -> {
      ItemStack apply = Cirrus.service(ItemStackConverter.class).apply(item);
      inventory.setItem(slot, apply);
    });
  }

  private InventoryView makeView(Menu menu, Player player) {
    Inventory result;
    Inventory bottom = player.getInventory();
    if (isChest(menu.type())) {
      result = Bukkit.createInventory(
          player,
          menu.type().getTypicalSize(ProtocolVersionUtil.serverProtocolVersion()),
          menu.title()
                                     );
    } else {
      result = Bukkit.createInventory(
          player,
          Cirrus.service(SpigotInventoryTypeConverter.class).apply(menu.type()),
          menu.title());
    }

    return new ModernInventoryView(menu, player, result, bottom);
  }

  private long generateID() {
    long id = 0;
    while (usedIDs.contains(id)) {
      id++;
    }
    usedIDs.add(id);
    return id;
  }
}