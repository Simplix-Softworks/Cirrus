package dev.simplix.cirrus.api.i18n;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import lombok.NonNull;

public class LocalizedString {

  private final Map<String, String> translations = new HashMap<>();

  public LocalizedString(@NonNull Map<String, String> stringMap) {
    translations.putAll(stringMap);
  }

  /**
   * @return The map with all translations
   */
  public Map<String, String> translations() {
    return translations;
  }

  /**
   * Returns the localized string for the given {@link Locale}. If the given locale is not
   * available, it will return the first available translation. If there aren't any translations, it
   * will return "missigno."
   *
   * @param locale The desired locale
   * @return the localized string or "missigno."
   */
  public String translated(Locale locale) {
    return translations.getOrDefault(
        locale.getLanguage(),
        translations.getOrDefault(
            translations.keySet().stream().findFirst().orElse(""),
            "missgno."));
  }

}
