package dev.simplix.cirrus.common.effects;

import dev.simplix.cirrus.common.effect.AbstractChangingItemAnimation;
import dev.simplix.cirrus.common.item.CirrusItem;

import java.util.List;

public class SimpleChangingItemAnimation extends AbstractChangingItemAnimation {
    private final List<CirrusItem> values;

    protected SimpleChangingItemAnimation(CirrusItem basis, List<CirrusItem> values, int effectLength) {
        super(basis, effectLength);
        this.values = values;
    }

    @Override
    public List<CirrusItem> calculate() {
        return this.values;
    }
}
