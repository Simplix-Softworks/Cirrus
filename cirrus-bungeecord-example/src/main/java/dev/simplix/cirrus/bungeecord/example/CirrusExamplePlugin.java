package dev.simplix.cirrus.bungeecord.example;

import dev.simplix.cirrus.bungeecord.CirrusBungeeCord;
import dev.simplix.cirrus.bungeecord.example.commands.TestCommand;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class CirrusExamplePlugin extends Plugin {

    @Override
    public void onEnable() {
        CirrusBungeeCord.init(this);
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new TestCommand());
    }

}
