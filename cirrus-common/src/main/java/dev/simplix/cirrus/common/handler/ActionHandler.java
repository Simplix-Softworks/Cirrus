package dev.simplix.cirrus.common.handler;

import dev.simplix.cirrus.common.business.InventoryMenuItemWrapper;
import dev.simplix.cirrus.common.model.CallResult;
import dev.simplix.cirrus.common.model.Click;

/**
 * {@link ActionHandler}s are functions that get called when a click on an {@link
 * InventoryMenuItemWrapper} occurs.
 */
@FunctionalInterface
public interface ActionHandler {

    CallResult handle(Click click);

}
