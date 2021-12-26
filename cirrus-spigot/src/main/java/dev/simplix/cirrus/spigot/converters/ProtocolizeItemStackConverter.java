package dev.simplix.cirrus.spigot.converters;


import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.simplix.cirrus.common.Utils;
import dev.simplix.cirrus.common.converter.Converter;
import dev.simplix.cirrus.common.converter.Converters;
import dev.simplix.cirrus.spigot.util.ProtocolVersionUtil;
import dev.simplix.cirrus.spigot.util.ReflectionClasses;
import dev.simplix.cirrus.spigot.util.ReflectionUtil;
import dev.simplix.protocolize.api.item.ItemStack;
import lombok.NonNull;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.querz.nbt.tag.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static dev.simplix.protocolize.api.util.ProtocolVersions.MINECRAFT_1_13;
import static dev.simplix.protocolize.api.util.ProtocolVersions.MINECRAFT_1_14;

public class ProtocolizeItemStackConverter implements Converter<ItemStack, org.bukkit.inventory.ItemStack> {

    private static Class<?> craftItemStackClass;
    private static Class<?> nbtTagCompoundClass;
    private static Class<?> itemStackNMSClass;
    private static Method nmsCopyMethod;
    private static Method bukkitCopyMethod;
    private static Method setTagMethod;

