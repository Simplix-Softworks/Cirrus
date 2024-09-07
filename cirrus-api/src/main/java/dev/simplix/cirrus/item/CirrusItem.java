package dev.simplix.cirrus.item;

import dev.simplix.cirrus.Utils;
import dev.simplix.cirrus.effect.AbstractMenuEffect;
import dev.simplix.cirrus.util.ToStringUtil;
import dev.simplix.protocolize.api.chat.ChatElement;
import dev.simplix.protocolize.api.item.BaseItemStack;
import dev.simplix.protocolize.api.item.ItemStack;
import dev.simplix.protocolize.data.ItemType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import lombok.*;
import lombok.experimental.Accessors;
import net.querz.nbt.tag.CompoundTag;

/**
 * One of the main entry-points of the Cirrus API
 *
 * <p>
 * The {@link CirrusItem} class extends the {@link ItemStack} class from the Protocolize API, which
 * is a class that represents an item in the Minecraft game. The {@link CirrusItem} class adds
 * additional functionality to the {@link ItemStack} class, such as the ability to store an action
 * handler and action arguments, and the ability to apply effects to the display name and lore of
 * the item. The class also provides several static methods for creating instances of the
 * {@link CirrusItem} class with different settings, such as the {@link CirrusItem#of(ItemType)}
 * method, which creates a {@link CirrusItem} with a given item type, and the
 * {@link CirrusItem#ofSkullHash(String)} method, which creates a {@link CirrusItem} with a player
 * head texture. The {@link CirrusItem} class also overrides some methods from the {@link ItemStack}
 * class to provide custom implementations, such as the {@link CirrusItem#displayName()} and
 * {@link CirrusItem#lore()} methods, which suppress the display of adventure components in the
 * item's display name and lore.
 *
 * </p>
 *
 * @author Leonhard Solbach
 */
@Getter
@Setter
@Accessors(fluent = true)
public class CirrusItem extends ItemStack {

  /**
   * A constant representing a CirrusItem with no data.
   */
  public static CirrusItem NO_DATA = CirrusItem.of(null);
  /**
   * The action handler for this CirrusItem.
   */
  private String actionHandler = "noAction";

  /**
   * The slot that this CirrusItem should be set in.
   */
  private transient int slot = -1;
  /**
   * The action arguments for this CirrusItem.
   */
  private List<String> actionArguments = Collections.emptyList();
  /**
   * The display name effect for this CirrusItem.
   */
  @Nullable
  private AbstractMenuEffect<String> displayNameEffect = null;

  /**
   * Constructs a new {@link CirrusItem} with a default item type of STONE.
   */
  public CirrusItem() {
    super(ItemType.STONE);
  }

  /**
   * Constructs a new {@link CirrusItem} from the given {@link BaseItemStack}.
   *
   * @param baseItemStack the base item stack to construct the new {@link CirrusItem} from
   */
  public CirrusItem(BaseItemStack baseItemStack) {
    super(baseItemStack);
  }

  /**
   * Constructs a new {@link CirrusItem} with the given item type and a default amount of 1.
   *
   * @param itemType the item type of the new {@link CirrusItem}
   */
  public CirrusItem(ItemType itemType) {
    this(itemType, 1);
  }

  /**
   * Constructs a new {@link CirrusItem} with the given item type and amount, and a default
   * durability of -1.
   *
   * @param itemType the item type of the new {@link CirrusItem}
   * @param amount   the amount of the new {@link CirrusItem}
   */
  public CirrusItem(ItemType itemType, int amount) {
    this(itemType, amount, (short) -1);
  }

  /**
   * Constructs a new {@link CirrusItem} with the given item type, amount, and durability.
   *
   * @param itemType   the item type of the new {@link CirrusItem}
   * @param amount     the amount of the new {@link CirrusItem}
   * @param durability the durability of the new {@link CirrusItem}
   */
  public CirrusItem(ItemType itemType, int amount, short durability) {
    super(itemType, amount, durability);
  }

  // ----------------------------------------------------------------------------------------------------
  // Static shortcuts
  // ----------------------------------------------------------------------------------------------------

