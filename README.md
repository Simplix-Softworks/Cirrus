<p align="center">
  <img src="https://i.imgur.com/vmfZcvS.png" />
</p>

# Cirrus
[![Build Status](http://ci.exceptionflug.de/buildStatus/icon?job=Cirrus)](http://ci.exceptionflug.de/job/Cirrus/) [![JetBrains IntelliJ Plugins](https://img.shields.io/jetbrains/plugin/v/15194-cirrus-tooling)](https://plugins.jetbrains.com/plugin/15194-cirrus-tooling) [![Discord](https://img.shields.io/discord/752533664696369204?label=Discord)](https://discord.simplixsoft.com/) ![GitHub](https://img.shields.io/github/license/Exceptionflug/Protocolize)

Cirrus enables you to develop platform independent versatile graphical menus for Spigot and BungeeCord! The goal of Cirrus is to aim for maximum compatibility. Cirrus had been tested on several well known BungeeCord-Forks as well on all Spigot versions beginning from 1.8 till the latest 1.16.3 release.

## Platforms
- BungeeCord
- Spigot

## Tooling
Cirrus offers an IntelliJ plugin to enhance the usability of Cirrus.
It provides a realtime preview while editing menu configuration files.

[![JetBrains IntelliJ plugins](https://img.shields.io/jetbrains/plugin/d/15194-cirrus-tooling)](https://plugins.jetbrains.com/plugin/15194-cirrus-tooling)

![Tooling](https://i.imgur.com/88pvZ8G.gif)

## Requirements
### SimplixCore
Cirrus is using the [SimplixCore framework](https://github.com/Simplix-Softworks/SimplixCore) and it's possibility to develop platform independent software for Minecraft use cases.
### Protocolize
Cirrus is powered by [Protocolize](https://github.com/Exceptionflug/protocolize). A lightweight protocol manipulation library by one of the Simplix Softworks founders, Exceptionflug. Protocolize enables you to do so much more on BungeeCord!

## Installation
### Manual installation
Assuming you have already installed SimplixCore on your server, just download the jar file for your platform (Spigot or BungeeCord) and drop it into the libraries folder of your server.

**I have no libraries folder. What should I do?**

Just follow the installation instructions of SimplixCore and start your server at least one time after you installed SimplixCore.

### Automatic installation as dependency
When you want to use Cirrus in your SimplixApplication, just create a `dependencies.json` in your resource folder and fill it with the following content:
```json
{  
  "repositories": [  
    {
      "id": "simplixsoft-public",  
      "url": "https://repo.simplix.dev/repository/simplixsoft-public/"  
    }  
  ],
  "dependencies": [  
    {
      "groupId": "dev.simplix.cirrus",  
      "artifactId": "cirrus-bungeecord", 
      "version": "1.0-SNAPSHOT",
      "platform": "BUNGEECORD",
      "type": "shared-library"  
    },
    {
      "groupId": "dev.simplix.cirrus",  
      "artifactId": "cirrus-spigot", 
      "version": "1.0-SNAPSHOT",
      "platform": "SPIGOT",
      "type": "shared-library"   
    }
  ]
}
```
SimplixCore will automatically download the latest cirrus-bungeecord (or spigot) on server startup.

### More developer documentation
Check out the [wiki](https://github.com/Simplix-Softworks/Cirrus/wiki) for further information on how to use Cirrus in your application.
