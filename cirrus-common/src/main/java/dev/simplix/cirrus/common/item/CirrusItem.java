package dev.simplix.cirrus.common.item;

import dev.simplix.cirrus.common.Utils;
import dev.simplix.cirrus.common.effect.AbstractMenuAnimation;
import dev.simplix.protocolize.data.ItemType;
import lombok.*;
import lombok.experimental.Accessors;
import net.querz.nbt.tag.CompoundTag;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

@Data
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CirrusItem {

    @Builder.Default
    @NonNull
    private String displayName = "Example-DisplayName";
    @Nullable
    @Builder.Default
    private AbstractMenuAnimation<String> titleEffect = null;
    @Builder.Default
    private List<String> lore = Collections.emptyList();
    @Nullable
    @Builder.Default
    private Supplier<List<String>> loreSupplier = null;
    @Builder.Default
    private ItemType itemType = ItemType.STONE;
    @Builder.Default
    private byte amount = 1;
    @Builder.Default
    private short durability = -1;
    @Builder.Default
    private String actionHandler = "noAction";
    @Builder.Default
    private List<String> actionArguments = Collections.emptyList();
    @Builder.Default
    private int[] slots = new int[0];
    @NonNull
    private CompoundTag nbt;

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

    public CirrusItem lore(List<String> lines) {
        this.lore = lines;
        return this;
    }

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
}
