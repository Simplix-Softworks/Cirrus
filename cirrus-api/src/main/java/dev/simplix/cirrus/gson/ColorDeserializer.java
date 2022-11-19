package dev.simplix.cirrus.gson;

import com.google.gson.*;
import dev.simplix.cirrus.Cirrus;
import dev.simplix.cirrus.service.ColorConvertService;
import java.awt.Color;
import java.lang.reflect.Type;
import java.util.Objects;

public class ColorDeserializer implements JsonDeserializer<Color> {
  @Override
  public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    if (!json.isJsonPrimitive()) {
      throw new IllegalArgumentException("Color must be a string");
    }
    final String asString = json.getAsString();
    return Objects.requireNonNull(Cirrus.service(ColorConvertService.class)).stringToColor(asString);

  }
}
