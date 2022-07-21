package dev.simplix.cirrus.common.effects;

import com.google.common.base.Preconditions;
import dev.simplix.cirrus.common.effect.AbstractMenuAnimation;
import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Will generate: <a href="https://media3.giphy.com/media/bvuK2KpLTip6vtemxY/giphy.gif?cid=790b7611af638e257a5f310fdd6d3015bfb9ee3119298826&rid=giphy.gif&ct=g">...</a>
 */
public class GradualColorChangeAnimation extends AbstractMenuAnimation<String> {

    protected final Color[] colors;

    protected final String colorSuffix;
    protected boolean reverse = false;
    protected int transitionCount = 0;

    public GradualColorChangeAnimation(String input, Color[] colors) {
        this(input, "§l", colors, 2);
    }

    public GradualColorChangeAnimation(
            String input,
            String colorSuffix,
            Color[] colors,
            int effectLength) {
        super(input, effectLength);
        this.colors = Preconditions.checkNotNull(colors, "colors must not be null");
        this.colorSuffix = Preconditions.checkNotNull(colorSuffix, "colorSuffix must not be null");
        Preconditions.checkArgument(colors.length > 0, "colors must not be empty");
    }

    public static GradualColorChangeAnimation fat(String input, Color... colors) {
        return new GradualColorChangeAnimation(input, "§l", colors, 2);
    }

    public GradualColorChangeAnimation reversed() {
        this.reverse = true;
        return this;
    }

    public GradualColorChangeAnimation reverse(boolean reverse) {
        this.reverse = reverse;
        return this;
    }

    public GradualColorChangeAnimation transitionCount(int transitionCount) {
        this.transitionCount = transitionCount;
        return this;
    }

    public GradualColorChangeAnimation length(int length) {
        return new GradualColorChangeAnimation(this.input, this.colorSuffix, this.colors, length).reverse(this.reverse).transitionCount(this.transitionCount);
    }

    @Override
    public List<String> calculate() {

        final List<String> out = new ArrayList<>();

        for (int i = 0; i < this.colors.length; i++) {
            final Color color = this.colors[i];
            final int index = (this.colors.length == i + 1) ? 0 : (i + 1);
            final Color color2 = this.colors[index];

            out.addAll(insertEffect(
                    ChatColor.of(color) + this.colorSuffix,
                    ChatColor.of(color2) + this.colorSuffix
            ));

        }

        return out;
    }

    private List<String> insertEffect(String effectColor, String primaryColor) {
        final List<String> out = new ArrayList<>();

        if (this.reverse) {
            for (int i = this.input.length() - 1; i >= 0; i--) {
                out.addAll(Collections.nCopies(this.effectLength, addAtIndex(primaryColor + this.input, effectColor, i + primaryColor.length())));
            }
            if (this.transitionCount != 0) {
                out.addAll(Collections.nCopies(this.transitionCount, effectColor + this.input));
            }

        } else {
            for (int i = 0; i <= this.input.length(); i++) {
                out.addAll(Collections.nCopies(this.effectLength, addAtIndex(primaryColor + this.input, effectColor, i + primaryColor.length())));
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
}
