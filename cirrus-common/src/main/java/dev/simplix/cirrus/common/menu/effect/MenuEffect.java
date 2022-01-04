package dev.simplix.cirrus.common.menu.effect;

import java.util.List;

public interface MenuEffect<T> {

    /**
     * Duration of the effect in the menu in ticks
     */
    int effectLength();

    /**
     * Initial input of the effect
     */
    T input();

    /**
     * All calculated values for the effect
     */
    List<T> calculate();

    /**
     * Next effect frame. Usually calculated with an
     * infinite iterable calculated with {@link MenuEffect#calculate()}
     * and created with {@link com.google.common.collect.Iterables#cycle(Object[])}
     */
    T next();

}
