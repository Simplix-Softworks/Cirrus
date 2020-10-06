package dev.simplix.cirrus.common.item;

import de.exceptionflug.protocolize.items.ItemStack;
import de.exceptionflug.protocolize.items.ItemType;
import dev.simplix.cirrus.api.business.ItemStackWrapper;
import java.util.List;
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
    return itemStack.getDisplayName();
  }

  @Override
  public BaseComponent[] displayNameComponents() {
    return itemStack.getDisplayNameComponents();
  }

  @Override
  public List<String> lore() {
    return itemStack.getLore();
  }

  @Override
  public List<BaseComponent[]> loreComponents() {
    return itemStack.getLoreComponents();
  }

  @Override
  public ItemType type() {
    return itemStack.getType();
  }

  @Override
  public CompoundTag nbt() {
    return (CompoundTag) itemStack.getNBTTag();
  }

  @Override
  public int amount() {
    return itemStack.getAmount();
  }

  @Override
  public short durability() {
    return itemStack.getDurability();
  }

  @Override
  public void type(@NonNull ItemType type) {
    itemStack.setType(type);
  }

  @Override
  public void displayName(@NonNull String displayName) {
    itemStack.setDisplayName(displayName);
  }

  @Override
  public void displayNameComponents(BaseComponent... baseComponents) {
    itemStack.setDisplayName(baseComponents);
  }

  @Override
  public void lore(@NonNull List<String> lore) {
    itemStack.setLore(lore);
  }

  @Override
  public void loreComponents(@NonNull List<BaseComponent[]> lore) {
    itemStack.setLoreComponents(lore);
  }

  @Override
  public void nbt(@NonNull CompoundTag compoundTag) {
    itemStack.setNBTTag(compoundTag);
  }

  @Override
  public void amount(int amount) {
    itemStack.setAmount((byte) amount);
  }

  @Override
  public void durability(short durability) {
    itemStack.setDurability(durability);
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
