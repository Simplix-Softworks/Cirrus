package dev.simplix.cirrus.velocity.example;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.simplix.cirrus.velocity.CirrusVelocity;
import dev.simplix.cirrus.velocity.example.commands.TestCommand;

@Plugin(
        id = "cirrusexample",
        name = "CirrusExample",
        version = "1.0",
        authors = "Xefreh"
)

public class CirrusExamplePlugin {

    private final ProxyServer proxyServer;
    private final CommandManager commandManager;

    @Inject
    public CirrusExamplePlugin(ProxyServer proxyServer, CommandManager commandManager) {
        this.proxyServer = proxyServer;
        this.commandManager = commandManager;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        CirrusVelocity.init(proxyServer, this);
        commandManager.register("test", new TestCommand());
    }


}
