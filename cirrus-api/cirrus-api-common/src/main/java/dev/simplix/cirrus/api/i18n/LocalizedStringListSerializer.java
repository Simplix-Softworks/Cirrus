package dev.simplix.cirrus.api.i18n;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class LocalizedStringListSerializer implements JsonSerializer<LocalizedStringList> {

  @Override
  public JsonElement serialize(
      LocalizedStringList localizedStringList,
      Type type,
      JsonSerializationContext jsonSerializationContext) {
    return jsonSerializationContext.serialize(localizedStringList.translations());
  }

}
