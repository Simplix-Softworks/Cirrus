package dev.simplix.cirrus.model;

/**
 * The {@link CallResult} enum is used to indicate the result of a call to a handler method that
 * processes a click on an item in a menu. It has two values:
 * <ul>
 * <li>{@link CallResult#DENY_GRABBING}: Indicates that the click should not be processed, and the item should not be grabbed from the menu.</li>
 * <li>{@link CallResult#ALLOW_GRABBING}: Indicates that the click should be processed, and the item should be grabbed from the menu.</li>
 * </ul>
 */
public enum CallResult {

  DENY_GRABBING, ALLOW_GRABBING

}

