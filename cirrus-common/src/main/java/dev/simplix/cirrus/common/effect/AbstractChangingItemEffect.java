package dev.simplix.cirrus.common.effect;

import dev.simplix.cirrus.common.item.MenuItem;

public abstract class AbstractChangingItemEffect extends AbstractMenuEffect<MenuItem> {

    protected AbstractChangingItemEffect(MenuItem input, int effectLength) {
        super(input, effectLength);
    }

}
