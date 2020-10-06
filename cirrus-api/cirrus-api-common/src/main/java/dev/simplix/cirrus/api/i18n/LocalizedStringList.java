package dev.simplix.cirrus.api.i18n;

import java.util.*;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
public class LocalizedStringList {

  private final Map<String, List<String>> translations = new HashMap<>();

  public LocalizedStringList(@NonNull Map<String, List<String>> stringListMap) {
    translations.putAll(stringListMap);
  }

  /**
   * @return The map with all translations
   */
  public Map<String, List<String>> translations() {
    return translations;
  }

  /**
   * Returns the localized string list for the given {@link Locale}. If the given locale is not
   * available, it will return the first available translation. If there aren't any translations, it
   * will return an empty list.
   *
   * @param locale The desired locale
   * @return the localized string list
   */
  public List<String> translated(@NonNull Locale locale) {
    if(translations.isEmpty()) {
      return Collections.emptyList();
    }
    String fallback = translations.keySet().iterator().next();
    return translations.getOrDefault(
        locale.getLanguage(),
        translations.getOrDefault(
            fallback,
            Collections.emptyList()));
  }

}