  /**
   * Constructs a new {@link CirrusItem} with the given item type. If the item type is null, a
   * {@link CirrusItem} with no data will be returned.
   *
   * @param itemType the item type of the new {@link CirrusItem}, or null to return a
   *                 {@link CirrusItem} with no data
   * @return the constructed {@link CirrusItem}
   */
  public static CirrusItem of(@Nullable ItemType itemType) {
    return new CirrusItem(itemType);
  }

  /**
   * Constructs a new {@link CirrusItem} with the given skull hash as its texture.
   *
   * @param skullHash the skull hash to use as the texture of the new {@link CirrusItem}
   * @return the constructed {@link CirrusItem}
   */
  public static CirrusItem ofSkullHash(@NonNull String skullHash) {
    return new CirrusItem(ItemType.PLAYER_HEAD).texture(skullHash);
  }

  /**
   * Constructs a new {@link CirrusItem} with the given item type, display name, and lore.
   *
   * @param itemType    the item type of the new {@link CirrusItem}
   * @param displayName the display name of the new {@link CirrusItem}
   * @param lore        the lore of the new {@link CirrusItem}
   * @return the constructed {@link CirrusItem}
   */
  public static CirrusItem of(
      @NonNull ItemType itemType,
      @NonNull ChatElement<?> displayName,
      @NonNull List<ChatElement<?>> lore) {
    return new CirrusItem(itemType).displayName(displayName).loreElements(lore);
  }

  /**
   Creates a new {@link CirrusItem} instance with the specified item type, display name, and lore.
   @param itemType the type of the item
   @param displayName the display name of the item
   @param lore the lore of the item
   @return a new {@link CirrusItem} instance with the specified item type, display name, and lore
   */
  public static CirrusItem of(
      @NonNull ItemType itemType,
      @NonNull ChatElement<?> displayName,
      @NonNull ChatElement<?>... lore) {
    return new CirrusItem(itemType).displayName(displayName).lore(lore);
  }

  /**
   * Constructs a new {@link CirrusItem} with the given item type, display name effect, and lore.
   *
   * @param itemType          the item type of the new {@link CirrusItem}
   * @param displayNameEffect the display name effect to apply to the new {@link CirrusItem}
   * @param lore              the lore of the new {@link CirrusItem}
   * @return the constructed {@link CirrusItem}
   */
  public static CirrusItem of(
      @NonNull ItemType itemType,
      @NonNull AbstractMenuEffect<String> displayNameEffect,
      @NonNull ChatElement<?>... lore) {
    return new CirrusItem(itemType).displayNameEffect(displayNameEffect).lore(lore);
  }

  // ----------------------------------------------------------------------------------------------------
  // Overloaded methods
  // ----------------------------------------------------------------------------------------------------

  /**
   * Sets the lore of this item to the specified list of strings.
   *
   * <p>This method applies color codes to each string in the list before setting it as the lore of the item.
   *
   * <p>Important: If the specified list of strings is null, this method does nothing and returns this CirrusItem.
   *
   * @param lore the list of strings to set as the lore of this item
   * @return this CirrusItem
   */
  public CirrusItem loreElements(@Nullable List<ChatElement<?>> lore) {
    if (lore == null) {
      return this;
    }
    this.lore(lore);
    return this;
  }

  @Override
  public CirrusItem displayName(@Nullable ChatElement<?> displayName) {
    if (displayName == null) {
      return this;
    }
    super.displayName(displayName);
    return this;
  }

  /**
   * Sets the lore of this item to the specified varargs of strings.
   *
   * <p>This method applies color codes to each string in the varargs before setting it as the lore of the item.
   *
   * <p>Important: If the varargs of strings is null, this method does nothing and returns this CirrusItem.
   *
   * @param lore the varargs of strings to set as the lore of this item
   * @return this CirrusItem
   */
  public CirrusItem lore(@NonNull ChatElement<?>... lore) {
    if (lore == null) {
      return this;
    }
    super.lore(Arrays.asList(lore));
    return this;
  }
  // ----------------------------------------------------------------------------------------------------
  // Overridden methods from ItemStack
  // ----------------------------------------------------------------------------------------------------

