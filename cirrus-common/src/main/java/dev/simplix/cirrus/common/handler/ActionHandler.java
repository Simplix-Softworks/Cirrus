package dev.simplix.cirrus.common.handler;

import dev.simplix.cirrus.common.business.InventoryItemWrapper;
import dev.simplix.cirrus.common.model.CallResult;
import dev.simplix.cirrus.common.model.Click;

/**
 * {@link ActionHandler}s are functions that get called when a click on an {@link
 * InventoryItemWrapper} occurs.
 */
@FunctionalInterface
public interface ActionHandler {

    CallResult handle(Click click);

}
