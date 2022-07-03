package dev.simplix.cirrus.common.effect;

import dev.simplix.cirrus.common.menu.Menu;

public interface Animated extends Menu {
    void update();

    @Override
    default void handleClose(boolean inventorySwitch) {
        MenuAnimator.remove(this);
    }
}
