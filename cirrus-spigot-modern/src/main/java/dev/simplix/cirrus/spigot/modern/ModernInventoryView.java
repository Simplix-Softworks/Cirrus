package dev.simplix.cirrus.spigot.modern;

import dev.simplix.cirrus.api.menu.Menu;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

public final class ModernInventoryView extends InventoryView {

  private final Menu menu;
  private final Inventory top;
  private final Inventory bottom;

  public ModernInventoryView(Menu menu, Inventory top, Inventory bottom) {
    this.menu = menu;
    this.top = top;
    this.bottom = bottom;
  }

  public @NotNull Inventory getTopInventory() {
    return top;
  }

  public @NotNull Inventory getBottomInventory() {
    return bottom;
  }

  public @NotNull HumanEntity getPlayer() {
    return menu.player().handle();
  }

  public @NotNull InventoryType getType() {
    return top.getType();
  }

  public @NotNull String getTitle() {
    return menu.title();
  }

}
