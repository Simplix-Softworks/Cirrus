package dev.simplix.cirrus.api.i18n;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class LocalizedStringListDeserializer implements JsonDeserializer<LocalizedStringList> {

  @Override
  public LocalizedStringList deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    Map<String, List<String>> stringListMap = jsonDeserializationContext.deserialize(
        jsonElement,
        new TypeToken<Map<String, List<String>>>() {
        }.getType());
    return new LocalizedStringList(stringListMap);
  }
}
