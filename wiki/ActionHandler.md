# Action handlers

This page will introduce a technique with which you can handle an action which was performed on the
given item stack. This is called an `ActionHandler`. Action handlers are functions that get called
when a click on an `ItemStackWrapper` occurs.

## Using action handlers

Action handlers need to be registered at the construction of a menu object instance. For example:

```java

public class ExampleMenu {

  public ExampleMenu(PlayerWrapper player) {
  }

  @Override
  protected void registerActionHandlers() {
    registerActionHandler("tnt", click -> {
      click.clickedMenu().value().remove(click.slot());
      title("Hello, {viewer}");
      click.clickedMenu().update();
      click.player().sendMessage("It simply works :)");
      return CallResult.DENY_GRABBING; // Not necessary
    });
  }
}
```

This example removes the clicked item, changes the inventory title to "Hello, {viewer}" and sends a
message to the player, that the click logic simply works.

The `registerActionHandler` method requires an unique identifier string and, of course,
an `ActionHandler` instance. The string that was provided as the first argument is used
in `ItemStackWrappers` to determine which action handler should be called when a click on an item
occurs.

The action handler itself has only one method: the `handle` method. This method will be called if a
click on any item was performed which has the name of the action handler configured to be called.
The `handle` method's return type is an instance of `CallResult`.

### CallResult

CallResult is an enumeration which allows two different instances: `DENY_GRABBING`
and `ALLOW_GRABBING`. However which one was returned, the player will be able to pick up the clicked
item to hold it on the cursor. In common practice, only `DENY_GRABBING` is used.

### Click

The passed `Click` instance contains information about the click that was performed on the item
stack. It contains the following information:

- `clickType(): ClickType` The type of click (Protocolize,
  click [here](https://github.com/Exceptionflug/protocolize/blob/master/protocolize-api/src/main/java/de/exceptionflug/protocolize/api/ClickType.java)
  for more information)
- `clickedMenu(): Menu` The clicked menu instance
- `arguments(): List<String>` A list of strings that have been passed by the item stack wrapper
- `<T extends ItemStackWrapper> clickedItem(): T` The instance of the clicked item stack wrapper
- `slot(): int` The clicked slot id


### ActionHandlers

The ActionHandlers` class is a utility class that provides a set of common ActionHandler
implementations that can be used to handle menu item clicks. An ActionHandler is a 
functional interface that is used to specify the behavior of a menu item when it is clicked.
