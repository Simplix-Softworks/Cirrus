package dev.simplix.cirrus.api.i18n;

import dev.simplix.core.common.Replacer;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import net.querz.nbt.io.SNBTUtil;
import net.querz.nbt.tag.CompoundTag;
import dev.simplix.cirrus.api.model.ItemStackModel;

public final class Localizer {

  public static List<String> localize(
      LocalizedStringList localizedStringList,
      Locale locale,
      String... replacements) {
    return Arrays.asList(Replacer.of(localizedStringList.translated(locale))
        .replaceAll((Object[]) replacements).replacedMessage());
  }

  public static String localize(
      LocalizedString localizedString,
      Locale locale,
      String... replacements) {
    return Replacer
        .of(localizedString.translated(locale))
        .replaceAll((Object[]) replacements)
        .replacedMessageJoined();
  }

  public static LocalizedItemStackModel localize(
      ItemStackModel model,
      Locale locale,
      String... replacements) {
    return LocalizedItemStackModel.builder()
        .actionArguments(Arrays.asList(Replacer
            .of(model.actionArguments() == null ? Collections.emptyList() : model.actionArguments())
            .replaceAll((Object[]) replacements)
            .replacedMessage()))
        .actionHandler(model.actionHandler())
        .amount(model.amount())
        .durability(model.durability())
        .itemType(model.itemType())
        .displayName(localize(model.displayName(), locale, replacements))
        .lore(localize(model.lore(), locale, replacements))
        .nbt(formatNbt(model.nbt(), replacements))
        .slots(model.slots())
        .build();
  }

  private static CompoundTag formatNbt(CompoundTag compoundTag, String... replacements) {
    if(compoundTag == null) {
      return null;
    }
    try {
      String mojangson = SNBTUtil.toSNBT(compoundTag);
      return (CompoundTag) SNBTUtil.fromSNBT(Replacer.of(mojangson)
          .replaceAll((Object[]) replacements).replacedMessageJoined());
    } catch (IOException e) {
    }
    return compoundTag;
  }

}