  /**
   * Returns the display name of this item.
   *
   * <p>This method overrides the super#displayName() method and always uses the more
   * concise 'legacy' string format.
   *
   * <p>The display name may include special effects, such as a scrolling or blinking effect, if the
   * {@link #displayNameEffect}
   * field is set.
   *
   * <p>Note: this method suppresses the "unchecked" warning, as it uses a generic type that may not
   * match the expected type.
   *
   * @return the display name of this item
   */
  // We really don't want crappy adventure components
  @Override
  @SuppressWarnings("unchecked")
  public ChatElement<?> displayName() {
    return super.displayName();
  }

  /**
   * Returns the lore of this item.
   *
   * <p>This method overrides the {@link super#lore()} method and always uses the more concise
   * 'legacy' string format.
   *
   * <p>Note: this method suppresses the "unchecked" warning, as it uses a generic type that may not
   * match the expected type.
   *
   * @return the lore of this item
   */
  // We really don't want crappy adventure components
  @Override
  @SuppressWarnings("unchecked")
  public List<ChatElement<?>> lore() {
    return super.lore();
  }

  @Override
  public CirrusItem deepClone(int protocolVersion) {
    return (CirrusItem) super.deepClone(protocolVersion);
  }

  @Override
  public CirrusItem itemType(@NonNull ItemType itemType) {
    return (CirrusItem) super.itemType(itemType);
  }

  @Override
  public CirrusItem nbtData(@NonNull CompoundTag nbtData) {
    return (CirrusItem) super.nbtData(nbtData);
  }

  /**
   * Returns the type of this item.
   *
   * <p>This method overrides the {@link super#itemType()} method and may return null.
   *
   * @return the type of this item, or null if it is not set
   */
  @Nullable
  @Override
  public ItemType itemType() {
    return super.itemType();
  }

  /**
   * Sets the amount of this item to the specified value.
   *
   * <p>This method overrides the super#amount(byte) method and returns this CirrusItem.
   *
   * @param amount the amount of this item
   * @return this CirrusItem
   */
  @Override
  public CirrusItem amount(byte amount) {
    return (CirrusItem) super.amount(amount);
  }

  /**
   * Sets the durability of this item to the specified value.
   *
   * <p>This method overrides the {@link super#durability(short)} method and returns this CirrusItem.
   *
   * @param durability the durability of this item
   * @return this CirrusItem
   */
  @Override
  public CirrusItem durability(short durability) {
    return (CirrusItem) super.durability(durability);
  }

  /**
   * Sets the hide flags of this item to the specified value.
   *
   * <p>This method overrides the {@link super#hideFlags(int)} method and returns this CirrusItem.
   *
   * @param hideFlags the hide flags of this item
   * @return this CirrusItem
   */
  @Override
  public CirrusItem hideFlags(int hideFlags) {
    return (CirrusItem) super.hideFlags(hideFlags);
  }

  // ----------------------------------------------------------------------------------------------------
  // Convenience methods
  // ----------------------------------------------------------------------------------------------------

  /**
   * Returns the slot that this CirrusItem should be set in.
   *
   * @return the slot that this CirrusItem should be set in
   */
  public int slot() {
    return this.slot;
  }

  /**
   * Sets the slot that this CirrusItem should be set in to the specified value.
   *
   * @param slot the slot that this CirrusItem should be set in
   * @return this CirrusItem
   */
  public CirrusItem slot(int slot) {
    this.slot = slot;
    return this;
  }


  // ----------------------------------------------------------------------------------------------------
  // Utility methods for nbt data
  // ----------------------------------------------------------------------------------------------------

  /**
   * Hides the NBT flags of this CirrusItem.
   *
   * <p>This method uses the {@link Utils#hideNbtFlags(CompoundTag)} method to hide the NBT flags
   * of
   * the item.
   *
   * @return this CirrusItem
   */
  public CirrusItem hideNbtFlags() {
    Utils.hideNbtFlags(this.nbtData());
    return this;
  }

