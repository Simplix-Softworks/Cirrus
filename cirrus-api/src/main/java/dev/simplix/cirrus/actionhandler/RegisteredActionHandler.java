package dev.simplix.cirrus.actionhandler;

/**
 * A simple data class representing a registered `ActionHandler`.
 * This class associates a unique `String` name with an `ActionHandler` instance. It is used to
 * register `ActionHandler`s with the `ActionHandlerRegistry` so that they can be easily accessed
 * and used by menu items.
 */
public record RegisteredActionHandler(String name, ActionHandler handler) {

}
