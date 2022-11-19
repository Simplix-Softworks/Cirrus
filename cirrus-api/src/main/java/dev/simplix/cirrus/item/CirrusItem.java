package dev.simplix.cirrus.item;

import dev.simplix.cirrus.Utils;
import dev.simplix.cirrus.effect.AbstractMenuEffect;
import dev.simplix.cirrus.util.ToStringUtil;
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
 * @author Leonhard Solbach
 */
@Getter
@Setter
@Accessors(fluent = true)
public class CirrusItem extends ItemStack {

  public static CirrusItem NO_DATA = CirrusItem.of(null);
  private String actionHandler = "noAction";

  private transient int slot = -1;
  private List<String> actionArguments = Collections.emptyList();
  @Nullable
  private AbstractMenuEffect<String> displayNameEffect = null;

  public CirrusItem() {
    super(ItemType.STONE);
  }

  public CirrusItem(BaseItemStack baseItemStack) {

    super(baseItemStack);
  }

  public CirrusItem(ItemType itemType) {

    this(itemType, 1);
  }

  public CirrusItem(ItemType itemType, int amount) {
    this(itemType, amount, (short) -1);
  }

  public CirrusItem(ItemType itemType, int amount, short durability) {
    super(itemType, amount, durability);
  }

  // ----------------------------------------------------------------------------------------------------
  // Static shortcuts
  // ----------------------------------------------------------------------------------------------------

  public static CirrusItem of(@Nullable ItemType itemType) {
    return new CirrusItem(itemType);
  }

  public static CirrusItem ofSkullHash(@NonNull String skullHash) {
    return new CirrusItem(ItemType.PLAYER_HEAD).texture(skullHash);
  }

  public static CirrusItem of(
      @NonNull ItemType itemType,
      @NonNull String displayName,
      @NonNull List<String> lore) {
    return new CirrusItem(itemType).displayName(displayName).lore(lore);
  }

  public static CirrusItem of(
      @NonNull ItemType itemType,
      @NonNull String displayName,
      @NonNull String... lore) {
    return new CirrusItem(itemType).displayName(displayName).lore(lore);
  }

  public static CirrusItem of(
      @NonNull ItemType itemType,
      @NonNull AbstractMenuEffect<String> displayNameEffect,
      @NonNull String... lore) {
    return new CirrusItem(itemType).displayNameEffect(displayNameEffect).lore(lore);
  }

  // ----------------------------------------------------------------------------------------------------
  // Overloaded methods
  // ----------------------------------------------------------------------------------------------------

  public CirrusItem lore(@NonNull String... lore) {
    if (lore == null) {
      return this;
    }

    this.lore(Arrays.asList(lore), true);
    return this;
  }

  public CirrusItem lore(@Nullable List<String> lore) {
    if (lore == null) {
      return this;
    }
    this.lore(Utils.colorize(lore), true);
    return this;
  }
  // ----------------------------------------------------------------------------------------------------
  // Overridden methods from ItemStack
  // ----------------------------------------------------------------------------------------------------

  @Nullable
  @Override
  public ItemType itemType() {
    return super.itemType();
  }

  // We really don't want crappy adventure components
  @Override
  @SuppressWarnings("unchecked")
  public String displayName() {
    return this.displayName(true);
  }

  // We really don't want crappy adventure components
  @Override
  @SuppressWarnings("unchecked")
  public List<String> lore() {
    return super.lore(true);
  }

  // We still really don't want crappy adventure components
  @Override
  @SuppressWarnings("unchecked")
  public <T> T displayName(boolean legacyString) {
    if (this.displayNameEffect != null) {
      final String next = this.displayNameEffect.nextFrame();
      return (T) (legacyString ? next : CONVERTER.fromLegacyText(next));
    }

    return super.displayName(legacyString);
  }

  @Override
  public CirrusItem displayName(@Nullable String legacyName) {
    if (legacyName == null || legacyName.isEmpty()) {
      return this;
    }
    return (CirrusItem) super.displayName(Utils.colorize(legacyName));
  }

  @Override
  public CirrusItem displayName(Object displayName) {
    return (CirrusItem) super.displayName(displayName);
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

  @Override
  public CirrusItem amount(byte amount) {
    return (CirrusItem) super.amount(amount);
  }

  @Override
  public CirrusItem durability(short durability) {
    return (CirrusItem) super.durability(durability);
  }

  @Override
  public CirrusItem hideFlags(int hideFlags) {
    return (CirrusItem) super.hideFlags(hideFlags);
  }

  // ----------------------------------------------------------------------------------------------------
  // Convenience methods
  // ----------------------------------------------------------------------------------------------------

  public int slot() {
    return this.slot;
  }

  public CirrusItem slot(int slot) {
    this.slot = slot;
    return this;
  }

  // ----------------------------------------------------------------------------------------------------
  // Utility methods for nbt data
  // ----------------------------------------------------------------------------------------------------

  public CirrusItem hideNbtFlags() {
    Utils.hideNbtFlags(this.nbtData());
    return this;
  }

  public CirrusItem glow() {
    Utils.glow(this.nbtData());
    return this;
  }

  public CirrusItem texture(@NonNull String texture) {
    Utils.texture(this.nbtData(), texture);
    return this;
  }

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

  @Override
  public int hashCode() {
    return Objects.hash(
        this.itemType,
        this.amount,
        this.durability,
        this.nbtData,
        this.actionHandler(),
        this.loreJson());
  }
}
