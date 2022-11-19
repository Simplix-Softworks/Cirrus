package dev.simplix.cirrus.spigot.plugin;


import dev.simplix.cirrus.Cirrus;
import dev.simplix.cirrus.actionhandler.ActionHandlers;
import dev.simplix.cirrus.item.CirrusItem;
import dev.simplix.cirrus.item.Items;
import dev.simplix.cirrus.menus.AbstractBrowser;
import dev.simplix.cirrus.menus.SimpleMenu;
import dev.simplix.cirrus.model.Click;
import dev.simplix.cirrus.service.MenuBuildService;
import dev.simplix.cirrus.spigot.CirrusSpigot;
import dev.simplix.cirrus.spigot.util.ProtocolVersionUtil;
import dev.simplix.cirrus.spigot.wrapper.SpigotPlayerWrapper;
import dev.simplix.protocolize.data.ItemType;
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
    log.info("--> Detected Minecraft version " + ProtocolVersionUtil.versionString() + " on " + ProtocolVersionUtil.serverProtocolVersion());
  }

  @EventHandler
  public void onCmd(AsyncPlayerChatEvent asyncPlayerChatEvent) {

    if (!"menu".equalsIgnoreCase(asyncPlayerChatEvent.getMessage())) {
      return;
    }

    SimpleMenu exampleMenu = new SelectMenu();
    SpigotPlayerWrapper playerWrapper = new SpigotPlayerWrapper(asyncPlayerChatEvent.getPlayer());

    Bukkit.getScheduler().runTask(this, () -> {
      var displayedMenu = Cirrus.service(MenuBuildService.class).buildAndOpenMenu(exampleMenu, playerWrapper);

    });
  }

  static class ExampleBrowser extends AbstractBrowser<ItemType>{

    @Override
    protected void handleClick(Click click, ItemType value) {

    }

    @Override
    protected Collection<ItemType> elements() {
      return null;
    }

    @Override
    protected CirrusItem map(ItemType element) {
      return null;
    }
  }

  static class ActionMenu extends SimpleMenu {

    public ActionMenu() {
      title("Action");
      set(
              Items.withWaveEffect(
                              ItemType.EMERALD_BLOCK,
                              "Back",
                              "§7Get back to the main menu")
                      .slot(13)
                      .actionHandler("back")
      );
    }

    @Override
    protected void registerActionHandlers() {
      registerActionHandler("back", ActionHandlers.openMenu(new SelectMenu()));
    }

    @Override
    public int updateTicks() {
      return 6;
    }
  }


  static class SelectMenu extends SimpleMenu {

    public SelectMenu() {
      title("Select Menu");
      type(InventoryType.GENERIC_9X5);
      set(
              Items.withSpectrumEffect(
                              ItemType.STONE,
                              "Click to open the browser menu",
                              "§7Open the action menu")
                      .actionHandler("browser")
                      .slot(0)
      );

      set(Items.withSpectrumEffect(
                      ItemType.DIAMOND_BLOCK,
                      "Action menu",
                      "§7Open the action menu")
              .actionHandler("select")
              .slot(2)
      );
    }

    @Override
    protected void registerActionHandlers() {
      registerActionHandler("select", ActionHandlers.openMenu(new ActionMenu()));
      registerActionHandler("browser", ActionHandlers.openMenu(new ExampleBrowser()));
    }

    @Override
    public int updateTicks() {
      return 2;
    }
  }
}
