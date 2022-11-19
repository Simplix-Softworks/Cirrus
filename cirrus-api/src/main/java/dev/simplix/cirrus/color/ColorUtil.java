package dev.simplix.cirrus.color;

import java.awt.Color;
import java.util.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ColorUtil {

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
