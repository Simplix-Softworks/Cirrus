package dev.simplix.cirrus.color;

import java.awt.Color;
import java.util.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ColorUtil {

  /**
   * Calculates the colors in between two given colors, using the specified step value.
   * <p>
   * The method calculates the difference between the RGB values of the two colors and divides it by
   * the step value. It then creates a list of colors that are evenly spaced between the first and
   * second colors, with the number of colors determined by the step value.
   * <p>
   * <p>
   * To evenly distribute the values between the two given colors, the method calculates the
   * difference between the red, green, and blue values of the two colors and divides each
   * difference by the step value. This produces a set of increments that can be added to the
   * starting color's values to produce a sequence of colors that are evenly spaced between the
   * first and second colors. These increments are then used in a loop to generate the colors that
   * are evenly spaced between the two given colors, with the number of colors determined by the
   * step value. This ensures that the generated colors are evenly distributed between the two given
   * colors.
   * <p>
   * @param first  The first color
   * @param second The second color
   * @param step   The step value to use
   * @return A collection of colors between the two given colors, generated using the specified step
   * value
   */
  public Collection<Color> colorsInBetween(
      final Color first,
      final Color second, final double step) {
    final double diffBlue = (second.getBlue() - first.getBlue()) / step;
    final double diffGreen = (second.getGreen() - first.getGreen()) / step;
    final double diffRed = (second.getRed() - first.getRed()) / step;
    final List<Color> list = new LinkedList<>();
    for (int i = 1; i <= step; ++i) {
      list.add(
          new Color(
              (int) Math.round(first.getRed() + diffRed * i),
              (int) Math.round(first.getGreen() + diffGreen * i),
              (int) Math.round(first.getBlue() + diffBlue * i)
          )
              );
    }
    return list;
  }
}
