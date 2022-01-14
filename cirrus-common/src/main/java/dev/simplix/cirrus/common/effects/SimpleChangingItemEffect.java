package dev.simplix.cirrus.common.effects;

import dev.simplix.cirrus.common.item.MenuItem;
import dev.simplix.cirrus.common.effect.AbstractChangingItemEffect;

import java.util.List;

public class SimpleChangingItemEffect extends AbstractChangingItemEffect {
    private final List<MenuItem> values;

    protected SimpleChangingItemEffect(MenuItem basis, List<MenuItem> values, int effectLength) {
        super(basis, effectLength);
        this.values = values;
    }

    @Override
    public List<MenuItem> calculate() {
        return this.values;
    }
}
