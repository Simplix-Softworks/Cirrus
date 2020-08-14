package dev.simplix.cirrus.api.i18n;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;

public class LocalizedStringDeserializer implements JsonDeserializer<LocalizedString> {

  @Override
  public LocalizedString deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    Map<String, String> stringMap = jsonDeserializationContext.deserialize(
        jsonElement,
        new TypeToken<Map<String, String>>() {
        }.getType());
    return new LocalizedString(stringMap);
  }

}
