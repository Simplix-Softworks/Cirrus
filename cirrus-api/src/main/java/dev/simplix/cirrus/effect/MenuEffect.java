package dev.simplix.cirrus.effect;

import java.util.List;

/**
 * Menus should have effects/animation. For instance a RGB-effect for the displayname of an
 * {@link dev.simplix.cirrus.item.CirrusItem}
 */
public interface MenuEffect<T> {

  /**
   * Duration is measured in MC-ticks. The effect length describes the duration between each frame
   * of the effect/animation in ticks
   *
   * @return the duration of between each frame
   */
  int effectLength();

  /**
   * Each effect/animation output is calculated based on an input. Based on this input a List of
   * outputs E.G (frames) is generated.
   *
   * @return The input of the effect/animation
   */
  T input();

  /**
   * All calculated values for the effect
   */
  List<T> calculate();

  /**
   * Next effect frame. Usually calculated with an infinite iterable calculated with
   * {@link MenuEffect#calculate()} and created with
   * {@link com.google.common.collect.Iterables#cycle(Object[])}
   */
  T nextFrame();

}
