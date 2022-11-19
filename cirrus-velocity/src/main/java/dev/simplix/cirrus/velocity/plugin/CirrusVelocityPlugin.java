package dev.simplix.cirrus.velocity.plugin;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.simplix.cirrus.menus.example.SelectMenu;
import dev.simplix.cirrus.velocity.CirrusVelocity;
import dev.simplix.cirrus.velocity.wrapper.VelocityPlayerWrapper;
import org.slf4j.Logger;

@Plugin(id = "cirrusplugin", name = "CirrusPlugin", version = "3.0.0-SNAPSHOT",
    authors = {"SimplixSoft"}, dependencies = @Dependency(id =
    "protocolize"))
public class CirrusVelocityPlugin {

  private final ProxyServer server;
  private final Logger logger;

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

  @Subscribe
  public void onChat(PlayerChatEvent event) {
    if ("menu".equals(event.getMessage())) {
      event.setResult(PlayerChatEvent.ChatResult.denied());
      new SelectMenu().display(new VelocityPlayerWrapper(event.getPlayer()));
    }
  }
}
