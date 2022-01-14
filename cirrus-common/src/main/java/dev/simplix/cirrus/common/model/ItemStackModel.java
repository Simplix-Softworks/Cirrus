package dev.simplix.cirrus.common.model;

import dev.simplix.cirrus.common.i18n.LocalizedString;
import dev.simplix.cirrus.common.i18n.LocalizedStringList;
import dev.simplix.protocolize.data.ItemType;
import java.util.*;
import lombok.*;
import lombok.experimental.Accessors;
import net.querz.nbt.tag.CompoundTag;

@Data
@Builder
@Accessors(fluent = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemStackModel {

    @Builder.Default
    private LocalizedString displayName = new LocalizedString(new HashMap<>());
    @Builder.Default
    private LocalizedStringList lore = new LocalizedStringList(new HashMap<>());
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
    @Builder.Default
    private CompoundTag nbt = new CompoundTag();

}
