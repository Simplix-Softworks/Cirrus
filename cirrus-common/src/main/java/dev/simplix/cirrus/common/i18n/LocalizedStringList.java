package dev.simplix.cirrus.common.i18n;

import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.*;

@NoArgsConstructor
public class LocalizedStringList {

    private final Map<String, List<String>> translations = new HashMap<>();

    public LocalizedStringList(@NonNull Map<String, List<String>> stringListMap) {
        this.translations.putAll(stringListMap);
    }

    /**
     * @return The map with all translations
     */
    public Map<String, List<String>> translations() {
        return this.translations;
    }


    private final List<String> forceAdded = new ArrayList<>();

    /**
     * Returns the localized string list for the given {@link Locale}. If the given locale is not
     * available, it will return the first available translation. If there aren't any translations, it
     * will return an empty list.
     *
     * @param locale The desired locale
     * @return the localized string list
     */
    public List<String> translated(@NonNull Locale locale) {
        if (this.translations.isEmpty()) {
            return Collections.emptyList();
        }
        String fallback = this.translations.keySet().iterator().next();
        final List<String> out = new ArrayList<>(this.translations.getOrDefault(
                locale.getLanguage(),
                this.translations.getOrDefault(
                        fallback,
                        Collections.emptyList()))
        );

        out.addAll(this.forceAdded);
        return out;
    }

}
