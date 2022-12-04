# CirrusItem

CirrusItem is the main entry point for the Cirrus framework.
It is a class that extends the ItemStack class from the Protocolize API, 
which represents an item in the Minecraft game.
The CirrusItem class adds additional functionality to the ItemStack class, such as the ability to store an action handler and action arguments, and the ability to apply effects to the display name and lore of the item. The class also provides several static methods for creating instances of the CirrusItem class with different settings, such as the of(ItemType) method, which creates a CirrusItem with a given item type, and the ofSkullHash(String) method, which creates a CirrusItem with a player head texture. The CirrusItem class also overrides some methods from the ItemStack class to provide custom implementations, such as the displayName() and lore() methods, which suppress 
the display of adventure components in the item's display name and lore.

## Creating a CirrusItem

To create a new CirrusItem, you can use the default constructor new CirrusItem(),
which creates a CirrusItem with a default item type of STONE. Alternatively, 
you can use the of(ItemType) static method, which creates a CirrusItem with a
specified item type and a default amount of 1. For example:

```java
// Create a new CirrusItem with the item type IRON_SWORD
CirrusItem ironSword = CirrusItem.of(ItemType.IRON_SWORD);

// Create a new CirrusItem with the item type PLAYER_HEAD and the specified player head texture
CirrusItem playerHead = CirrusItem.ofSkullHash("Skull here");
```

If you want to create a CirrusItem with a custom amount or durability, 
you can use the amount(byte) and durability(short) methods. For example:

````java
// Create a new CirrusItem with the item type IRON_SWORD and an amount of 64
CirrusItem ironSword = CirrusItem.of(ItemType.IRON_SWORD).amount((byte) 64);
````


### Setting the displayname

To apply a custom display name to a CirrusItem, you can use the CirrusItem#displayName(String) method. This method takes a string representing the new display name for the item, and applies it to the item. For example:

````java
CirrusItem myItem = CirrusItem.of(ItemType.DIAMOND_SWORD);

// Set the display name of the item to "My Sword"
myItem.displayName("My Sword");
````

This will change the display name of the item to "My Sword".
Note that the CirrusItem#displayName(String) method also applies color codes to the 
display name, so you can use the & character followed by a color code to apply colors to the
display name. For example:

````java
CirrusItem myItem = CirrusItem.of(ItemType.DIAMOND_SWORD);

// Set the display name of the item to "My &4Red Sword"
myItem.displayName("My &4Red Sword");
````
This will set the display name of the item to "My Red Sword", with the word "Red" being displayed
in red. You can use this method to apply any custom display name to a  CirrusItem.


### Important constructor methods

- public CirrusItem(): Constructs a new CirrusItem with a default item type of STONE.
- public CirrusItem(BaseItemStack baseItemStack): Constructs a new CirrusItem from the given 
  BaseItemStack.
- public CirrusItem(ItemType itemType): Constructs a new CirrusItem with the given item type and a 
  default amount of 1.
- public CirrusItem(ItemType itemType, byte amount): Constructs a new CirrusItem with the given 
  item type and amount, and a default durability of -1.
- public CirrusItem(ItemType itemType, byte amount, short durability): Constructs a new CirrusItem 
  with the given item type, amount, and durability.
- public CirrusItem(ItemType itemType, byte amount, short durability, int hideFlags): Constructs a 
  new CirrusItem with the given item type, amount, durability, and hide flags.
- public static CirrusItem of(ItemType itemType): Creates a new CirrusItem with the given item 
  type and a default amount of 1.
- public static CirrusItem of(ItemType itemType, byte amount): Creates a new CirrusItem with the 
  given item type and amount, and a default durability of -1.
- public static CirrusItem of(ItemType itemType, byte amount, short durability): Creates a new 
  CirrusItem with the given item type, amount, and durability.
- public static CirrusItem of(ItemType itemType, byte amount, short durability, int hideFlags): 
  Creates a new CirrusItem with the given item type, amount, durability, and hide flags.
- public static CirrusItem of(@NonNull ItemStack itemStack): Creates a new CirrusItem from the given


### Mutators
- displayName(String): Sets the display name of the CirrusItem
- displayNameEffect(AbstractMenuEffect<String>): Sets the display name effect of the CirrusItem
- actionHandler(String): Sets the action handler of the CirrusItem
- actionArguments(List<String>): Sets the action arguments of the CirrusItem
- lore(String...): Sets the lore of the CirrusItem
- lore(List<String>): Sets the lore of the CirrusItem
- slot(int): Sets the slot of the CirrusItem
