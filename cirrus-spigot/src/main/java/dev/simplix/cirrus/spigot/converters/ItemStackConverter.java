package dev.simplix.cirrus.spigot.converters;

import static dev.simplix.protocolize.api.util.ProtocolVersions.MINECRAFT_1_13;
import static dev.simplix.protocolize.api.util.ProtocolVersions.MINECRAFT_1_14;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.simplix.cirrus.Cirrus;
import dev.simplix.cirrus.Utils;
import dev.simplix.cirrus.spigot.util.*;
import dev.simplix.protocolize.api.item.BaseItemStack;

import java.lang.reflect.*;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.querz.nbt.tag.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

@Slf4j
public class ItemStackConverter implements Function<BaseItemStack, org.bukkit.inventory.ItemStack> {

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
  public org.bukkit.inventory.ItemStack apply(BaseItemStack protocolizeItemStack) {
    if (protocolizeItemStack.itemType() == null) {
      return new org.bukkit.inventory.ItemStack(Material.AIR);
    }

    Material material = Cirrus
        .service(ItemTypeMaterialConverter.class).apply(protocolizeItemStack.itemType());
    org.bukkit.inventory.ItemStack itemStack;

    if (ProtocolVersionUtil.serverProtocolVersion() < MINECRAFT_1_13) {
      itemStack = new org.bukkit.inventory.ItemStack(
          material,
          protocolizeItemStack.amount(),
          protocolizeItemStack.durability(),
          (byte) 0);
    } else {
      itemStack = new org.bukkit.inventory.ItemStack(
          Cirrus.service(ItemTypeMaterialConverter.class).apply(protocolizeItemStack.itemType()),
          protocolizeItemStack.amount(),
          protocolizeItemStack.durability());
    }

    if (protocolizeItemStack.nbtData() == null) {
      protocolizeItemStack.nbtData(new CompoundTag());
    }

    String textureHashToInsert = null;

    if (protocolizeItemStack.nbtData() != null && !protocolizeItemStack
        .nbtData()
        .keySet()
        .isEmpty()) {
      CompoundTag tag = protocolizeItemStack.nbtData();
      if (tag.containsKey("SkullOwner") && tag.get("SkullOwner") instanceof CompoundTag) {

        final CompoundTag skullOwnerTag = tag.getCompoundTag("SkullOwner");
        final Tag<?> propertiesRaw = skullOwnerTag.get("Properties");

        if (propertiesRaw instanceof CompoundTag) {
          final ListTag<CompoundTag> textures = (ListTag<CompoundTag>) ((CompoundTag) propertiesRaw)
              .getListTag("textures");
          textureHashToInsert = textures.get(0).getString("Value");
        }
      }
    }

    writeLoreAndDisplayNameToStack(protocolizeItemStack);

    // Finalizing the itemstack by inserting nbt material & hiding attributes
    try {
      Object nmsItemStack = nmsCopyMethod.invoke(null, itemStack);
      if (protocolizeItemStack.nbtData() != null && !protocolizeItemStack
          .nbtData()
          .keySet()
          .isEmpty()) {
        try {

          Method setTag = setTagMethod();

          final CompoundTag nbtTag = protocolizeItemStack.nbtData().clone();
          if (textureHashToInsert != null) {
            nbtTag.remove("SkullOwner");
          }

          setTag.invoke(nmsItemStack, Cirrus.service(QuerzNbtNmsNbtConverter.class).apply(nbtTag));
        } catch (Throwable throwable) {
          log.error("Error while setting nbt tag", throwable);
        }
      }

      final ItemStack result = (org.bukkit.inventory.ItemStack) bukkitCopyMethod
          .invoke(null, nmsItemStack);

      final ItemMeta itemMeta = result.getItemMeta();
      mutateMetaDataToHideAttributes(itemMeta);

      // Apply special 'precautions' against NMS.
      result.setType(material);
      itemMeta.setDisplayName(Utils.colorize((String) protocolizeItemStack.displayName(true)));

      // No texture-hash to insert
      if (textureHashToInsert != null && itemMeta instanceof SkullMeta skullMeta) {
        mutateItemMetaForTextureHash(skullMeta, textureHashToInsert);
      }

      result.setItemMeta(itemMeta);

      return result;
    } catch (final Exception exception) {
      throw new IllegalStateException("Could not fully execute copying operations ", exception);
    }
  }

  private Method setTagMethod() throws NoSuchMethodException {
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

  private void mutateMetaDataToHideAttributes(final ItemMeta itemMeta) {
    try {
      itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
      itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
      itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
      itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
      itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
      itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
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
    } catch (NoSuchMethodException | IllegalAccessException |
             InvocationTargetException reflectiveOperationException) {
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

  private void writeLoreAndDisplayNameToStack(@NonNull BaseItemStack stack) {
    if (stack.displayName(false) != null) {
      if (ProtocolVersionUtil.serverProtocolVersion() >= MINECRAFT_1_13) {
        stack.nbtData().put("Damage", new IntTag(stack.durability()));
        final BaseComponent[] baseComponents = stack.displayName(false); // This is were crappy adventure components come into play
        ComponentHelper.removeItalic(baseComponents);
        setDisplayNameTag(stack.nbtData(), ComponentSerializer.toString(baseComponents));
      } else {
        setDisplayNameTag(
            stack.nbtData(),
            TextComponent.toLegacyText(stack.displayName(false)));
      }
    }

    if (stack.lore(false) != null) {
      setLoreTag(stack.nbtData(), stack.lore(false), ProtocolVersionUtil.serverProtocolVersion());
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
    CompoundTag display = (CompoundTag) nbtData.get("display");
    if (display == null) {
      display = new CompoundTag();
    }
    final ListTag<StringTag> tag = new ListTag<>(StringTag.class);
    if (protocolVersion < MINECRAFT_1_14) {
      tag.addAll(lore.stream().map(i -> new StringTag(TextComponent.toLegacyText(i))).collect(
          Collectors.toList()));
    } else {
      tag.addAll(lore.stream().map(components -> {
        for (BaseComponent component : components) {
          if (!component.isItalic()) {
            component.setItalic(false);
          }
        }
        return new StringTag(ComponentSerializer.toString(components));
      }).collect(Collectors.toList()));
    }
    display.put("Lore", tag);
    nbtData.put("display", display);
  }
}