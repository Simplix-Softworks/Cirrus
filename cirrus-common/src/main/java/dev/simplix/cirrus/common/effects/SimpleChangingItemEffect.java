package dev.simplix.cirrus.common.effects;

import dev.simplix.cirrus.common.i18n.LocalizedItemStackModel;
import dev.simplix.cirrus.common.effect.AbstractChangingItemEffect;

import java.util.List;

public class SimpleChangingItemEffect extends AbstractChangingItemEffect {
    private final List<LocalizedItemStackModel> values;

    protected SimpleChangingItemEffect(LocalizedItemStackModel basis, List<LocalizedItemStackModel> values, int effectLength) {
        super(basis, effectLength);
        this.values = values;
    }

    @Override
    public List<LocalizedItemStackModel> calculate() {
        return this.values;
    }
}
