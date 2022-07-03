package dev.simplix.cirrus.common.effects;

import com.google.common.base.Preconditions;
import dev.simplix.cirrus.common.effect.AbstractMenuAnimation;
import dev.simplix.cirrus.common.util.ColorUtils;
import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RGBColorChangeAnimation extends AbstractMenuAnimation<String> {

    private final List<Color> colors;
    private String colorSuffix;
    private double step;

    private RGBColorChangeAnimation(
            String input,
            int effectLength,
            double step,
            String colorSuffix,
            List<Color> colors
    ) {
        super(Preconditions.checkNotNull(input, "input must not be null"), effectLength);
        this.step = step;
        this.colors = Preconditions.checkNotNull(colors, "colors must not be null");
        this.colorSuffix = Preconditions.checkNotNull(colorSuffix, "colorAddon must not be null");
        Preconditions.checkState(colors.size() >= 2, "At least 2 colors must be provided¥");
    }

    public static RGBColorChangeAnimation fat(String input, Color... colors) {
        return of(input, "§l", 2, 40, colors);
    }

    public static RGBColorChangeAnimation of(String input, String colorSuffix, int effectLength, double step, Color... colors) {
        return new RGBColorChangeAnimation(input, effectLength, step, colorSuffix, Arrays.asList(colors));
    }

    public RGBColorChangeAnimation step(int step) {
        this.step = step;
        return this;
    }

    public RGBColorChangeAnimation colorSuffix(String colorSuffix) {
        this.colorSuffix = colorSuffix;
        return this;
    }

    @Override
    public List<String> calculate() {
        final List<String> out = new ArrayList<>();

        for (int i = 0; i < this.colors.size(); i++) {
            final Color color = this.colors.get(i);
            final int index = (this.colors.size() == i + 1) ? 0 : (i + 1);
            final Color color2 = this.colors.get(index);

            out.addAll(Collections.nCopies(
                    this.effectLength,
                    ChatColor.of(color) + this.colorSuffix + this.input
            ));

            for (Color between : ColorUtils.colorsInBetween(color, color2, this.step)) {
                out.addAll(
                        Collections.nCopies(
                                this.effectLength,
                                ChatColor.of(between) + this.colorSuffix + this.input
                        )
                );
            }
        }

        return out;
    }
}
