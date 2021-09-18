package dev.simplix.cirrus.common.item;

import dev.simplix.cirrus.api.business.ItemStackWrapper;
import java.util.List;

import dev.simplix.protocolize.api.item.ItemStack;
import dev.simplix.protocolize.data.ItemType;
import lombok.NonNull;
import net.md_5.bungee.api.chat.BaseComponent;
import net.querz.nbt.tag.CompoundTag;

public class ProtocolizeItemStackWrapper implements ItemStackWrapper {

  private final ItemStack itemStack;

  public ProtocolizeItemStackWrapper(ItemStack itemStack) {
    this.itemStack = itemStack;
  }

  @Override
  public String displayName() {
    return itemStack.displayName(true);
  }

  @Override
  public BaseComponent[] displayNameComponents() {
    return itemStack.displayName();
  }

  @Override
  public List<String> lore() {
    return itemStack.lore(true);
  }

  @Override
  public List<BaseComponent[]> loreComponents() {
    return itemStack.lore();
  }

  @Override
  public ItemType type() {
    return itemStack.itemType();
  }

  @Override
  public CompoundTag nbt() {
    return itemStack.nbtData();
  }

  @Override
  public int amount() {
    return itemStack.amount();
  }

  @Override
  public short durability() {
    return itemStack.durability();
  }

  @Override
  public void type(@NonNull ItemType type) {
    itemStack.itemType(type);
  }

  @Override
  public void displayName(@NonNull String displayName) {
    itemStack.displayName(displayName);
  }

  @Override
  public void displayNameComponents(BaseComponent... baseComponents) {
    itemStack.displayName(baseComponents);
  }

  @Override
  public void lore(@NonNull List<String> lore) {
    itemStack.lore(lore, true);
  }

  @Override
  public void loreComponents(@NonNull List<BaseComponent[]> lore) {
    itemStack.lore(lore, false);
  }

  @Override
  public void nbt(@NonNull CompoundTag compoundTag) {
    itemStack.nbtData(compoundTag);
  }

  @Override
  public void amount(int amount) {
    itemStack.amount((byte) amount);
  }

  @Override
  public void durability(short durability) {
    itemStack.durability(durability);
  }

  @Override
  public <T> T handle() {
    return (T) itemStack;
  }

  @Override
  public String toString() {
    return handle().toString();
  }
}
