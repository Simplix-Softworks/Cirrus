package dev.simplix.cirrus.common.i18n;

import dev.simplix.protocolize.data.ItemType;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import net.querz.nbt.tag.CompoundTag;

@Data
@Builder
@Accessors(fluent = true)
public class LocalizedItemStackModel {

    @Builder.Default
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
    private CompoundTag nbt;

}
