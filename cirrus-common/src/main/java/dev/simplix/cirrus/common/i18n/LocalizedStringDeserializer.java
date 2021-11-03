package dev.simplix.cirrus.common.i18n;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;
import lombok.NonNull;

public class LocalizedStringDeserializer implements JsonDeserializer<LocalizedString> {

    @Override
    public LocalizedString deserialize(
            @NonNull JsonElement jsonElement,
            @NonNull Type type,
            @NonNull JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Map<String, String> stringMap = jsonDeserializationContext.deserialize(
                jsonElement,
                new TypeToken<Map<String, String>>() {
                }.getType());
        return new LocalizedString(stringMap);
    }

}
