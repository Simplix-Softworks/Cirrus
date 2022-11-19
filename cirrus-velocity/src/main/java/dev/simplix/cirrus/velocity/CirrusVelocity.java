package dev.simplix.cirrus.velocity;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.simplix.cirrus.Cirrus;
import dev.simplix.cirrus.menu.MenuUpdateTask;
import dev.simplix.cirrus.service.MenuBuildService;
import dev.simplix.cirrus.velocity.listener.QuitListener;
import dev.simplix.cirrus.velocity.menubuilder.VelocityMenuBuildService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CirrusVelocity {
  private final Object plugin;
  private final ProxyServer proxyServer;
  private final CommandManager commandManager;

  public void init() {
    Cirrus.init();
    proxyServer.getEventManager().register(plugin, new QuitListener());
    Cirrus.registerService(MenuBuildService.class, new VelocityMenuBuildService());

    proxyServer
            .getScheduler()
            .buildTask(plugin, new MenuUpdateTask())
            .repeat(50, TimeUnit.MILLISECONDS)
            .schedule();
  }
}
