package dev.simplix.cirrus.common.effect;

import dev.simplix.cirrus.common.i18n.LocalizedItemStackModel;

public abstract class AbstractChangingItemEffect extends AbstractMenuEffect<LocalizedItemStackModel> {

    protected AbstractChangingItemEffect(LocalizedItemStackModel input, int effectLength) {
        super(input, effectLength);
    }

}
