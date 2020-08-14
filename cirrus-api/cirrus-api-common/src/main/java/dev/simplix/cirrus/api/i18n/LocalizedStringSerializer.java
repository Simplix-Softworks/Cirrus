package dev.simplix.cirrus.api.i18n;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class LocalizedStringSerializer implements JsonSerializer<LocalizedString> {

  @Override
  public JsonElement serialize(
      LocalizedString localizedString,
      Type type,
      JsonSerializationContext jsonSerializationContext) {
    return jsonSerializationContext.serialize(localizedString.translations());
  }

}
