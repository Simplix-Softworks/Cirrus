package dev.simplix.cirrus.common.effect;

import dev.simplix.cirrus.common.item.CirrusItem;

public abstract class AbstractChangingItemAnimation extends AbstractMenuAnimation<CirrusItem> {

    protected AbstractChangingItemAnimation(CirrusItem input, int effectLength) {
        super(input, effectLength);
    }

}
