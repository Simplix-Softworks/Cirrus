package dev.simplix.cirrus.common.mojangson;

import com.google.gson.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.querz.nbt.io.SNBTUtil;
import net.querz.nbt.tag.Tag;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Type;

@Slf4j
public final class TagDeserializer implements JsonDeserializer<Tag<?>> {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    public Tag<?> deserialize(
            @NonNull JsonElement json,
            @NonNull Type typeOfT,
            @NonNull JsonDeserializationContext context)
            throws JsonParseException {
        if (!json.isJsonObject()) {
            throw new IllegalArgumentException("Expected json object!");
        }
        StringWriter stringWriter = new StringWriter();
        try {
            GSON.toJson(json, new MojangsonWriter(stringWriter));
            return SNBTUtil.fromSNBT(stringWriter.toString());
        } catch (IOException e) {
            log.error("Something went wrong while parsing SNBT string: " + stringWriter.toString());
            throw new RuntimeException(e);
        }
    }
}
