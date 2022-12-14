---------------
Cirrus
---------------

__Menu Framework__

[![Build Status](http://ci.exceptionflug.de/buildStatus/icon?job=cirrusv3)](http://ci.exceptionflug.de/job/cirrusv3/) 
[![JetBrains IntelliJ Plugins](https://img.shields.io/jetbrains/plugin/v/15194-cirrus-tooling)](https://plugins.jetbrains.com/plugin/15194-cirrus-tooling) 
[![Discord](https://img.shields.io/discord/752533664696369204?label=Discord)](https://discord.simplixsoft.com/)
![GitHub](https://img.shields.io/github/license/Exceptionflug/Protocolize)

Cirrus is a powerful and versatile menu development framework for Protocolize.

Cirrus was created to make menu development easy and fun for everyone, whether you're a experienced
developer or just getting started. Cirrus is built on top of Protocolize, so it benefits from all
the features that Protocolize provides.
That enables you to quickly and easily create platform-independent menus for use in Minecraft.
Cirrus is designed to be highly compatible with all major 1.16+ Minecraft platforms, including
Spigot but also Velocity, BungeeCord.


### JavaDoc:
https://ci.exceptionflug.de/job/cirrusv3/13/javadoc/


### Project setup
See wiki/project setup

### Menus as JSON

```JSON
{
  "display-name-effect": {
    "type": "rgbcolorchangeanimation",
    "colors": [
      "§#0000ff",
      "§#00ffff"
    ],
    "colorSuffix": "§l",
    "step": 40.0,
    "input": "test",
    "effectLength": 2
  },
  "type": "ITEM_FRAME",
  "lore": [
    "test123"
  ],
  "amount": 1,
  "durability": -1,
  "hide-flags": 0,
  "nbt": {},
  "flags": [],
  "action-arguments": []
}
```

### Compatibility

Java: 17+

| Platform    | Supported? |
|-------------|------------|
| Spigot      | ✅          |
| Paper       | ✅          |
| BungeeCord  | ✅          |
| Waterfall   | ✅          |
| Velocity    | ✅          |

| Versions    | Supported? | Note(s)                            |
|-------------|------------|------------------------------------|
| 1.8-1.13    | ❌          | Untested, items will likely break  |
| 1.14-1.16.4 | ❎          | Partial support without guarantees |
| 1.16.5-1.19 | ✅          | Latest                             |

__You would like to add support for legacy versions?
Feel free to open a pull request! See: Contributing__

### Contributing:

CodeStyle: https://google.github.io/styleguide/javaguide.html

### Developer mode.

Cirrus can work as a plugin to rapidly test changes using the
"Cirrus-Developer-Mode". For this to work you will have to set an
environment variable named "simplix.dev" to "on".

![Image](https://i.imgur.com/DmP5ydJ.png)