    static {
        try {
            craftItemStackClass = ReflectionUtil.getClass("{obc}.inventory.CraftItemStack");
            nbtTagCompoundClass = ReflectionClasses.nbtTagCompound();
            itemStackNMSClass = ReflectionClasses.itemStackClass();
            nmsCopyMethod = craftItemStackClass.getMethod(
                    "asNMSCopy",
                    org.bukkit.inventory.ItemStack.class);
            bukkitCopyMethod = craftItemStackClass.getMethod("asBukkitCopy", itemStackNMSClass);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public org.bukkit.inventory.ItemStack convert(@NonNull ItemStack src) {
        if (src.itemType() == null) {
            return null;
        }
        if (src.itemType() == null) {
            return new org.bukkit.inventory.ItemStack(Material.AIR);
        }
        MaterialData data = Converters.convert(src.itemType(), MaterialData.class);
        org.bukkit.inventory.ItemStack out;
        if (ProtocolVersionUtil.serverProtocolVersion() < MINECRAFT_1_13) {
            out = new org.bukkit.inventory.ItemStack(
                    data.getItemType(),
                    src.amount(),
                    src.durability(),
                    data.getData());
        } else {
            out = new org.bukkit.inventory.ItemStack(
                    data.getItemType(),
                    src.amount(),
                    src.durability());
        }

        if (src.nbtData() == null) {
            src.nbtData(new CompoundTag());
        }

        String textureHashToInsert = null;

        if (src.nbtData() != null) {
            CompoundTag tag = src.nbtData();
            if (tag.containsKey("SkullOwner") && tag.get("SkullOwner") instanceof CompoundTag) {

                final CompoundTag skullOwnerTag = tag.getCompoundTag("SkullOwner");
                final Tag<?> propertiesRaw = skullOwnerTag.get("Properties");


                if (propertiesRaw instanceof CompoundTag) {
                    try {
                        final ListTag<CompoundTag> textures = (ListTag<CompoundTag>) ((CompoundTag) propertiesRaw)
                                .getListTag("textures");
                        textureHashToInsert = textures.get(0).getString("Value");
                    } catch (final Exception ignored) {

                    }
                }
            }
        }

        writeDataToNbt(src);

        // Finalizing the itemstack by inserting nbt data & hiding attributes
        try {
            Object nmsItemStack = nmsCopyMethod.invoke(null, out);
            if (src.nbtData() != null) {
                try {

                    Method setTag = method();
                    ;
                    final CompoundTag nbtTag = src.nbtData().clone();
                    if (textureHashToInsert != null) {
                        nbtTag.remove("SkullOwner");
                    }

                    setTag.invoke(nmsItemStack, Converters.convert(nbtTag, nbtTagCompoundClass));
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
            
            final org.bukkit.inventory.ItemStack itemStackCopy = (org.bukkit.inventory.ItemStack) bukkitCopyMethod
                    .invoke(null, nmsItemStack);

            mutateMetaDataToHideAttributes(itemStackCopy);
            if (textureHashToInsert == null) {
                return itemStackCopy;
            }

            final SkullMeta meta = (SkullMeta) itemStackCopy.getItemMeta();
            mutateItemMetaForTextureHash(meta, textureHashToInsert);
            itemStackCopy.setItemMeta(meta);
            return itemStackCopy;
        } catch (final Exception exception) {
            exception.printStackTrace(); // Setting nbt to nms item is also pain in the ass
        }

        return out;
    }

    private Method method() throws NoSuchMethodException {
        if (setTagMethod != null) {
            return setTagMethod;
        }
        Method setTag;
        try {
            setTag = itemStackNMSClass.getMethod("setTag", nbtTagCompoundClass);
        } catch (NoSuchMethodException e) {
            setTag = itemStackNMSClass.getDeclaredMethod("setTagClone", nbtTagCompoundClass);
            setTag.setAccessible(true);
        }
        return setTagMethod = setTag;
    }

    private void mutateMetaDataToHideAttributes(org.bukkit.inventory.ItemStack out) {
        try {
            final ItemMeta itemMeta = out.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

            out.setItemMeta(itemMeta);
        } catch (Throwable ignored) {
        }
    }

    private GameProfile makeProfile(@NonNull String textureHash) {
        // random uuid based on the textureHash string
        UUID id = new UUID(
                textureHash.substring(textureHash.length() - 20).hashCode(),
                textureHash.substring(textureHash.length() - 10).hashCode()
        );
        GameProfile profile = new GameProfile(id, "Player");
        profile.getProperties().put("textures", new Property("textures", textureHash));
        return profile;
    }

    private void mutateItemMetaForTextureHash(SkullMeta meta, String textureHash) {
        try {
            Method metaSetProfileMethod = meta
                    .getClass()
                    .getDeclaredMethod("setProfile", GameProfile.class);
            metaSetProfileMethod.setAccessible(true);
            metaSetProfileMethod.invoke(meta, makeProfile(textureHash));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            // if in an older API where there is no setProfile method,
            // we set the profile field directly.
            try {
                Field profileField = meta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(meta, makeProfile(textureHash));

            } catch (NoSuchFieldException | IllegalAccessException exception) {
                exception.printStackTrace();
            }
        }
    }

    private void writeDataToNbt(@NonNull ItemStack stack) {
        if (stack.displayName() != null) {
            if (ProtocolVersionUtil.serverProtocolVersion() >= MINECRAFT_1_13) {
                stack.nbtData().put("Damage", new IntTag(stack.durability()));
                final BaseComponent[] baseComponents = stack.displayName();
                Utils.removeItalic(baseComponents);
                setDisplayNameTag(stack.nbtData(), ComponentSerializer.toString(baseComponents));
            } else {
                setDisplayNameTag(stack.nbtData(),
                        TextComponent.toLegacyText(stack.displayName()));
            }
        }

        if (stack.lore() != null) {
            setLoreTag(stack.nbtData(),
                    stack.lore(),
                    ProtocolVersionUtil.serverProtocolVersion());
        }
    }

    private void setDisplayNameTag(@NonNull CompoundTag nbtData, @NonNull String name) {
        if (name == null) {
            return;
        }
        CompoundTag display = (CompoundTag) nbtData.get("display");
        if (display == null) {
            display = new CompoundTag();
        }
        final StringTag tag = new StringTag(name);
        display.put("Name", tag);
        nbtData.put("display", display);
    }

    private void setLoreTag(
            @NonNull CompoundTag nbtData,
            @NonNull List<BaseComponent[]> lore,
            int protocolVersion) {
        if (lore == null) {
            return;
        }
        CompoundTag display = (CompoundTag) nbtData.get("display");
        if (display == null) {
            display = new CompoundTag();
        }
        if (protocolVersion < MINECRAFT_1_14) {
            final ListTag<StringTag> tag = new ListTag<>(StringTag.class);
            tag.addAll(lore.stream().map(i -> new StringTag(TextComponent.toLegacyText(i))).collect(
                    Collectors.toList()));
            display.put("Lore", tag);
            nbtData.put("display", display);
        } else {
            final ListTag<StringTag> tag = new ListTag<>(StringTag.class);
            tag.addAll(lore.stream().map(components -> {
                for (BaseComponent component : components) {
                    if (!component.isItalic()) {
                        component.setItalic(false);
                    }
                }
                return new StringTag(ComponentSerializer.toString(components));
            }).collect(Collectors.toList()));
            display.put("Lore", tag);
            nbtData.put("display", display);
        }
    }

}