package dev.simplix.cirrus.common.mojangson;

import com.google.gson.*;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Type;
import lombok.NonNull;
import net.querz.nbt.io.SNBTUtil;
import net.querz.nbt.tag.Tag;

public final class TagDeserializer implements JsonDeserializer<Tag<?>> {

  private static final Gson GSON = new GsonBuilder().create();

  @Override
  public Tag<?> deserialize(
      @NonNull JsonElement json,
      @NonNull Type typeOfT,
      @NonNull JsonDeserializationContext context)
      throws JsonParseException {
    if (!json.isJsonObject()) {
      throw new IllegalArgumentException("Expected json object!");
    }
    try {
      StringWriter stringWriter = new StringWriter();
      GSON.toJson(json, new MojangsonWriter(stringWriter));
      return SNBTUtil.fromSNBT(stringWriter.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
