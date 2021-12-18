package dev.simplix.cirrus.common.i18n;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import lombok.NonNull;

public class LocalizedStringListSerializer implements JsonSerializer<LocalizedStringList> {

    @Override
    public JsonElement serialize(
            @NonNull LocalizedStringList localizedStringList,
            @NonNull Type type,
            @NonNull JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(localizedStringList.translations());
    }

}
