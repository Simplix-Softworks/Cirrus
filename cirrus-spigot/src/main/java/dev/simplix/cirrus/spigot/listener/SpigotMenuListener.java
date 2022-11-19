package dev.simplix.cirrus.spigot.listener;

import dev.simplix.cirrus.Cirrus;
import dev.simplix.cirrus.actionhandler.ActionHandler;
import dev.simplix.cirrus.menu.Menus;
import dev.simplix.cirrus.menu.DisplayedMenu;
import dev.simplix.cirrus.model.CallResult;
import dev.simplix.cirrus.model.Click;
import dev.simplix.cirrus.spigot.converters.SpigotClickTypeConverter;
import dev.simplix.protocolize.api.ClickType;

import java.util.Optional;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@Slf4j
public class SpigotMenuListener implements Listener {

  private static void handleClick(
      InventoryClickEvent event,
      DisplayedMenu displayedMenu,
      ActionHandler actionHandler) {

    CallResult handle = null;

    try {
      final ClickType spigotClickType = Cirrus
          .service(SpigotClickTypeConverter.class)
          .apply(event.getClick());
      final Click click = new Click(
          spigotClickType,
          displayedMenu,
          displayedMenu.value().get(event.getSlot()),
          event.getSlot());
      handle = actionHandler.handle(click);
    } catch (Exception exception) {
      log.warn("Exception caught in clickhandler", exception);
    }
    event.setCancelled(handle != CallResult.ALLOW_GRABBING);
  }

  @EventHandler
  public void onDrag(InventoryDragEvent event) {
    if (Menus.of(event.getWhoClicked().getUniqueId()).isEmpty()) {
      return;
    }

    event.setCancelled(true);
  }

  @EventHandler
  public void onClick(InventoryClickEvent event) {
    try {
      final Optional<DisplayedMenu> of = Menus.of(event.getWhoClicked().getUniqueId());
      if (of.isEmpty()) {
        log.info("no opened menu");
        return;
      }

      final DisplayedMenu displayedMenu = of.get();
      final ActionHandler actionHandler = displayedMenu
          .value()
          .actionHandler(event.getSlot())
          .orElse(null);
      if (actionHandler != null) {
        handleClick(event, displayedMenu, actionHandler);
        return;
      }
    } catch (Exception exception) {
      log.warn("Could not handle click event. Canceling for safety", exception);
    }
    event.setCancelled(true);
  }

  @EventHandler
  public void onClose(InventoryCloseEvent event) {
    UUID uniqueId = event.getPlayer().getUniqueId();
    final Optional<DisplayedMenu> of = Menus.of(uniqueId);
    if (of.isEmpty()) {
      return;
    }

    Menus.remove(uniqueId);
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    Menus.remove(event.getPlayer().getUniqueId());
  }
}
