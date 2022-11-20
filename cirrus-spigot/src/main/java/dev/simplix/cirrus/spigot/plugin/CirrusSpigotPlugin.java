package dev.simplix.cirrus.spigot.plugin;

import dev.simplix.cirrus.Cirrus;
import dev.simplix.cirrus.actionhandler.ActionHandlers;
import dev.simplix.cirrus.item.CirrusItem;
import dev.simplix.cirrus.item.Items;
import dev.simplix.cirrus.menus.AbstractBrowser;
import dev.simplix.cirrus.menus.SimpleMenu;
import dev.simplix.cirrus.menus.example.SelectMenu;
import dev.simplix.cirrus.model.Click;
import dev.simplix.cirrus.service.MenuBuildService;
import dev.simplix.cirrus.spigot.CirrusSpigot;
import dev.simplix.cirrus.spigot.util.ProtocolVersionUtil;
import dev.simplix.cirrus.spigot.wrapper.SpigotPlayerWrapper;
import dev.simplix.protocolize.api.SoundCategory;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import dev.simplix.protocolize.data.ItemType;
import dev.simplix.protocolize.data.Sound;
import dev.simplix.protocolize.data.inventory.InventoryType;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

@Slf4j
public class CirrusSpigotPlugin extends JavaPlugin implements Listener {

  @Override
  public void onEnable() {
    log.info("Enabling Cirrus test plugin");
    new CirrusSpigot(this).init();
    Bukkit.getPluginManager().registerEvents(this, this);
    log.info("--> Detected Minecraft version "
             + ProtocolVersionUtil.versionString()
             + " on "
             + ProtocolVersionUtil.serverProtocolVersion());
  }

  @EventHandler
  public void onCmd(AsyncPlayerChatEvent asyncPlayerChatEvent) {

    if (!"menu".equalsIgnoreCase(asyncPlayerChatEvent.getMessage())) {
      return;
    }

    SimpleMenu exampleMenu = new SelectMenu();
    SpigotPlayerWrapper playerWrapper = new SpigotPlayerWrapper(asyncPlayerChatEvent.getPlayer());

    Bukkit.getScheduler().runTask(this, () -> {
      var displayedMenu = Cirrus
          .service(MenuBuildService.class)
          .buildAndOpenMenu(exampleMenu, playerWrapper);

    });
  }
}
