package dev.simplix.cirrus.effects;

import com.google.common.base.Preconditions;
import dev.simplix.cirrus.Cirrus;
import dev.simplix.cirrus.color.ColorUtil;
import dev.simplix.cirrus.effect.AbstractMenuEffect;
import dev.simplix.cirrus.service.ColorConvertService;
import dev.simplix.cirrus.util.ToStringUtil;
import java.awt.Color;
import java.util.*;

/**
 * The {@code SpectrumEffect} class is a menu effect that is used to create a color spectrum effect
 * for a given input string. The effect will cycle through the given colors, adding the specified
 * suffix to each color. The resulting list of strings can be used to display the effect in a menu.
 * <p>To create a new {@code SpectrumEffect}, use one of the provided static factory methods:
 * <ul>
 * <li>{@link SpectrumEffect#fat(String, Color...)} to create a "fat" spectrum effect with the
 * <p>
 * default color suffix and effect length.
 * <li>{@link SpectrumEffect#of(String, String, int, double, Color...)} to create a custom spectrum
 * <p>
 * effect with the specified input string, color suffix, effect length, and step size. The
 * <p>
 * {@code step} parameter determines how quickly the colors will transition from one to the
 * <p>
 * next. The {@code effectLength} parameter determines how many times the input string will be
 * <p>
 * repeated for each color in the spectrum.
 * </ul>
 * <p>Once a {@code SpectrumEffect} has been created, its properties can be customized using the
 * following methods:
 * <ul>
 * <li>{@link SpectrumEffect#step(int)} to set the step size for the effect.
 * <li>{@link SpectrumEffect#colorSuffix(String)} to set the color suffix to use for each color in
 * <p>
 * the spectrum.
 * </ul>
 * <p>Finally, to generate the list of strings representing the spectrum effect, call the
 * {@link SpectrumEffect#calculate()} method.
 */
public class SpectrumEffect extends AbstractMenuEffect<String> {

  private static final ColorConvertService SERVICE = Cirrus.service(ColorConvertService.class);

  private final List<Color> colors;
  private String colorSuffix;
  private double step;

  private SpectrumEffect(
      String input, int effectLength, double step, String colorSuffix, List<Color> colors) {
    super(Preconditions.checkNotNull(input, "input must not be null"), effectLength);
    this.step = step;
    this.colors = Preconditions.checkNotNull(colors, "colors must not be null");
    this.colorSuffix = Preconditions.checkNotNull(colorSuffix, "colorAddon must not be null");
    Preconditions.checkState(colors.size() >= 2, "At least 2 colors must be provided");
  }

  public static SpectrumEffect fat(String input, Color... colors) {
    return of(input, "§l", 2, 40, colors);
  }

  public static SpectrumEffect of(
      String input, String colorSuffix, int effectLength, double step, Color... colors) {
    return new SpectrumEffect(input, effectLength, step, colorSuffix, Arrays.asList(colors));
  }

  public SpectrumEffect step(int step) {
    this.step = step;
    return this;
  }

  public SpectrumEffect colorSuffix(String colorSuffix) {
    this.colorSuffix = colorSuffix;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    SpectrumEffect that = (SpectrumEffect) o;

    if (Double.compare(that.step, this.step) != 0) {
      return false;
    }
    if (!this.colors.equals(that.colors)) {
      return false;
    }
    return this.colorSuffix.equals(that.colorSuffix);
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = this.colors.hashCode();
    result = 31 * result + this.colorSuffix.hashCode();
    temp = Double.doubleToLongBits(this.step);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  /**
   * Calculates the resulting list of colored strings by iterating over the list of colors provided
   * to the constructor. For each color, it adds the specified number of colored strings to the
   * output list using the color, color suffix, and input string provided to the constructor. It
   * also calculates intermediate colors between the current color and the next color in the list
   * and adds them to the output list in the same way. This results in a gradient.
   *
   * @return A list of colored strings
   */
  @Override
  public List<String> calculate() {
    final List<String> out = new ArrayList<>();

    for (int i = 0; i < this.colors.size(); i++) {
      final Color color = this.colors.get(i);
      final int index = (this.colors.size() == i + 1) ? 0 : (i + 1);
      final Color color2 = this.colors.get(index);

      out.addAll(Collections.nCopies(
          this.effectLength,
          SERVICE.colorToString(color) + this.colorSuffix + this.input));

      for (Color between : ColorUtil.colorsInBetween(color, color2, this.step)) {
        out.addAll(Collections.nCopies(
            this.effectLength,
            SERVICE.colorToString(between) + this.colorSuffix + this.input));
      }
    }

    return out;
  }

  @Override
  public String toString() {
    return ToStringUtil
        .of(this)
        .add("input", this.input)
        .add("colors", this.colors)
        .add("effectLength", this.effectLength)
        .add("step", this.step)
        .add("colorSuffix", this.colorSuffix)
        .toString();
  }
}
