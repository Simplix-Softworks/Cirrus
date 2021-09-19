package dev.simplix.cirrus.common.tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.simplix.cirrus.common.mojangson.TagDeserializer;
import dev.simplix.cirrus.common.mojangson.TagSerializer;
import net.querz.nbt.tag.CompoundTag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class SnbtGsonTest {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(CompoundTag.class, new TagSerializer())
            .registerTypeAdapter(CompoundTag.class, new TagDeserializer())
            .create();

    @Test
    public void test() {
        CompoundTag display = new CompoundTag();
        display.putInt("integer", 134);
        display.putBoolean("boolean", true);
        display.putFloat("float", 24934.3F);
        display.putDouble("double", 9999.21348D);
        display.putShort("short", (short) 12);
        display.putByte("byte", (byte) 3);
        display.putString("string", "This is a string");

        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("display", display);

        String serialized = gson.toJson(compoundTag, CompoundTag.class);
        CompoundTag deserialized = gson.fromJson(serialized, CompoundTag.class);

        Assertions.assertEquals(compoundTag, deserialized);

        System.out.println("Cirrus SNBT over Gson - OK");
    }

}
