Cirrus is a powerful and versatile menu development framework for Protocolize. It is built on top of Protocolize, and is designed to be highly compatible with all major 1.16+ Minecraft platforms, including Spigot, Velocity, and BungeeCord.

Cirrus is designed to make menu development easy and fun for everyone, regardless of experience level. It offers a rich set of features that enable developers to quickly and easily create platform-independent menus for use in Minecraft.

At a high level, the architecture of Cirrus can be described as follows:


- The core of the framework is the MenuSchematic interface, which defines the structure and behavior of a menu.
- MenuSchematic implementations are responsible for managing the content of a menu, including the items that are displayed, their layout, and their behavior when interacted with by players.
- The MenuSchematic interface also provides a number of utility methods that make it easy to manipulate the content of a menu, such as adding and removing items, setting the title and layout of the menu, and handling player interactions.
- MenuSchematic instances are then used to create a SimpleMenu, which is a concrete implementation of the Menu interface.
- In addition to the core MenuSchematic interface,
  Cirrus provides a number of other classes and interfaces that can be used to further customize 
  and extend the functionality of menus. These include the MenuContent POJO, which defines the 
  contents of a menu, and the CirrusItem class, which represents an individual menu item and provides methods for 
  manipulating its appearance and behavior.
- Finally, the ActionHandler interface provides a way for developers to define custom behavior for menu items, allowing them to specify what should happen when a player interacts with an item in a menu.

Overall, the architecture of Cirrus is designed to be flexible and extensible, allowing developers to easily create and customize menus to meet their specific needs.



