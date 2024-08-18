package dev.simplix.cirrus.gson;

import com.google.gson.*;
import dev.simplix.cirrus.effect.AbstractMenuEffect;
import dev.simplix.cirrus.item.CirrusItem;
import dev.simplix.protocolize.api.chat.ChatElement;
import dev.simplix.protocolize.data.ItemType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import net.querz.nbt.tag.CompoundTag;

public class CirrusItemDeserializer implements JsonDeserializer<CirrusItem> {

  @Override
  public CirrusItem deserialize(
      JsonElement jsonElement,
      Type typeOf,
      JsonDeserializationContext context) throws JsonParseException {

    final JsonObject asJsonObject = jsonElement.getAsJsonObject();
    final ItemType type = ItemType.valueOf(asJsonObject.get("type").getAsString());
    final byte amount = asJsonObject.get("amount").getAsByte();
    final byte durability = asJsonObject.get("durability").getAsByte();
    final int hideflags = asJsonObject.get("hide-flags").getAsInt();
    final JsonElement nbtRaw = asJsonObject.get("nbt");
    final CompoundTag nbt = nbtRaw == null
        ? new CompoundTag()
        : context.deserialize(nbtRaw, CompoundTag.class);

    final AbstractMenuEffect<String> effect = asJsonObject.get("display-name-effect") != null
        ? context.deserialize(asJsonObject.get("display-name-effect"), AbstractMenuEffect.class)
        : null;

    String displayName = null;
    if (effect == null) {
      displayName = asJsonObject.get("display-name").getAsString();
    }

    final String actionHandler = asJsonObject.get("action-handler").getAsString();

    final List<String> lore = asJsonObject.get("lore") != null
        ? context.deserialize(asJsonObject.get("lore"), List.class)
        : null;

    final List<String> actionArguments = asJsonObject.get("action-arguments") != null
        ? context.deserialize(asJsonObject.get("action-arguments"), List.class)
        : null;

    return new CirrusItem(type)
        .amount(amount)
        .nbtData(nbt)
        .displayName(ChatElement.ofLegacyText(displayName))
        .displayNameEffect(effect)
        .durability(durability)
        .hideFlags(hideflags)
        .actionArguments(actionArguments)
        .actionHandler(actionHandler)
        .lore((ChatElement<?>) lore.stream().map(ChatElement::ofLegacyText).collect(Collectors.toSet()));

  }
}
