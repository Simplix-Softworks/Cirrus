package dev.simplix.cirrus.bungeecord.listeners;

import com.google.inject.Inject;
import de.exceptionflug.protocolize.api.ClickType;
import de.exceptionflug.protocolize.inventory.Inventory;
import de.exceptionflug.protocolize.inventory.event.InventoryClickEvent;
import de.exceptionflug.protocolize.inventory.event.InventoryCloseEvent;
import dev.simplix.cirrus.api.business.InventoryItemWrapper;
import dev.simplix.cirrus.api.menu.*;
import dev.simplix.cirrus.common.menu.AbstractMenu;
import dev.simplix.core.common.aop.Component;
import dev.simplix.core.minecraft.bungeecord.dynamiclisteners.DynamicListenersSimplixModule;
import java.util.Map;
import lombok.NonNull;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

@Component(DynamicListenersSimplixModule.class)
public class InventoryListener implements Listener {

  private final MenuBuilder menuBuilder;

  @Inject
  public InventoryListener(@NonNull MenuBuilder menuBuilder) {
    this.menuBuilder = menuBuilder;
  }

  @EventHandler
  public void onClick(@NonNull InventoryClickEvent event) {
    if (event.getClickType() == null) {
      return;
    }
    if (event.getPlayer() == null) {
      return;
    }

    if (event.getClickedItem() == null) {
      return;
    }

    final Inventory inventory = event.getInventory();
    if (inventory == null) {
      return;
    }
//    ProxyServer.getInstance().broadcast("Clicked inventory");
    Menu menu = menuBuilder.menuByHandle(inventory);
    if (menu == null) {
      return;
    }
//    ProxyServer.getInstance().broadcast("Clicked menu: " + menu.getClass().getSimpleName() + " @ slot " + event.getSlot());
    Container container;
    if (event.getSlot() > menu.topContainer().capacity() - 1) {
      container = menu.bottomContainer();
//      ProxyServer.getInstance().broadcast("Clicked bottom container");
    } else {
      container = menu.topContainer();
//      ProxyServer.getInstance().broadcast("Clicked top container");
    }
    InventoryItemWrapper item = container.get(event.getSlot());
    ClickType type = event.getClickType();
    if (item == null) {
//      ProxyServer.getInstance().broadcast("Clicked nothing");
      if (menu.customActionHandler() != null) {
        try {
          CallResult callResult = menu
              .customActionHandler()
              .handle(new Click(type, menu, null, event.getSlot()));
          event.setCancelled(callResult == null || callResult == CallResult.DENY_GRABBING);
        } catch (Exception ex) {
          event.setCancelled(true);
          menu.handleException(null, ex);
        }
      }
      return;
    }
//    ProxyServer.getInstance().broadcast("Clicked "+item.displayName());
    ActionHandler actionHandler = menu.actionHandler(item.actionHandler());
    if (actionHandler == null) {
      return;
    }
    try {
      final CallResult callResult = actionHandler.handle(new Click(
          type,
          menu,
          item,
          event.getSlot()));
      event.setCancelled(callResult == null || callResult == CallResult.DENY_GRABBING);
    } catch (final Exception ex) {
      event.setCancelled(true);
      menu.handleException(actionHandler, ex);
    }
  }

  @EventHandler
  public void onQuit(@NonNull PlayerDisconnectEvent event) {
    menuBuilder.destroyMenusOfPlayer(event.getPlayer().getUniqueId());
  }

  @EventHandler
  public void onClose(@NonNull InventoryCloseEvent inventoryCloseEvent) {
    final Inventory inventory = inventoryCloseEvent.getInventory();
    if (inventory == null) {
      return;
    }
    Menu menu = menuBuilder.menuByHandle(inventory);
    if (menu == null) {
      return;
    }
    Map.Entry<Menu, Long> lastBuild = menuBuilder.lastBuildOfPlayer(inventoryCloseEvent
        .getPlayer()
        .getUniqueId());
    if (((AbstractMenu) lastBuild.getKey()).internalId() == ((AbstractMenu) menu).internalId()
        && (System.currentTimeMillis() - lastBuild.getValue()) <= 55) {
      return;
    }
    menu.handleClose((System.currentTimeMillis() - lastBuild.getValue()) <= 55);
    menuBuilder.invalidate(menu);
  }

}
