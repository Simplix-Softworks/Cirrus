<p align="center">
  <img src="https://i.imgur.com/vmfZcvS.png" />
</p>


# Cirrus
[![Build Status](http://ci.exceptionflug.de/buildStatus/icon?job=Cirrus)](http://ci.exceptionflug.de/job/Cirrus/) [![JetBrains IntelliJ Plugins](https://img.shields.io/jetbrains/plugin/v/15194-cirrus-tooling)](https://plugins.jetbrains.com/plugin/15194-cirrus-tooling) [![Discord](https://img.shields.io/discord/752533664696369204?label=Discord)](https://discord.simplixsoft.com/) ![GitHub](https://img.shields.io/github/license/Exceptionflug/Protocolize)

Menu Framework.
Cirrus enables you to develop platform-independent versatile graphical menus for Spigot, BungeeCord, and Velocity! The
goal of Cirrus is to aim for maximum compatibility. Cirrus had been tested on several well-known BungeeCord-Forks as
well on all Spigot versions beginning from 1.16.5 till the latest release. The first-ever way of creating menus for Velocity.

# WIP

Cirrus is currently WIP. The documentation is invalid in parts. We will publish a stable version soon.
Spigot & BungeeCord implementations will work, Velocity implementations are currently very experimental.
We aim to publish documentation & production ready versions in the coming month.

## Version
Current version is:
- Stable: 2.0.0
- Development: 2.1.0-SNAPHOT

## PR

If you have any improvements regarding Cirrus feel free to create a PR. We are happy to review & merge it.
Keep in mind that to use our codestyle as defined in .editorconfig.

## Platforms

On Spigot Cirrus is fully independent of third party plugins. Protocolize is needed only only if Cirrus is used on
proxies like BungeeCord or Velocity.

- Spigot 
- BungeeCord
- Velocity (WIP)

## Supported Bukkit Versions

Due to the changes in the API we only officially support 1.16.5-LATEST (which is 1.19 by the time this is written)
Since Cirrus is designed to be mostly version independent, older versions will likely function as well but they are deprecated (so we won't attempt to resolve issues with these versions).

## Tooling

Cirrus offers an IntelliJ plugin to enhance the usability of Cirrus. It provides a realtime preview while editing menu
configuration files.

[![JetBrains IntelliJ plugins](https://img.shields.io/jetbrains/plugin/d/15194-cirrus-tooling)](https://plugins.jetbrains.com/plugin/15194-cirrus-tooling)

![Tooling](https://i.imgur.com/88pvZ8G.gif)

## Requirements

### Protocolize

Cirrus is powered by [Protocolize](https://github.com/Exceptionflug/protocolize). A protocol manipulation library by one
of the Simplix Softworks founders, Exceptionflug. Protocolize enables you to do so much more on BungeeCord/Velocity!

## Installation

Just shade and relocate the platform module into your own plugin. Don't forget to call the `CirrusSpigot`
, `CirrusBungeeCord` or `CirrusVelocity` init method.

### More developer documentation

Check out the [wiki](https://github.com/Simplix-Softworks/Cirrus/wiki) for further information on how to use Cirrus in
your application.
