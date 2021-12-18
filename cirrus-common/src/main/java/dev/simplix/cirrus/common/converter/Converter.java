package dev.simplix.cirrus.common.converter;

import lombok.NonNull;

/**
 * A converter is used to convert one object to another.
 *
 * @param <Source> The source type of the object
 * @param <Target> The target type ot the object
 */
public interface Converter<Source, Target> {

    /**
     * Converts a given source object to the target type.
     *
     * @param src The source object
     * @return The target object
     */
    Target convert(@NonNull Source src);

}
