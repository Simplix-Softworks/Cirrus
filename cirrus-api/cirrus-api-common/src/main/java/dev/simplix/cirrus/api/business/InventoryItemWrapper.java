package dev.simplix.cirrus.api.business;

import de.exceptionflug.protocolize.items.ItemType;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.md_5.bungee.api.chat.BaseComponent;
import net.querz.nbt.tag.CompoundTag;

@Accessors(fluent = true)
@Builder
public class InventoryItemWrapper implements ItemStackWrapper {

  private final ItemStackWrapper handle;

  @Getter
  @Setter
  private String actionHandler;
  @Getter
  @Setter
  private List<String> actionArguments;

  @Override
  public String displayName() {
    return handle.displayName();
  }

  @Override
  public BaseComponent[] displayNameComponents() {
    return handle.displayNameComponents();
  }

  @Override
  public List<String> lore() {
    return handle.lore();
  }

  @Override
  public List<BaseComponent[]> loreComponents() {
    return handle.loreComponents();
  }

  @Override
  public ItemType type() {
    return handle.type();
  }

  @Override
  public CompoundTag nbt() {
    return handle.nbt();
  }

  @Override
  public int amount() {
    return handle.amount();
  }

  @Override
  public short durability() {
    return handle.durability();
  }

  @Override
  public void type(@NonNull ItemType type) {
    handle.type(type);
  }

  @Override
  public void displayName(@NonNull String displayName) {
    handle.displayName(displayName);
  }

  @Override
  public void displayNameComponents(@NonNull BaseComponent... baseComponents) {
    handle.displayNameComponents(baseComponents);
  }

  @Override
  public void lore(@NonNull List<String> lore) {
    handle.lore(lore);
  }

  @Override
  public void loreComponents(@NonNull List<BaseComponent[]> lore) {
    handle.loreComponents(lore);
  }

  @Override
  public void nbt(@NonNull CompoundTag tag) {
    handle.nbt(tag);
  }

  @Override
  public void amount(int amount) {
    handle.amount(amount);
  }

  @Override
  public void durability(short durability) {
    handle.durability(durability);
  }

  @Override
  public <T> T handle() {
    return handle.handle();
  }

  @Override
  public String toString() {
    return "InventoryItemWrapper{" +
           "handle=" + handle +
           ", actionHandler='" + actionHandler + '\'' +
           ", actionArguments=" + actionArguments +
           '}';
  }
}
