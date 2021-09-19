package dev.simplix.cirrus.api.model;

import dev.simplix.cirrus.api.i18n.LocalizedString;
import dev.simplix.cirrus.api.i18n.LocalizedStringList;
import dev.simplix.protocolize.data.ItemType;
import lombok.*;
import lombok.experimental.Accessors;
import net.querz.nbt.tag.CompoundTag;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@Accessors(fluent = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemStackModel {

    private LocalizedString displayName;
    private LocalizedStringList lore;
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
    private CompoundTag nbt;

}
