package dev.simplix.cirrus.common.i18n;

import dev.simplix.cirrus.common.Utils;
import dev.simplix.cirrus.common.item.MenuItem;
import dev.simplix.protocolize.data.ItemType;
import lombok.*;
import lombok.experimental.Accessors;
import net.querz.nbt.tag.CompoundTag;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Data
@Builder
@Accessors(fluent = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LocalizedItemStackModel {

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
    @NonNull
    private CompoundTag nbt = new CompoundTag();

    public LocalizedItemStackModel glow() {
        Utils.glow(this.nbt);
        return this;
    }

    public LocalizedItemStackModel texture(@NonNull String texture) {
        Utils.texture(this.nbt, texture);
        return this;
    }

    public MenuItem localize(Locale locale, String... replacements) {
        return Localizer.localize(
                this,
                locale,
                replacements
        );
    }

}
