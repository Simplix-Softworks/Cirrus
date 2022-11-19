package dev.simplix.cirrus.bungee;

import dev.simplix.cirrus.Cirrus;
import dev.simplix.cirrus.bungee.listeners.QuitListener;
import dev.simplix.cirrus.bungee.menubuilder.BungeeMenuBuildService;
import dev.simplix.cirrus.menu.MenuUpdateTask;
import dev.simplix.cirrus.service.MenuBuildService;
import dev.simplix.protocolize.api.Protocolize;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

@RequiredArgsConstructor
public class CirrusBungee {

  private final Plugin plugin;

  public void init() {
    Cirrus.init();

    try {
      String version = Protocolize.version();
    } catch (Throwable throwable) {
      plugin.getLogger().severe(
          "Protocolize-Plugin required to run CirrusBungee"
                               );
      return;
    }

    ProxyServer.getInstance().getPluginManager().registerListener(plugin, new QuitListener());
    ProxyServer
        .getInstance()
        .getScheduler()
        .schedule(plugin, new MenuUpdateTask(), 0L, 50L, TimeUnit.MILLISECONDS);
    Cirrus.registerService(MenuBuildService.class, new BungeeMenuBuildService());
  }
}
