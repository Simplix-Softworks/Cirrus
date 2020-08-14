package dev.simplix.cirrus.common.item;

import de.exceptionflug.protocolize.items.ItemStack;
import de.exceptionflug.protocolize.items.ItemType;
import java.util.List;
import net.md_5.bungee.api.chat.BaseComponent;
import net.querz.nbt.tag.CompoundTag;
import dev.simplix.cirrus.api.business.ItemStackWrapper;

public class ProtocolizeItemStackWrapper implements ItemStackWrapper {

  private final ItemStack itemStack;

  public ProtocolizeItemStackWrapper(ItemStack itemStack) {
    this.itemStack = itemStack;
  }

  public String displayName() {
    return itemStack.getDisplayName();
  }

  public BaseComponent[] displayNameComponents() {
    return itemStack.getDisplayNameComponents();
  }

  public List<String> lore() {
    return itemStack.getLore();
  }

  public List<BaseComponent[]> loreComponents() {
    return itemStack.getLoreComponents();
  }

  public ItemType type() {
    return itemStack.getType();
  }

  public CompoundTag nbt() {
    return (CompoundTag) itemStack.getNBTTag();
  }

  public int amount() {
    return itemStack.getAmount();
  }

  public short durability() {
    return itemStack.getDurability();
  }

  public void type(ItemType type) {
    itemStack.setType(type);
  }

  public void displayName(String s) {
    itemStack.setDisplayName(s);
  }

  public void displayNameComponents(BaseComponent... baseComponents) {
    itemStack.setDisplayName(baseComponents);
  }

  public void lore(List<String> lore) {
    itemStack.setLore(lore);
  }

  public void loreComponents(List<BaseComponent[]> lore) {
    itemStack.setLoreComponents(lore);
  }

  public void nbt(CompoundTag tag) {
    itemStack.setNBTTag(tag);
  }

  public void amount(int amount) {
    itemStack.setAmount((byte) amount);
  }

  public void durability(short durability) {
    itemStack.setDurability(durability);
  }

  public <T> T handle() {
    return (T) itemStack;
  }

  @Override
  public String toString() {
    return handle().toString();
  }
}