  /**
   * Makes this CirrusItem glow.
   *
   * <p>This method uses the {@link Utils#glow(CompoundTag)} method to make the item glow.
   *
   * @return this CirrusItem
   */
  public CirrusItem glow() {
    Utils.glow(this.nbtData());
    return this;
  }

  /**
   * Sets the texture of this CirrusItem to the specified texture.
   *
   * <p>This method uses the {@link Utils#texture(CompoundTag, String)} method to set the texture
   * of
   * the item. The texture should not be null.
   *
   * @param texture the texture to set for this CirrusItem
   * @return this CirrusItem
   */
  public CirrusItem texture(@NonNull String texture) {
    Utils.texture(this.nbtData(), texture);
    return this;
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * <p>
   * The {@link CirrusItem#equals(Object)} method overrides the {@link Object#equals(Object)} method
   * to compare two {@link CirrusItem} objects for equality. The method returns true if the other
   * object is also a {@link CirrusItem} and all of the following conditions are met:
   *
   * <ul>
   *   <li>The lore of the two objects is equal.</li>
   *   <li>The item type of the two objects is equal.</li>
   *   <li>The action handler of the two objects is equal.</li>
   *   <li>The action arguments of the two objects are equal.</li>
   *   <li>The display name effect of the two objects is equal.</li>
   * </ul>
   * <p>
   * If the other object is not a {@link CirrusItem}, the method returns the result of calling
   * {@link ItemStack#equals(Object)} on the other object, passing this object as the argument.
   *
   * </p>
   *
   * @param o the reference object with which to compare
   * @return true if this object is the same as the obj argument; false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }

    if (o.getClass() == ItemStack.class) {
      return super.equals(o);
    }

    CirrusItem item = (CirrusItem) o;

    if (!Objects.equals(lore(), item.lore())) {
      return false;
    }
    if (!Objects.equals(this.itemType, item.itemType())) {
      return false;
    }
    if (!Objects.equals(this.actionHandler, item.actionHandler())) {
      return false;
    }
    if (!Objects.equals(this.actionArguments, item.actionArguments())) {
      return false;
    }
    return Objects.equals(this.displayNameEffect, item.displayNameEffect);
  }

  /**
   * Returns a string representation of this CirrusItem.
   *
   * <p>The string representation consists of the values of the following fields:
   * <ul>
   * <li>displayName: the display name of the item
   * <li>displayNameEffect: the display name effect of the item, or "null" if it is not set
   * <li>itemType: the type of the item
   * <li>lore: the lore of the item
   * <li>amount: the amount of the item
   * <li>hideFlags: the hide flags of the item
   * <li>actionArguments: the action arguments of the item
   * </ul>
   *
   * <p>The string representation is generated using the {@link ToStringUtil} class.
   *
   * @return a string representation of this CirrusItem
   */

  @Override
  public String toString() {
    return ToStringUtil.of("CirrusItem")
        .add("displayName", this.displayName())
        .add(
            "displayNameEffect",
            this.displayNameEffect == null ? "null" : this.displayNameEffect.toString()
            )
        .add("itemType", this.itemType)
        .add("lore", this.lore())
        .add("amount", this.amount)
        .add("hideFlags", this.hideFlags)
        .add("actionArguments", this.actionArguments)
        .toString();
  }

  /**
   * Returns a hash code value for the object.
   *
   * <p>
   * The {@link CirrusItem#hashCode()} method overrides the {@link Object#hashCode()} method to
   * calculate the hash code for a {@link CirrusItem} object. The method returns the result of
   * calling the {@link Objects#hash(Object...)} method, passing the item type, amount, durability,
   * NBT data, action handler, and lore JSON of this object as arguments.
   *
   * </p>
   *
   * @return a hash code value for this object
   */
  @Override
  public int hashCode() {
    return Objects.hash(
        this.itemType,
        this.amount,
        this.durability,
        this.nbtData,
        this.actionHandler(),
        this.lore());
  }
}
