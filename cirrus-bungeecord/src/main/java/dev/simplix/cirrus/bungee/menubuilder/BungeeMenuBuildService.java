package dev.simplix.cirrus.bungee.menubuilder;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import dev.simplix.cirrus.actionhandler.ActionHandler;
import dev.simplix.cirrus.bungee.utils.ComponentHelper;
import dev.simplix.cirrus.item.CirrusItem;
import dev.simplix.cirrus.menu.*;
import dev.simplix.cirrus.model.CallResult;
import dev.simplix.cirrus.model.Click;
import dev.simplix.cirrus.player.CirrusPlayerWrapper;
import dev.simplix.cirrus.service.MenuBuildService;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.inventory.*;
import dev.simplix.protocolize.api.item.BaseItemStack;
import dev.simplix.protocolize.api.item.ItemStack;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import dev.simplix.protocolize.data.packets.*;
import java.util.*;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@Slf4j
public class BungeeMenuBuildService implements MenuBuildService {

  private final BiMap<UUID, Inventory> protocolizeBuildMap = HashBiMap.create();
  private final Set<Long> usedIDs = new HashSet<>();

  private final Consumer<InventoryClose> inventoryCloseConsumer = (inventoryClose) -> {
    final UUID uuid = inventoryClose.player().uniqueId();
    Menus.of(uuid).ifPresent(menu -> {
      Menus.remove(uuid);
      protocolizeBuildMap.remove(uuid);
    });
  };

  private final Consumer<InventoryClick> inventoryClickConsumer = (inventoryClick) -> {

    inventoryClick.cancelled(true);
    Menus
            .of(inventoryClick.player().uniqueId())
            .ifPresent(menu -> menu.value().actionHandler(inventoryClick.slot())
                    .ifPresent(actionHandler ->
                            inventoryClick.cancelled(handle(inventoryClick, menu, actionHandler)!=CallResult.ALLOW_GRABBING))
            );

  };

  @Nullable
  private static CallResult handle(InventoryClick inventoryClick, DisplayedMenu menu, ActionHandler actionHandler) {
    CallResult result = null;

    try {
      result = actionHandler.handle(new Click(inventoryClick.clickType(), menu,
              inventoryClick.clickedItem(), inventoryClick.slot()));
    } catch (Exception exception) {
      log.warn("Exception caught in clickhandler", exception);
    }

    return result;
  }

  private static BaseComponent[] removeItalic(String title) {
    BaseComponent[] titleComponent = TextComponent.fromLegacyText(title);
    for (BaseComponent component : titleComponent) {
      if (!component.isItalic()) {
        component.setItalic(false); // Resolve client side behavior
      }
    }
    return titleComponent;
  }

  private static void setInventoryTitle(String title, Inventory inventory) {

    if (title!=null) {
      BaseComponent[] titleComponent = removeItalic(title);
      inventory.title(titleComponent);
    }
  }

  @Override
  public DisplayedMenu openAndBuildMenu0(Menu menu, CirrusPlayerWrapper playerWrapper) {
    ProxiedPlayer player = playerWrapper.handle();
    Inventory inventory = createInventory(menu);

    buildMenuIntoInventory(inventory, menu);
    long id = generateID();

    final ProtocolizePlayer protocolizePlayer = Protocolize.playerProvider().player(player.getUniqueId());
    sendInventoryToPlayer(menu, protocolizePlayer, inventory);

    return new DisplayedMenu(menu, inventory, playerWrapper, this, id);
  }

  @Override
  public void updateMenu(DisplayedMenu displayedMenu) {
    Inventory inventory = (Inventory) displayedMenu.nativeMenu();
    final ProtocolizePlayer player = Protocolize.playerProvider().player(displayedMenu.player().uuid());
    if (displayedMenu.closed().get()) {
      return;
    }
    if (inventory.type()==displayedMenu.value().type()) {
      buildMenuIntoInventory(inventory, displayedMenu.value());
      openOrUpdateInventory(player, inventory);

    } else {
      buildAndOpenMenu(displayedMenu.value(), displayedMenu.player());
    }
  }

  @Override
  public void closeMenu0(DisplayedMenu displayedMenu) {
    Protocolize.playerProvider().player(displayedMenu.player().uuid()).closeInventory();
  }

  private void sendInventoryToPlayer(Menu menu,
                                     ProtocolizePlayer player,
                                     Inventory inventory) {
    protocolizeBuildMap.put(player.uniqueId(), inventory);
    openOrUpdateInventory(player, inventory);
  }

  private void buildMenuIntoInventory(@NonNull Inventory inventory, @NonNull Menu menu) {
    setInventoryTitle(menu.title(), inventory);
    menu.rootItems().forEach((slot, item) -> {

      ItemStack finalStack;
      if (item instanceof ItemStack itemStack) {
        finalStack = itemStack;
      } else {
        finalStack = new CirrusItem(item);
      }

      ComponentHelper.fixItalic(finalStack);
      inventory.item(slot, finalStack);
    });
  }

  private Inventory createInventory(@NonNull Menu menu) {
    Inventory inventory = new Inventory(menu.type());

    inventory.onClose(inventoryCloseConsumer);
    inventory.onClick(inventoryClickConsumer);

    return inventory;
  }

  private Long generateID() {
    long id = 0;
    while (usedIDs.contains(id)) {
      id++;
    }
    usedIDs.add(id);
    return id;
  }


  private void openOrUpdateInventory(ProtocolizePlayer player, Inventory inventory) {
    boolean alreadyOpen = false;
    int windowId = -1;

    for (Integer id : player.registeredInventories().keySet()) {
      Inventory val = player.registeredInventories().get(id);
      if (val==inventory) {
        windowId = id;
        alreadyOpen = true;
        break;
      }
    }
    if (windowId==-1) {
      windowId = player.generateWindowId();
      player.registerInventory(windowId, inventory);
    }

    int protocolVersion;
    try {
      protocolVersion = player.protocolVersion();
    } catch (Throwable t) {
      protocolVersion = 47;
    }

    final List<BaseItemStack> itemStacks = inventory.itemsIndexed(protocolVersion).stream().map((item) -> (BaseItemStack) item).toList();
    if (!alreadyOpen) {
      player.sendPacket(new OpenWindow(windowId, inventory.type(), inventory.titleJson()));


      for (int i = 0; i < itemStacks.size(); i++) {
        final ItemStack itemStack = (ItemStack) itemStacks.get(i);
        if (itemStack==null) {
          continue;
        }
        final SetSlot set = new SetSlot((byte) windowId, (short) i, itemStack, 1);
        player.sendPacket(set);
      }
    }

    player.sendPacket(new WindowItems((short) windowId, new ArrayList<>(itemStacks), 1));
  }

}