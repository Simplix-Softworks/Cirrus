package dev.simplix.cirrus.common.converter;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.Map.Entry;

/**
 * Central registration class for {@link Converter}s.
 */
@SuppressWarnings("rawtypes")
public final class Converters {

    private static final Map<Entry<Class, Class>, Converter> CONVERTER_MAP = new HashMap<>();

    private Converters() {
    }

    /**
     * Converts a source object to a given type.
     *
     * @param source The source object to convert
     * @param to     The target type class
     * @param <T>    The target type
     * @return An object instance of the target type
     */
    public static <T> T convert(@NonNull final Object source, @NonNull final Class<T> to) {
        Converter converter = getConverter(source.getClass(), to);
        if (converter == null) {
            converter = getMultiConverter(source.getClass(), to);
        }
        if (converter == null) {
            throw new NullPointerException("No converter available to convert " + source
                    .getClass()
                    .getName() + " to " + to.getName());
        }
        return (T) converter.convert(source);
    }

    private static <S, T> Converter<S, T> getMultiConverter(
            @NonNull final Class<S> source,
            @NonNull final Class<T> to) {
        final List<Converter> converters = new ArrayList<>();
        findConversionRoute(converters, source, to);
        if (converters.size() <= 1) {
            return null;
        }
        return src -> {
            Object out = src;
            for (final Converter converter : converters) {
                out = converter.convert(out);
            }
            return (T) out;
        };
    }

    private static boolean findConversionRoute(
            @NonNull final List<Converter> converters,
            @NonNull final Class<?> source,
            @NonNull final Class<?> to) {
        for (final Entry<Class, Class> entry : CONVERTER_MAP.keySet()) {
            if (entry.getKey().equals(source)) {
                final List<Converter> list = new ArrayList<>();
                if (buildRoute(list, entry, to)) {
                    list.add(CONVERTER_MAP.get(entry));
                    Collections.reverse(list);
                    converters.addAll(list);
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean buildRoute(
            @NonNull final List<Converter> converters,
            @NonNull final Map.Entry<Class, Class> entry,
            @NonNull final Class<?> to) {
        if (entry.getValue().equals(to)) {
            return true;
        }
        for (final Entry<Class, Class> entry1 : CONVERTER_MAP.keySet()) {
            if (entry1.getKey().equals(entry.getValue())) {
                if (buildRoute(converters, entry1, to)) {
                    converters.add(CONVERTER_MAP.get(entry1));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Registers a {@link Converter} for a given type conversion.
     *
     * @param sourceType The source type
     * @param targetType The target type
     * @param converter  The convert instance
     */
    public static void register(
            @NonNull final Class<?> sourceType,
            @NonNull final Class targetType,
            @NonNull final Converter converter) {
        CONVERTER_MAP.put(new SimpleEntry<>(sourceType, targetType), converter);
    }

    /**
     * Returns a registered converter
     *
     * @param sourceType The source type class
     * @param targetType The target type class
     * @param <S>        The source type
     * @param <T>        The target type
     * @return The converter instance or null if no such converter exists
     */
    @Nullable
    public static <S, T> Converter<S, T> getConverter(
            final Class<S> sourceType,
            final Class<T> targetType) {
        Converter converter = CONVERTER_MAP.get(new SimpleEntry<>(sourceType, targetType));
        if (converter == null) {
            for (Entry<Class, Class> entry : CONVERTER_MAP.keySet()) {
                if (entry.getKey().isAssignableFrom(sourceType)) {
                    if (entry.getValue().equals(targetType)) {
                        return CONVERTER_MAP.get(entry);
                    }
                }
            }
        }
        return converter;
    }
}
