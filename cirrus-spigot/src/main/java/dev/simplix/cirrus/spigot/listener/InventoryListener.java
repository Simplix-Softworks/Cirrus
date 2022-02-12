package dev.simplix.cirrus.spigot.listener;

import dev.simplix.cirrus.common.Cirrus;
import dev.simplix.cirrus.common.business.InventoryMenuItemWrapper;
import dev.simplix.cirrus.common.container.Container;
import dev.simplix.cirrus.common.converter.Converters;
import dev.simplix.cirrus.common.handler.ActionHandler;
import dev.simplix.cirrus.common.menu.AbstractMenu;
import dev.simplix.cirrus.common.menu.Menu;
import dev.simplix.cirrus.common.menu.MenuBuilder;
import dev.simplix.cirrus.common.model.CallResult;
import dev.simplix.cirrus.common.model.Click;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryView;

@Slf4j
public class InventoryListener implements Listener {

    private final MenuBuilder menuBuilder = Cirrus.getService(MenuBuilder.class);

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        InventoryView inventoryView = event.getWhoClicked().getOpenInventory();
        if (event.getInventory() == null) {
            return;
        }

        Menu menu = this.menuBuilder.menuByHandle(inventoryView);
        if (menu==null) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryView inventoryView = event.getWhoClicked().getOpenInventory();
        if (event.getClickedInventory()==null) {
            return;
        }
//    Bukkit.broadcastMessage("Clicked inventoryView");
        Menu menu = this.menuBuilder.menuByHandle(inventoryView);
        if (menu==null) {
            return;
        }
//    Bukkit.broadcastMessage("Clicked menu: "
//                            + menu.getClass().getSimpleName()
//                            + " @ slot "
//                            + event.getSlot());
        Container container;
        if (event.getRawSlot() > menu.topContainer().capacity() - 1) {
            container = menu.bottomContainer();
//      Bukkit.broadcastMessage("Clicked bottom container");
        } else {
            container = menu.topContainer();
//      Bukkit.broadcastMessage("Clicked top container");
        }
        InventoryMenuItemWrapper item = container.get(event.getRawSlot());
        ClickType type = event.getClick();
        if (item==null) {
//      Bukkit.broadcastMessage("Clicked nothing");
            if (menu.customActionHandler()!=null) {
                try {
                    CallResult callResult = menu
                            .customActionHandler()
                            .handle(new Click(Converters.convert(
                                    type,
                                    dev.simplix.protocolize.api.ClickType.class),
                                    menu, null, event.getSlot()));
                    event.setCancelled(callResult==null || callResult==CallResult.DENY_GRABBING);
                } catch (Exception ex) {
                    event.setCancelled(true);
                    menu.handleException(null, ex);
                }
            }
            return;
        }
//    Bukkit.broadcastMessage("Clicked " + item.displayName());
        ActionHandler actionHandler = menu.actionHandler(item.actionHandler());
        if (actionHandler==null) {
            event.setCancelled(true);
            return;
        }
        try {
            final CallResult callResult = actionHandler.handle(new Click(
                    Converters.convert(type, dev.simplix.protocolize.api.ClickType.class),
                    menu,
                    item,
                    event.getSlot()));
            event.setCancelled(callResult==null || callResult==CallResult.DENY_GRABBING);
        } catch (final Exception ex) {
            event.setCancelled(true);
            menu.handleException(actionHandler, ex);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.menuBuilder.destroyMenusOfPlayer(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onClose(InventoryCloseEvent inventoryCloseEvent) {
        InventoryView inventoryView = inventoryCloseEvent.getPlayer().getOpenInventory();
        if (inventoryView==null) {
            return;
        }
        Menu menu = this.menuBuilder.menuByHandle(inventoryView);
        if (menu==null) {
            return;
        }
        ((Player) inventoryCloseEvent.getPlayer()).updateInventory();
        Map.Entry<Menu, Long> lastBuild = this.menuBuilder.lastBuildOfPlayer(inventoryCloseEvent.getPlayer().getUniqueId());
        if (lastBuild==null) {
            log.warn("[Cirrus] Exiting from unbuilt menu? Class = "
                    + menu.getClass().getName()
                    + ", Player = "
                    + inventoryCloseEvent.getPlayer().getName());
            menu.handleClose(false);
            this.menuBuilder.invalidate(menu);
            return;
        }
        if (((AbstractMenu) lastBuild.getKey()).internalId()==((AbstractMenu) menu).internalId()
                && (System.currentTimeMillis() - lastBuild.getValue()) <= 55) {
            return;
        }
        menu.handleClose((System.currentTimeMillis() - lastBuild.getValue()) <= 55);
        this.menuBuilder.invalidate(menu);
    }

}
