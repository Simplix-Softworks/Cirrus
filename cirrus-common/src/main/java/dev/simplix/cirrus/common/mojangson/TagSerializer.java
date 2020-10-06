package dev.simplix.cirrus.common.mojangson;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import lombok.NonNull;
import net.querz.nbt.io.SNBTUtil;
import net.querz.nbt.tag.Tag;

public final class TagSerializer implements JsonSerializer<Tag<?>> {

  @Override
  public JsonElement serialize(
      @NonNull Tag<?> src,
      @NonNull Type typeOfSrc,
      @NonNull JsonSerializationContext context) {
    try {
      String json = SNBTUtil.toSNBT(src);
      return JsonParser.parseString(json);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
