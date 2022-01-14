package dev.simplix.cirrus.common.item;

import dev.simplix.cirrus.common.Utils;
import dev.simplix.protocolize.data.ItemType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;
import net.querz.nbt.tag.CompoundTag;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@Accessors(fluent = true)
public class MenuItem {

    @Builder.Default
    @NonNull
    private String displayName = "DisplayName";
    @Builder.Default
    private List<String> lore = Collections.emptyList();
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

    public MenuItem glow() {
        Utils.glow(nbt);
        return this;
    }

    public MenuItem texture(@NonNull String texture) {
        Utils.texture(nbt, texture);
        return this;
    }

    public MenuItem copy() {
        return builder()
                .displayName(displayName)
                .lore(lore)
                .itemType(itemType)
                .amount(amount)
                .durability(durability)
                .actionHandler(actionHandler)
                .slots(slots)
                .nbt(nbt.clone())
                .build();
    }
}
