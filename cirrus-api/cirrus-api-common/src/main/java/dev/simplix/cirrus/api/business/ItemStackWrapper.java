package dev.simplix.cirrus.api.business;

import de.exceptionflug.protocolize.items.ItemType;
import java.util.List;
import net.md_5.bungee.api.chat.BaseComponent;
import net.querz.nbt.tag.CompoundTag;

/**
 * An {@link ItemStackWrapper} provides uniform data access for items across different platforms.
 * Please be aware, that most of the given properties are immutable, so you have to set them using
 * their setter after you have done some changes.
 */
public interface ItemStackWrapper {

  /**
   * Returns the display name of an item as a string.
   *
   * @return display name
   */
  String displayName();

  /**
   * Returns the display name of an item as component array.
   *
   * @return display name
   */
  BaseComponent[] displayNameComponents();

  /**
   * The lore of the item as strings.
   *
   * @return lore
   */
  List<String> lore();

  /**
   * The lore of the item as component arrays.
   *
   * @return lore
   */
  List<BaseComponent[]> loreComponents();

  /**
   * The {@link ItemType} of the item.
   *
   * @return item type
   */
  ItemType type();

  /**
   * This returns a querz-nbt {@link CompoundTag} with the nbt information about this item. Please
   * be aware, that the nbt data of spigot ItemStacks are immutable. So you have to call setNBT()
   * after you changed the data to set them.
   *
   * @return nbt data
   */
  CompoundTag nbt();

  /**
   * Returns the amount of the stack
   *
   * @return amount
   */
  int amount();

  /**
   * Returns the durability of the item.
   *
   * @return
   */
  short durability();

  /**
   * Sets the mcc item type.
   *
   * @param type type to set
   */
  void type(ItemType type);

  /**
   * Sets the display name of the item
   *
   * @param s name
   */
  void displayName(String s);

  /**
   * Sets the display name of the item
   *
   * @param baseComponents name
   */
  void displayNameComponents(BaseComponent... baseComponents);

  /**
   * Sets the lore of the item
   *
   * @param lore
   */
  void lore(List<String> lore);

  /**
   * Sets the lore of the item
   *
   * @param lore
   */
  void loreComponents(List<BaseComponent[]> lore);

  /**
   * Sets the nbt data.
   *
   * @param tag nbt data
   */
  void nbt(CompoundTag tag);

  /**
   * Sets the stack amount.
   *
   * @param amount amount
   */
  void amount(int amount);

  /**
   * Sets the durability.
   * <br>#DUDELAVERITY
   *
   * @param durability durability
   */
  void durability(short durability);

  /**
   * Returns the handle of the wrapper
   *
   * @param <T> dyncast handle type
   * @return the handle
   */
  <T> T handle();

}