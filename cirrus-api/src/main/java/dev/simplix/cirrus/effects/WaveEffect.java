package dev.simplix.cirrus.effects;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import dev.simplix.cirrus.Cirrus;
import dev.simplix.cirrus.color.ColorUtil;
import dev.simplix.cirrus.effect.AbstractMenuEffect;
import dev.simplix.cirrus.service.ColorConvertService;
import dev.simplix.cirrus.util.ToStringUtil;
import java.awt.Color;
import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Will generate: <a
 * href="https://media3.giphy.com/media/bvuK2KpLTip6vtemxY/giphy.gif?cid=790b7611af638e257a5f310fdd6d3015bfb9ee3119298826&rid=giphy.gif&ct=g">...</a>
 */
@Slf4j
public class WaveEffect extends AbstractMenuEffect<String> {

  protected final Color[] colors;

  protected final String colorSuffix;
  protected boolean reverse = false;
  protected int transitionCount = 0;

  public WaveEffect(String input, Color[] colors) {
    this(input, "§l", colors, 2);
  }

  public WaveEffect(
      String input,
      String colorSuffix,
      Color[] colors,
      int effectLength) {
    super(input, effectLength);
    this.colors = Preconditions.checkNotNull(colors, "colors must not be null");
    this.colorSuffix = Preconditions.checkNotNull(colorSuffix, "colorSuffix must not be null");
    Preconditions.checkArgument(colors.length > 0, "colors must not be empty");
  }

  public static WaveEffect fat(String input, Color... colors) {
    return new WaveEffect(input, "§l", colors, 2);
  }

  public WaveEffect reversed() {
    this.reverse = true;
    return this;
  }

  public WaveEffect reverse(boolean reverse) {
    this.reverse = reverse;
    return this;
  }

  public WaveEffect transitionCount(int transitionCount) {
    this.transitionCount = transitionCount;
    return this;
  }

  public WaveEffect length(int length) {
    return new WaveEffect(this.input, this.colorSuffix, this.colors, length)
        .reverse(this.reverse)
        .transitionCount(this.transitionCount);
  }

  boolean smooth = false;

  @Override
  public List<String> calculate() {

    final List<String> out = new ArrayList<>();

    for (int i = 0; i < this.colors.length; i++) {
      final Color color = this.colors[i];
      final int index = (this.colors.length == i + 1) ? 0 : (i + 1);
      final Color color2 = this.colors[index];

      out.addAll(insertEffect(
          Cirrus.service(ColorConvertService.class).colorToString(color) + this.colorSuffix,
          Cirrus.service(ColorConvertService.class).colorToString(color2) + this.colorSuffix));

    }

    return out;
  }

  private List<String> insertEffect(String effectColor, String primaryColor) {
    final List<String> out = new ArrayList<>();

    if (this.reverse) {
      for (int i = this.input.length() - 1; i >= 0; i--) {
        out.addAll(Collections.nCopies(
            this.effectLength,
            addAtIndex(primaryColor + this.input, effectColor, i + primaryColor.length())));
      }
      if (this.transitionCount != 0) {
        out.addAll(Collections.nCopies(this.transitionCount, effectColor + this.input));
      }

    } else {
      for (int i = 0; i <= this.input.length(); i++) {
        out.addAll(Collections.nCopies(
            this.effectLength,
            addAtIndex(primaryColor + this.input, effectColor, i + primaryColor.length())));
      }
      if (this.transitionCount != 0) {
        out.addAll(Collections.nCopies(this.transitionCount, primaryColor + this.input));
      }
    }
    return out;
  }

  private String addAtIndex(String str, String toAdd, int position) {
    StringBuilder sb = new StringBuilder(str);
    sb.insert(position, toAdd);
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    WaveEffect that = (WaveEffect) o;

    if (this.reverse != that.reverse) {
      return false;
    }
    if (this.transitionCount != that.transitionCount) {
      return false;
    }
    if (!Arrays.equals(this.colors, that.colors)) {
      return false;
    }
    return this.colorSuffix.equals(that.colorSuffix);
  }

  @Override
  public int hashCode() {
    int result = Arrays.hashCode(this.colors);
    result = 31 * result + this.colorSuffix.hashCode();
    result = 31 * result + (this.reverse ? 1 : 0);
    result = 31 * result + this.transitionCount;
    return result;
  }

  @Override
  public String toString() {
    return ToStringUtil.of(this)
        .add("colors", Arrays.toString(this.colors))
        .add("colorSuffix", this.colorSuffix)
        .add("reverse", this.reverse)
        .add("transitionCount", this.transitionCount)
        .add("input", this.input)
        .add("effectLength", this.effectLength)
        .toString();
  }
}
