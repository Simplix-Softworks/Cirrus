package dev.simplix.cirrus.common.item;

import dev.simplix.cirrus.common.Utils;
import dev.simplix.cirrus.common.effect.AbstractMenuAnimation;
import dev.simplix.protocolize.data.ItemType;
import lombok.NonNull;
import lombok.experimental.Accessors;
import net.querz.nbt.tag.CompoundTag;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

@Accessors(fluent = true)
public class CirrusItem {

    @NonNull
    private String displayName = "Example-DisplayName";
    @Nullable
    private AbstractMenuAnimation<String> titleEffect = null;
    private List<String> lore = Collections.emptyList();
    @Nullable
    private Supplier<List<String>> loreSupplier = null;
    private ItemType itemType = ItemType.STONE;
    private byte amount = 1;
    private short durability = -1;
    private String actionHandler = "noAction";
    private List<String> actionArguments = Collections.emptyList();
    private int[] slots = new int[0];
    @NonNull
    private CompoundTag nbt;

    private CirrusItem(@NonNull String displayName, @Nullable AbstractMenuAnimation<String> titleEffect, List<String> lore, @Nullable Supplier<List<String>> loreSupplier, ItemType itemType, byte amount, short durability, String actionHandler, List<String> actionArguments, int[] slots, @NonNull CompoundTag nbt) {
        this.displayName = displayName;
        this.titleEffect = titleEffect;
        this.lore = lore;
        this.loreSupplier = loreSupplier;
        this.itemType = itemType;
        this.amount = amount;
        this.durability = durability;
        this.actionHandler = actionHandler;
        this.actionArguments = actionArguments;
        this.slots = slots;
        this.nbt = nbt;
    }

    private CirrusItem() {
    }

    // ----------------------------------------------------------------------------------------------------
    // Instancing
    // ----------------------------------------------------------------------------------------------------

    public static CirrusItem of(String name, ItemType type) {
        return new CirrusItem().displayName(name).itemType(type);
    }

    public static CirrusItem of(String name) {
        return of(name, ItemType.STONE);
    }

    public static CirrusItem animated(AbstractMenuAnimation<String> effect, ItemType type) {
        return new CirrusItem().itemType(type).titleEffect(effect);
    }

    public static CirrusItem animated(AbstractMenuAnimation<String> effect) {
        return animated(effect, ItemType.STONE);
    }

    // ----------------------------------------------------------------------------------------------------
    // Chained setters
    // ----------------------------------------------------------------------------------------------------

    public CirrusItem lore(String... lines) {
        this.lore = Arrays.asList(lines);
        return this;
    }

    public CirrusItem slot(int... slots) {
        this.slots = slots;
        return this;
    }

    public String displayNam() {
        if (this.titleEffect != null) {
            return this.titleEffect.next();
        }
        return this.displayName;
    }

    public CirrusItem glow() {
        Utils.glow(this.nbt);
        return this;
    }

    public CirrusItem texture(@NonNull String texture) {
        Utils.texture(this.nbt, texture);
        return this;
    }

    public CirrusItem copy() {
        return new CirrusItem(
                this.displayName,
                this.titleEffect,
                this.lore,
                this.loreSupplier,
                this.itemType,
                this.amount,
                this.durability,
                this.actionHandler,
                this.actionArguments,
                this.slots,
                this.nbt
        );
    }

    public @NonNull String displayName() {
        return this.displayName;
    }

    public @Nullable AbstractMenuAnimation<String> titleEffect() {
        return this.titleEffect;
    }

    public List<String> lore() {
        return this.lore;
    }

    public @Nullable Supplier<List<String>> loreSupplier() {
        return this.loreSupplier;
    }

    public ItemType itemType() {
        return this.itemType;
    }

    public byte amount() {
        return this.amount;
    }

    public short durability() {
        return this.durability;
    }

    public String actionHandler() {
        return this.actionHandler;
    }

    public List<String> actionArguments() {
        return this.actionArguments;
    }

    public int[] slots() {
        return this.slots;
    }

    public @NonNull CompoundTag nbt() {
        return this.nbt;
    }

    public CirrusItem displayName(@NonNull String displayName) {
        this.displayName = displayName;
        return this;
    }

    public CirrusItem titleEffect(@Nullable AbstractMenuAnimation<String> titleEffect) {
        this.titleEffect = titleEffect;
        return this;
    }

    public CirrusItem lore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public CirrusItem loreSupplier(@Nullable Supplier<List<String>> loreSupplier) {
        this.loreSupplier = loreSupplier;
        return this;
    }

