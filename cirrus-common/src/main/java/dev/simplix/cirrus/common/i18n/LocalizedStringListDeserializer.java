package dev.simplix.cirrus.common.i18n;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import lombok.NonNull;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class LocalizedStringListDeserializer implements JsonDeserializer<LocalizedStringList> {

    @Override
    public LocalizedStringList deserialize(
            @NonNull JsonElement jsonElement,
            @NonNull Type type,
            @NonNull JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        Map<String, List<String>> stringListMap = jsonDeserializationContext.deserialize(
                jsonElement,
                new TypeToken<Map<String, List<String>>>() {
                }.getType());
        return new LocalizedStringList(stringListMap);
    }
}
