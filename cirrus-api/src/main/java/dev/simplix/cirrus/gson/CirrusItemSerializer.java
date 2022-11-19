package dev.simplix.cirrus.gson;

import com.google.gson.*;
import dev.simplix.cirrus.effect.AbstractMenuEffect;
import dev.simplix.cirrus.item.CirrusItem;
import java.lang.reflect.Type;
import java.util.List;

public class CirrusItemSerializer implements JsonSerializer<CirrusItem> {

  @Override
  public JsonElement serialize(
      CirrusItem src,
      Type typeOfSrc,
      JsonSerializationContext context) {
    final JsonObject jsonObject = new JsonObject();

    jsonObject.add(
        RuntimeTypeAdapterFactory.TYPE,
        new JsonPrimitive(src.getClass().getSimpleName().toLowerCase()));

    final String displayName = src.displayName();
    AbstractMenuEffect<String> effect = src.displayNameEffect();
    // Only add displayName is present and no displayName effect is applied
    if (displayName != null && effect == null) {
      jsonObject.add("display-name", new JsonPrimitive(displayName));
    }

    if (effect != null) {
      // TODO: Redo
      jsonObject.add("display-name-effect", context.serialize(effect, AbstractMenuEffect.class));
    }

    jsonObject.add("type", context.serialize(src.itemType()));
    final List<String> lores = src.lore();
    if (lores != null) {
      jsonObject.add("lore", lores.isEmpty() ? new JsonArray() : context.serialize(lores));
    }
    jsonObject.add("amount", new JsonPrimitive(src.amount()));
    jsonObject.add("durability", new JsonPrimitive(src.durability()));
    jsonObject.add("hide-flags", new JsonPrimitive(src.hideFlags()));
    jsonObject.add("nbt", context.serialize(src.nbtData()));

    jsonObject.add(
        "action-handler",
        new JsonPrimitive(src.actionHandler() == null ? "noAction" : src.actionHandler()));
    jsonObject.add("flags", context.serialize(src.itemFlags()));

    if (src.actionArguments().isEmpty()) {
      jsonObject.add("action-arguments", new JsonArray());
    } else {
      jsonObject.add("action-arguments", context.serialize(src.actionArguments()));
    }

    return jsonObject;
  }

}
