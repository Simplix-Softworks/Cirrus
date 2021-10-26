package dev.simplix.cirrus.api.menu;

import dev.simplix.cirrus.api.business.InventoryItemWrapper;

/**
 * {@link ActionHandler}s are functions that get called when a click on an {@link
 * InventoryItemWrapper} occurs.
 */
@FunctionalInterface
public interface ActionHandler {

    CallResult handle(Click click);

}
