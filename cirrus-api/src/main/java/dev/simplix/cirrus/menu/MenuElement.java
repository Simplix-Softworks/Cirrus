package dev.simplix.cirrus.menu;

import dev.simplix.cirrus.item.CirrusItem;
import dev.simplix.cirrus.schematic.MenuSchematic;
import dev.simplix.protocolize.api.item.BaseItemStack;

import java.util.Optional;
import javax.annotation.Nullable;

import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Accessors(fluent = true)
public class MenuElement {
  @Nullable
  private transient MenuSchematic menuSchematic;

  @Nullable
  private BaseItemStack item;
  @Nullable
  @Getter
  private Integer slot;

  @Nullable
  private String actionHandler;


  public void set(@NonNull CirrusItem cirrusItem) {
    this.set(cirrusItem, cirrusItem.actionHandler());
  }

  public void set(@NonNull BaseItemStack item) {
    this.set(item, null);
  }

  public void set(@NonNull BaseItemStack item, @Nullable String actionHandler) {
    this.actionHandler = actionHandler;
    this.item = item;
    if (this.menuSchematic != null && this.slot != null) {
      applyChanges(this.menuSchematic, this.slot);
    }
  }

  public Optional<MenuSchematic> menuSchematic(){
    return Optional.ofNullable(menuSchematic);
  }

  public Optional<String> actionHandlerString() {
    return Optional.ofNullable(actionHandler);
  }

  public void applyChanges(@NonNull MenuSchematic menuSchematic, int slot){
      this.slot = slot;
      menuSchematic.set(item, slot, actionHandler);
  }

  public Optional<BaseItemStack> item() {
    return Optional.ofNullable(this.item);
  }

}