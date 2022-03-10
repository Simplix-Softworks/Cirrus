package dev.simplix.cirrus.common.util;

import lombok.experimental.UtilityClass;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ColorUtils {

    public List<Color> colorsInBetween(final Color first, final Color second, final double step) {
        final double diffBlue = (second.getBlue() - first.getBlue()) / step;
        final double diffGreen = (second.getGreen() - first.getGreen()) / step;
        final double diffRed = (second.getRed() - first.getRed()) / step;
        final List<Color> list = new ArrayList<>();
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
