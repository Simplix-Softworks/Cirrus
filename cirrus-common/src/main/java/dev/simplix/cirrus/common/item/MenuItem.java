package dev.simplix.cirrus.common.item;

import dev.simplix.cirrus.common.Utils;
import dev.simplix.cirrus.common.effect.AbstractMenuEffect;
import dev.simplix.protocolize.data.ItemType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;
import net.querz.nbt.tag.CompoundTag;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

@Data
@Builder
@Accessors(fluent = true)
public class MenuItem {

    @Builder.Default
    @NonNull
    private String displayName = "DisplayName";
    @Nullable
    @Builder.Default
    private AbstractMenuEffect<String> titleEffect = null;
    @Builder.Default
    private List<String> lore = Collections.emptyList();
    @Nullable

    @Builder.Default
    private Supplier<List<String>> loreSupplier = null;
    private ItemType itemType;
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

    public static MenuItem of(String name, ItemType type) {
        return builder().displayName(name).itemType(type).build();
    }

    public String displayNam() {
        if (this.titleEffect != null) {
            return this.titleEffect.next();
        }
        return this.displayName;
    }

    public MenuItem glow() {
        Utils.glow(this.nbt);
        return this;
    }

    public MenuItem texture(@NonNull String texture) {
        Utils.texture(this.nbt, texture);
        return this;
    }

    public MenuItem copy() {
        return builder()
                .displayName(this.displayName)
                .lore(this.lore)
                .itemType(this.itemType)
                .amount(this.amount)
                .durability(this.durability)
                .actionHandler(this.actionHandler)
                .slots(this.slots)
                .nbt(this.nbt.clone())
                .build();
    }
}
