### CirrusItem

CirrusItem is the main entry point for the Cirrus framework.
It is a class that extends the ItemStack class from the Protocolize API,
which represents an item in the Minecraft game.
The CirrusItem class adds additional functionality to the ItemStack class, such as the ability to
store an action handler and action arguments, and the ability to apply effects to the display name
and lore of the item. The class also provides several static methods for creating instances of the
CirrusItem class with different settings, such as the of(ItemType) method, which creates a
CirrusItem with a given item type, and the ofSkullHash(String) method, which creates a CirrusItem
with a player head texture. The CirrusItem class also overrides some methods from the ItemStack
class to provide custom implementations, such as the displayName() and lore() methods, which
suppress
the display of adventure components in the item's display name and lore.

### Items

The Items class is a utility class that provides methods for creating menu items with special
effects that make use of the accent colors defined in the StandardColorConfiguration class. The
defaultBottomRowProvider field is a BiConsumer that is used to provide the default bottom row for a
menu. The withSpectrumEffect and withWaveEffect methods both take an ItemType, a name, and a
variable number of lores (descriptive text displayed below the item name) as arguments. These
methods return a CirrusItem object with a special effect applied to the item's name. The
withSpectrumEffect method applies the SpectrumEffect effect, which animates the color of the item
name using the accent colors defined in StandardColorConfiguration. The withWaveEffect method
applies the WaveEffect effect, which animates the color of the item name in a wave-like pattern
between white and the accent color defined in StandardColorConfiguration.

# SpectrumEffect

The SpectrumEffect class is a menu effect that displays text in a rainbow-like gradient. It accepts
a string input, a list of colors, and a few optional parameters such as the color suffix and step
size, and calculates a sequence of strings with the input text in different colors. The resulting
list of strings can be used to animate the text in a menu. The class has a few static factory
methods for creating instances with common configurations, such as fat which creates an effect with
a bold font and large step size. The class also has setter methods for modifying the configuration
of the effect.

# WaveEffect

The class WaveFormat is used to generate an animated "wave" effect on a given input string. The
effect is created by alternating the colors of the characters in the input string between a set of
given colors. The direction of the wave (forward or backward) and the number of transitions between
the two colors can be specified. The calculate method is used to generate the animation frames for
the effect.

# ActionHandlers

The ActionHandlers class is a utility class that provides common action handlers for use in a
streamlined form. An action handler is a functional interface that is used to handle a specific
action, such as a click on a menu item. The ActionHandlers class provides several methods for
creating action handlers that perform common actions, such as changing the type of an item in a
menu, sending a message to a player, or opening a menu. These methods allow developers to easily
create and use action handlers without having to implement the action handler interface themselves.

### MenuSchematic

A MenuSchematic is a schematic of a menu.
It contains the whole fundamentals of a menu, but does not contain any data.

### SimpleMenu

A SimpleMenu is a menu that is based on a MenuSchematic.
It provides the simplest way to create a menu.

### AbstractBrowser
Abstract implementation of a browser Represent a menu with a list of items that are browsable.
