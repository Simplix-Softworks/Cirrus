package dev.simplix.cirrus.actionhandler;

import dev.simplix.cirrus.model.CallResult;
import dev.simplix.cirrus.model.Click;

/**
 * Defines a handler for processing menu item clicks. Implementations of this interface can be used
 * to define custom behavior for menu items. When a menu item is clicked, the `handle` method of the
 * `ActionHandler` associated with that item will be called, passing in the {@link Click} object
 * representing the click event. The `handle` method can then perform any desired actions and return
 * a {@link CallResult} indicating whether the event should be allowed to continue to other event
 * handlers.
 */
public interface ActionHandler {

  /**
   * Handles a menu item click.
   *
   * @param click The {@link Click} object representing the click event
   * @return A {@link CallResult} indicating whether the event should be allowed to continue to other
   * event handlers
   */
  CallResult handle(Click click);
}
