package dev.simplix.cirrus.common.effects;

import com.google.common.base.Preconditions;
import dev.simplix.cirrus.common.effect.AbstractMenuAnimation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Will generate: <a href="https://media3.giphy.com/media/bvuK2KpLTip6vtemxY/giphy.gif?cid=790b7611af638e257a5f310fdd6d3015bfb9ee3119298826&rid=giphy.gif&ct=g">...</a>
 */
public class GradualColorChangeAnimation extends AbstractMenuAnimation<String> {

    protected final String primaryColor;
    protected final String secondaryColor;

    protected boolean reverse = false;
    protected int transitionCount = 0;

    public GradualColorChangeAnimation(
            String primaryColor,
            String input) {
        this(primaryColor, "§f§l", input, 2);
    }

    public GradualColorChangeAnimation(
            String primaryColor,
            String secondaryColor,
            String input,
            int effectLength) {
        super(input, effectLength);
        this.primaryColor = Preconditions.checkNotNull(primaryColor, "primaryColor must not be null");
        this.secondaryColor = Preconditions.checkNotNull(secondaryColor, "secondaryColor must not be null");
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
        return new GradualColorChangeAnimation(this.primaryColor, this.secondaryColor, this.input, length).reverse(this.reverse).transitionCount(this.transitionCount);
    }

    @Override
    public List<String> calculate() {

        final List<String> out = new ArrayList<>();
        out.addAll(insertEffect(this.primaryColor, this.secondaryColor));
        out.addAll(insertEffect(this.secondaryColor, this.primaryColor));

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
