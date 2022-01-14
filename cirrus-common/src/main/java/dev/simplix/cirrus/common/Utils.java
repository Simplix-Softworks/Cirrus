package dev.simplix.cirrus.common;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.chat.BaseComponent;
import net.querz.nbt.tag.*;

@UtilityClass
public class Utils {
    private static final String STEVE_TEXTURE = "ewogICJ0aW1lc3RhbXAiIDogMTU5MTU3NDcyMzc4MywKICAicHJvZmlsZUlkIiA6ICI4NjY3YmE3MWI4NWE0MDA0YWY1NDQ1N2E5NzM0ZWVkNyIsCiAgInByb2ZpbGVOYW1lIiA6ICJTdGV2ZSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82ZDNiMDZjMzg1MDRmZmMwMjI5Yjk0OTIxNDdjNjlmY2Y1OWZkMmVkNzg4NWY3ODUwMjE1MmY3N2I0ZDUwZGUxIgogICAgfSwKICAgICJDQVBFIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS85NTNjYWM4Yjc3OWZlNDEzODNlNjc1ZWUyYjg2MDcxYTcxNjU4ZjIxODBmNTZmYmNlOGFhMzE1ZWE3MGUyZWQ2IgogICAgfQogIH0KfQ==";

    public void removeItalic(BaseComponent[] components) {
        for (BaseComponent component : components) {
            component.setItalic(false);
        }
    }

    public static void hideNbtFlags(CompoundTag tag) {
        tag.put("HideFlags", new IntTag(127));
    }

    public static void glow(CompoundTag tag) {
        hideNbtFlags(tag);

        final ListTag<CompoundTag> enchantments = new ListTag<>(CompoundTag.class);
        final ListTag<CompoundTag> enchs = new ListTag<>(CompoundTag.class);

        final CompoundTag exampleEnchantment = new CompoundTag();

        exampleEnchantment.put("id", new StringTag("minecraft:efficiency"));
        exampleEnchantment.put("lvl", new ShortTag((short) 1));

        final CompoundTag exampleEnch = new CompoundTag();
        exampleEnch.put("id", new ShortTag((short) 1));
        exampleEnch.put("lvl", new ShortTag((short) 1));

        enchantments.add(exampleEnchantment);
        enchs.add(exampleEnch);

        tag.put("ench", enchs);
        tag.put("Enchantments", enchantments);
    }

    public static void texture(CompoundTag tag, String textureHash) {
        if (!(tag.get("SkullOwner") instanceof CompoundTag)) {
            tag.put("SkullOwner", new CompoundTag());
        }

        CompoundTag skullOwner = tag.getCompoundTag("SkullOwner");

        if (skullOwner == null) {
            skullOwner = new CompoundTag();
        }

        skullOwner.put("Name", new StringTag(textureHash));
        CompoundTag properties = skullOwner.getCompoundTag("Properties");
        if (properties == null) {
            properties = new CompoundTag();
        }

        CompoundTag texture = new CompoundTag();
        texture.put(
                "Value",
                new StringTag(textureHash.isEmpty() ? STEVE_TEXTURE : textureHash));
        ListTag<CompoundTag> textures = new ListTag<>(CompoundTag.class);
        textures.add(texture);
        properties.put("textures", textures);
        skullOwner.put("Properties", properties);
        tag.put("SkullOwner", skullOwner);

    }

    public static int calculateSizeForContent(final int items) {
        return (
                items <= 9 ?
                        9 * 1 : items <= 9 * 2 ? 9 * 2 : items <= 9 * 3 ? 9 * 3
                        : items <= 9 * 4 ? 9 * 4 : 9 * 5) + 9;
    }

}
