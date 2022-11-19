package dev.simplix.cirrus.mojang;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.simplix.cirrus.util.LoadingCache;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

@UtilityClass
@FieldDefaults(makeFinal = true)
public class MojangTextureFetcher {

  private final String STEVE_TEXTURE = "ewogICJ0aW1lc3RhbXAiIDogMTU5MTU3NDcyMzc4MywKICAicHJvZmlsZUlkIiA6ICI4NjY3YmE3MWI4NWE0MDA0YWY1NDQ1N2E5NzM0ZWVkNyIsCiAgInByb2ZpbGVOYW1lIiA6ICJTdGV2ZSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82ZDNiMDZjMzg1MDRmZmMwMjI5Yjk0OTIxNDdjNjlmY2Y1OWZkMmVkNzg4NWY3ODUwMjE1MmY3N2I0ZDUwZGUxIgogICAgfSwKICAgICJDQVBFIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS85NTNjYWM4Yjc3OWZlNDEzODNlNjc1ZWUyYjg2MDcxYTcxNjU4ZjIxODBmNTZmYmNlOGFhMzE1ZWE3MGUyZWQ2IgogICAgfQogIH0KfQ==";

  public static final String SERVICE_URL = "https://sessionserver.mojang.com/session/minecraft/profile/";

  private final LoadingCache<UUID, String> cache = new LoadingCache<>(
      30,
      TimeUnit.MINUTES,
      new LoadingCache.CacheLoader<UUID, String>() {
        @SneakyThrows
        @Override
        public String load(UUID uuid) {
          return fetch0(uuid);
        }
      });

  public String getTextureHash(final UUID uuid) {
    return cache.get(uuid);
  }

  public String fetch(final UUID uuid) {
    try {
      final String out = fetch0(uuid);
      if (isValid(out)) {
        cache.put(uuid, out);
        return out;
      }
    } catch (final Throwable throwable) {
      System.err.println(
          "If you aren't in online mode, disable it in your settings.yml as well!"
                        );
      System.err.println("Using Steve-Texture as default");
      throwable.printStackTrace();
    }
    return STEVE_TEXTURE;
  }

  private String fetch0(final UUID uuid) throws Exception {
    final URL url_1 = new URL(
        SERVICE_URL + UUIDTypeAdapter
            .fromUUID(uuid)
        + "?unsigned=false");
    final InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());
    final JsonObject textureProperty = new JsonParser().parse(reader_1).getAsJsonObject()
        .get("properties").getAsJsonArray().get(0).getAsJsonObject();

    return textureProperty.get("value").getAsString();
  }

  private boolean isValid(final Object hash) {
    if (!(hash instanceof String)) {
      return false;
    }

    return !((String) hash).isEmpty() && (!hash.equals(STEVE_TEXTURE));
  }
}
