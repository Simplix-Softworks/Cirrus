package dev.simplix.cirrus.bungee.plugin;

import dev.simplix.cirrus.Cirrus;
import dev.simplix.cirrus.bungee.CirrusBungee;
import dev.simplix.cirrus.bungee.wrapper.BungeePlayerWrapper;
import dev.simplix.cirrus.menus.example.SelectMenu;
import dev.simplix.cirrus.service.MenuBuildService;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class CirrusBungeePlugin extends Plugin implements Listener {

  @Override
  public void onEnable() {
    new CirrusBungee(this).init();
    ProxyServer.getInstance().getPluginManager().registerListener(this, this);
  }

  @EventHandler
  public void onChat(ChatEvent chatEvent) {
    if (chatEvent.isCommand()) {
      return;
    }
    if (!"menu".equals(chatEvent.getMessage())) {
      return;
    }

    final SelectMenu selectMenu = new SelectMenu();

    if (chatEvent.getSender() instanceof ProxiedPlayer player) {
      final BungeePlayerWrapper playerWrapper = new BungeePlayerWrapper(player);
      var displayedMenu = Cirrus.service(MenuBuildService.class).buildAndOpenMenu(selectMenu, playerWrapper);
    }
  }

}
