package dev.simplix.cirrus.spigot.modern;

import dev.simplix.cirrus.api.menu.Menu;
import lombok.NonNull;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public final class ModernInventoryView extends InventoryView {

  private final Menu menu;
  private final Inventory top;
  private final Inventory bottom;

  public ModernInventoryView(
      @NonNull Menu menu,
      @NonNull Inventory top,
      @NonNull Inventory bottom) {
    this.menu = menu;
    this.top = top;
    this.bottom = bottom;
  }

  @Override
  public Inventory getTopInventory() {
    return top;
  }

  @Override
  public Inventory getBottomInventory() {
    return bottom;
  }

  @Override
  public HumanEntity getPlayer() {
    return menu.player().handle();
  }

  @Override
  public InventoryType getType() {
    return top.getType();
  }

  @Override
  public String getTitle() {
    return menu.title();
  }

}
