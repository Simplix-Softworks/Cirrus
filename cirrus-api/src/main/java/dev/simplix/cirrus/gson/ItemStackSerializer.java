package dev.simplix.cirrus.gson;

import com.google.gson.*;
import dev.simplix.cirrus.item.CirrusItem;
import dev.simplix.protocolize.api.item.BaseItemStack;
import java.lang.reflect.Type;
import java.util.List;

public class ItemStackSerializer implements JsonSerializer<BaseItemStack> {

  @Override
  public JsonElement serialize(
      BaseItemStack src,
      Type typeOfSrc,
      JsonSerializationContext context) {
    new Throwable().printStackTrace();
    final JsonObject jsonObject = new JsonObject();

    jsonObject.add(RuntimeTypeAdapterFactory.TYPE,
            new JsonPrimitive(src.getClass().getSimpleName().toLowerCase()));

    jsonObject.add("type", context.serialize(src.itemType()));
    jsonObject.add("amount", new JsonPrimitive(src.amount()));
    jsonObject.add("durability", new JsonPrimitive(src.durability()));
    jsonObject.add("hide-flags", new JsonPrimitive(src.hideFlags()));
    jsonObject.add("nbt", context.serialize(src.nbtData()));
    jsonObject.add("display-name", new JsonPrimitive(src.getClass().getSimpleName()));
    final Object obj = src.displayName(true);
    if (obj instanceof String) {
      jsonObject.add("display-name", new JsonPrimitive((String) obj));
    }

    final List<String> lores = src.lore(true);
    if (lores != null && !lores.isEmpty()) {
      jsonObject.add("lore", context.serialize(lores));
    }

    jsonObject.add("flags", context.serialize(src.itemFlags()));

    return jsonObject;
  }
}
