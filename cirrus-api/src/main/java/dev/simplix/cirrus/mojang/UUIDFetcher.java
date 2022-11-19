package dev.simplix.cirrus.mojang;

import dev.simplix.cirrus.util.LoadingCache;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * Way more reliable UUIDFetcher implementation than the default one.
 * <p>
 * A bit slower than using the mojang api but doesn't have a rate-limit.
 */
@UtilityClass
@Slf4j
public class UUIDFetcher {

  private final String urlString = "https://mcuuid.net/?q=";
  private final LoadingCache<UUID, String> nameCache = new LoadingCache<>(
      30,
      TimeUnit.MINUTES,
      new LoadingCache.CacheLoader<UUID, String>() {
        @Override
        public String load(UUID uuid) {
          URL url =
              getURL(uuid.toString());

          for (String line : readStringArrayFromUrl(url)) {
            if (!line.startsWith("<h3>")) {
              continue;
            }

            return line.replace("<h3>", "")
                .replace(
                    "</h3>",
                    "");
          }

          return null;
        }
      });
  private final LoadingCache<String, UUID> uuidCache = new LoadingCache<>(
      30,
      TimeUnit.MINUTES,
      new LoadingCache.CacheLoader<String,
          UUID>() {
        @Override
        public UUID load(String s) {
          URL url = getURL(s);

          for (String line : readStringArrayFromUrl(url)) {
            if (
                !line.startsWith(
                    "<td><input type=\"text\" class=\"form-control\" onclick=\"this.select();\" readonly=\"readonly\" value=\"")) {
              continue;
            }

            String[] parts =
                line.split("\\s");

            return UUID.fromString(
                parts[5].replace(
                        "value=",
                        "")
                    .replace(
                        "\"",
                        ""));
          }

          return null;
        }
      });

  private String[] readStringArrayFromUrl(URL url) {
    try {
      URLConnection urlConnection = url.openConnection();

      urlConnection.addRequestProperty(
          "User-Agent",
          "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");

      return new Scanner(urlConnection.getInputStream(), "UTF-8")
          .useDelimiter("\\A")
          .next()
          .split("\n");
    } catch (IOException e) {
      e.printStackTrace();

      return new String[0];
    }
  }

  public String getName(UUID uuid) {
    return nameCache.get(uuid);
  }

  private URL getURL(String name) {
    try {
      return new URL(urlString + name);
    } catch (MalformedURLException ex) {
      ex.printStackTrace();
      log.error("Malformed URL: {}", urlString + name);
      throw new IllegalStateException("Cant return null. (UUIDFetcher.getURL())");
    }
  }

  public UUID getUUID(String name) {
    return uuidCache.get(name);
  }
}


