For dependencies and the repository see:
wiki/DependenciesAndRepository


```java
import dev.simplix.cirrus.spigot.CirrusSpigot;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {

  public void onEnable() {
    CirrusSpigot.init(this);
  }
}

```


### BungeeCord

```java

import dev.simplix.cirrus.bungeecord.CirrusBungeeCord;
import net.md_5.bungee.api.plugin.Plugin;

public class ExamplePlugin extends Plugin {
  @Override
  public void onEnable() {
      CirrusBungeeCord.init(this);
  }
}

```


### Velocity

```java
  @Inject
  public CirrusVelocityPlugin(ProxyServer server, Logger logger) {
    this.server = server;
    this.logger = logger;
  }

  @Subscribe
  public void onProxyInitialization(ProxyInitializeEvent event) {
    new CirrusVelocity(this, server, server.getCommandManager()).init();
    server.getCommandManager().register("menu", new CirrusTestCommand());
    logger.info("CirrusVelocityPlugin has been loaded!");
  }

```


### Example menu

A full list of example menus can be found [here](https://github.com/Simplix-Softworks/Cirrus/tree/v3/cirrus-api/src/main/java/dev/simplix/cirrus/menus/example).
    
```java
import com.google.common.collect.Iterators;
import dev.simplix.cirrus.actionhandler.ActionHandlers;
import dev.simplix.cirrus.item.CirrusItem;
import dev.simplix.cirrus.menus.SimpleMenu;
import dev.simplix.protocolize.data.ItemType;
import dev.simplix.protocolize.data.inventory.InventoryType;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;

public class NextMenu extends SimpleMenu {

  private final Iterator<ItemType> iterator = Iterators.cycle(
      ItemType.STONE,
      ItemType.EMERALD_BLOCK,
      ItemType.BONE_BLOCK,
      ItemType.IRON_BLOCK,
      ItemType.FIRE_CORAL_BLOCK,
      ItemType.COPPER_BLOCK);

  public NextMenu() {
    title("Next");
    type(InventoryType.GENERIC_9X5);

    set(CirrusItem.of(iterator.next(), "§aClick").slot(9 * 2 + 4).actionHandler("click"));

    row(5).get(8).set(CirrusItem.of(ItemType.DARK_OAK_DOOR, "§7Back").actionHandler("back"));

  }

  @Override
  protected void registerActionHandlers() {
    registerActionHandler("back", ActionHandlers.openMenu(new SelectMenu()));

    registerActionHandler(
        "click",
        ActionHandlers.changeClickedItemType(iterator::next));
  }
}

```