    public CirrusItem itemType(ItemType itemType) {
        this.itemType = itemType;
        return this;
    }

    public CirrusItem amount(byte amount) {
        this.amount = amount;
        return this;
    }

    public CirrusItem durability(short durability) {
        this.durability = durability;
        return this;
    }

    public CirrusItem actionHandler(String actionHandler) {
        this.actionHandler = actionHandler;
        return this;
    }

    public CirrusItem actionArguments(List<String> actionArguments) {
        this.actionArguments = actionArguments;
        return this;
    }

    public CirrusItem slots(int[] slots) {
        this.slots = slots;
        return this;
    }

    public CirrusItem nbt(@NonNull CompoundTag nbt) {
        this.nbt = nbt;
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CirrusItem)) {
            return false;
        }
        final CirrusItem other = (CirrusItem) o;
        if (!other.canEqual((Object) this)) {
            return false;
        }
        final Object this$displayName = this.displayName();
        final Object other$displayName = other.displayName();
        if (this$displayName == null ? other$displayName != null : !this$displayName.equals(other$displayName)) {
            return false;
        }
        final Object this$titleEffect = this.titleEffect();
        final Object other$titleEffect = other.titleEffect();
        if (this$titleEffect == null ? other$titleEffect != null : !this$titleEffect.equals(other$titleEffect)) {
            return false;
        }
        final Object this$lore = this.lore();
        final Object other$lore = other.lore();
        if (this$lore == null ? other$lore != null : !this$lore.equals(other$lore)) {
            return false;
        }
        final Object this$loreSupplier = this.loreSupplier();
        final Object other$loreSupplier = other.loreSupplier();
        if (this$loreSupplier == null ? other$loreSupplier != null : !this$loreSupplier.equals(other$loreSupplier)) {
            return false;
        }
        final Object this$itemType = this.itemType();
        final Object other$itemType = other.itemType();
        if (this$itemType == null ? other$itemType != null : !this$itemType.equals(other$itemType)) {
            return false;
        }
        if (this.amount() != other.amount()) {
            return false;
        }
        if (this.durability() != other.durability()) {
            return false;
        }
        final Object this$actionHandler = this.actionHandler();
        final Object other$actionHandler = other.actionHandler();
        if (this$actionHandler == null ? other$actionHandler != null : !this$actionHandler.equals(other$actionHandler)) {
            return false;
        }
        final Object this$actionArguments = this.actionArguments();
        final Object other$actionArguments = other.actionArguments();
        if (this$actionArguments == null ? other$actionArguments != null : !this$actionArguments.equals(other$actionArguments)) {
            return false;
        }
        if (!Arrays.equals(this.slots(), other.slots())) {
            return false;
        }
        final Object this$nbt = this.nbt();
        final Object other$nbt = other.nbt();
        if (this$nbt == null ? other$nbt != null : !this$nbt.equals(other$nbt)) {
            return false;
        }
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CirrusItem;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $displayName = this.displayName();
        result = result * PRIME + ($displayName == null ? 43 : $displayName.hashCode());
        final Object $titleEffect = this.titleEffect();
        result = result * PRIME + ($titleEffect == null ? 43 : $titleEffect.hashCode());
        final Object $lore = this.lore();
        result = result * PRIME + ($lore == null ? 43 : $lore.hashCode());
        final Object $loreSupplier = this.loreSupplier();
        result = result * PRIME + ($loreSupplier == null ? 43 : $loreSupplier.hashCode());
        final Object $itemType = this.itemType();
        result = result * PRIME + ($itemType == null ? 43 : $itemType.hashCode());
        result = result * PRIME + this.amount();
        result = result * PRIME + this.durability();
        final Object $actionHandler = this.actionHandler();
        result = result * PRIME + ($actionHandler == null ? 43 : $actionHandler.hashCode());
        final Object $actionArguments = this.actionArguments();
        result = result * PRIME + ($actionArguments == null ? 43 : $actionArguments.hashCode());
        result = result * PRIME + Arrays.hashCode(this.slots());
        final Object $nbt = this.nbt();
        result = result * PRIME + ($nbt == null ? 43 : $nbt.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "CirrusItem(displayName=" + this.displayName() + ", titleEffect=" + this.titleEffect() + ", lore=" + this.lore() + ", loreSupplier=" + this.loreSupplier() + ", itemType=" + this.itemType() + ", amount=" + this.amount() + ", durability=" + this.durability() + ", actionHandler=" + this.actionHandler() + ", actionArguments=" + this.actionArguments() + ", slots=" + Arrays.toString(this.slots()) + ", nbt=" + this.nbt() + ")";
    }
}
