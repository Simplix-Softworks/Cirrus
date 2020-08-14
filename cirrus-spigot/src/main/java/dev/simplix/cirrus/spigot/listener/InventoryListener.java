package dev.simplix.cirrus.spigot.listener;

import com.google.inject.Inject;
import dev.simplix.cirrus.api.business.InventoryItemWrapper;
import dev.simplix.cirrus.api.menu.*;
import dev.simplix.cirrus.common.menu.AbstractMenu;
import dev.simplix.core.common.aop.Component;
import dev.simplix.core.common.converter.Converters;
import dev.simplix.core.minecraft.spigot.dynamiclisteners.DynamicListenersSimplixModule;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryView;

import java.util.Map;

@Component(DynamicListenersSimplixModule.class)
public class InventoryListener implements Listener {

  private final MenuBuilder menuBuilder;

  @Inject
  public InventoryListener(MenuBuilder menuBuilder) {
    this.menuBuilder = menuBuilder;
  }

  @EventHandler
  public void onClick(InventoryClickEvent event) {
    InventoryView inventoryView = event.getWhoClicked().getOpenInventory();
    if (event.getClickedInventory() == null) {
      return;
    }
    Bukkit.broadcastMessage("Clicked inventoryView");
    Menu menu = menuBuilder.menuByHandle(inventoryView);
    if (menu == null)
      return;
    Bukkit.broadcastMessage("Clicked menu: " + menu.getClass().getSimpleName() + " @ slot " + event.getSlot());
    Container container;
    if (event.getSlot() > menu.topContainer().capacity() - 1) {
      container = menu.bottomContainer();
      Bukkit.broadcastMessage("Clicked bottom container");
    } else {
      container = menu.topContainer();
      Bukkit.broadcastMessage("Clicked top container");
    }
    InventoryItemWrapper item = container.get(event.getSlot());
    ClickType type = event.getClick();
    if (item == null) {
      Bukkit.broadcastMessage("Clicked nothing");
      if (menu.customActionHandler() != null) {
        try {
          CallResult callResult = menu
                  .customActionHandler()
                  .handle(new Click(Converters.convert(type, de.exceptionflug.protocolize.api.ClickType.class),
                          menu, null, event.getSlot()));
          event.setCancelled(callResult == null || callResult == CallResult.DENY_GRABBING);
        } catch (Exception ex) {
          event.setCancelled(true);
          menu.handleException(null, ex);
        }
      }
      return;
    }
    Bukkit.broadcastMessage("Clicked "+item.displayName());
    ActionHandler actionHandler = menu.actionHandler(item.actionHandler());
    if (actionHandler == null)
      return;
    try {
      final CallResult callResult = actionHandler.handle(new Click(
              Converters.convert(type, de.exceptionflug.protocolize.api.ClickType.class),
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
  public void onQuit(PlayerQuitEvent event) {
    menuBuilder.destroyMenusOfPlayer(event.getPlayer().getUniqueId());
  }

  @EventHandler
  public void onClose(InventoryCloseEvent e) {
    InventoryView inventoryView = e.getPlayer().getOpenInventory();
    if (inventoryView == null) {
      return;
    }
    Menu menu = menuBuilder.menuByHandle(inventoryView);
    if (menu == null)
      return;
    Map.Entry<Menu, Long> lastBuild = menuBuilder.lastBuildOfPlayer(e.getPlayer().getUniqueId());
    if (((AbstractMenu) lastBuild.getKey()).internalId() == ((AbstractMenu) menu).internalId()
            && (System.currentTimeMillis() - lastBuild.getValue()) <= 55)
      return;
    menu.handleClose((System.currentTimeMillis() - lastBuild.getValue()) <= 55);
    menuBuilder.invalidate(menu);
  }

}
