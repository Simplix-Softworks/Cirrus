package dev.simplix.cirrus.mojang;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.UUID;

public final class UUIDTypeAdapter extends TypeAdapter<UUID> {

  @Override
  public void write(final JsonWriter out, final UUID value) throws IOException {
    out.value(fromUUID(value));
  }

  @Override
  public UUID read(final JsonReader jsonReader) throws IOException {
    return fromString(jsonReader.nextString());
  }

  public static String fromUUID(final UUID value) {
    return value.toString().replace("-", "");
  }

  private static UUID fromString(final String input) {
    return UUID.fromString(input.replaceFirst(
        "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
        "$1-$2-$3-$4-$5"));
  }
}
