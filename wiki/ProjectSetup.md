# Maven dependencies
You need to add some dependencies in order to work with Cirrus. Please ensure you are using the latest version of the dependencies. This article may not contain up-to-date version numbers of dependencies.

Replace ```VERSION``` with the latest Cirrus version.

## Repositories:
```xml
<repository>
  <id>simplixsoft-public</id>
  <url>https://repo.simplix.dev/repository/simplixsoft-public/</url>
</repository>
```
For Spigot also add:
```xml
<repository>
  <id>exceptionflug</id>
  <url>https://mvn.exceptionflug.de/repository/exceptionflug-public/</url>
</repository>
```
## Dependencies & Initialization
### Spigot
```xml
<dependency>  
  <groupId>dev.simplix.cirrus</groupId>
  <artifactId>cirrus-spigot</artifactId>
  <version>VERSION</version>
´</dependency>
<dependency>  
  <groupId>dev.simplix</groupId>  
  <artifactId>protocolize-api</artifactId>  
  <version>2.1.0</version>  
</dependency>
```

```java
import dev.simplix.cirrus.spigot.CirrusSpigot;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {
  
  public void onEnable() {
    CirrusSpigot.init(this);
  }
}

```

A full Spigot-Cirrus setup can be found [here](https://github.com/Simplix-Softworks/Cirrus/tree/master/cirrus-spigot-example).

### BungeeCord
```xml
<dependency>  
  <groupId>dev.simplix.cirrus</groupId>
  <artifactId>cirrus-bungeecord</artifactId>
  <version>VERSION</version>
</dependency>
```

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

Full BungeeCord-Cirrus setup [here](https://github.com/Simplix-Softworks/Cirrus/tree/master/cirrus-bungeecord-example).

### Velocity
```xml
<dependency>  
  <groupId>dev.simplix.cirrus</groupId>
  <artifactId>cirrus-velocity</artifactId>
  <version>VERSION</version>
</dependency>
```

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



