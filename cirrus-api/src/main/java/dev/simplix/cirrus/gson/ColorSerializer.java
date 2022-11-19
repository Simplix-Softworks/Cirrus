package dev.simplix.cirrus.gson;

import com.google.gson.*;
import dev.simplix.cirrus.Cirrus;
import dev.simplix.cirrus.service.ColorConvertService;
import java.awt.Color;
import java.lang.reflect.Type;
import java.util.Objects;

public class ColorSerializer implements JsonSerializer<Color> {
  @Override
  public JsonElement serialize(Color src, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(Objects.requireNonNull(Cirrus.service(ColorConvertService.class)).colorToString(src));
  }
}
