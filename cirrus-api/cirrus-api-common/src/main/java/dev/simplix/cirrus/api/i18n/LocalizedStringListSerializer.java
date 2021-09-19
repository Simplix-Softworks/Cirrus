package dev.simplix.cirrus.api.i18n;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import lombok.NonNull;

import java.lang.reflect.Type;

public class LocalizedStringListSerializer implements JsonSerializer<LocalizedStringList> {

    @Override
    public JsonElement serialize(
            @NonNull LocalizedStringList localizedStringList,
            @NonNull Type type,
            @NonNull JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(localizedStringList.translations());
    }

}
