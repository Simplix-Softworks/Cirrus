package dev.simplix.cirrus.common.menu;

import de.exceptionflug.protocolize.inventory.InventoryType;
import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.i18n.LocalizedItemStackModel;
import dev.simplix.cirrus.api.i18n.Localizer;
import dev.simplix.cirrus.api.menu.ActionHandler;
import dev.simplix.cirrus.api.menu.Container;
import dev.simplix.cirrus.api.menu.Menu;
import dev.simplix.cirrus.api.menu.MenuBuilder;
import dev.simplix.cirrus.api.model.ItemStackModel;
import dev.simplix.core.common.Replacer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

@Getter
@Accessors(fluent = true)
@Slf4j
public abstract class AbstractMenu implements Menu {

  private static final AtomicInteger ID_GENERATOR = new AtomicInteger();

  private final Map<String, ActionHandler> actionHandlerMap = new HashMap<>();
  private final Container topContainer;
  private final Container bottomContainer;
  private final InventoryType inventoryType;
  private final Locale locale;
  private final PlayerWrapper player;
  private final int internalId = ID_GENERATOR.incrementAndGet();
  private Supplier<String[]> replacements;
  private ActionHandler customActionHandler;
  private MenuBuilder menuBuilder;
  private Object nativeInventory;
  private String title;

  public AbstractMenu(
      @NonNull PlayerWrapper player,
      @NonNull InventoryType inventoryType,
      @NonNull Locale locale) {
    this.inventoryType = inventoryType;
    this.locale = locale;
    this.player = player;
    replacements = () -> new String[]{"viewer", player.name()};
    topContainer = new ItemContainer(0, inventoryType.getTypicalSize(player.protocolVersion()));
    bottomContainer = new ItemContainer(
        inventoryType.getTypicalSize(player.protocolVersion()),
        4 * 9);
  }

  @Override
  public void registerActionHandler(@NonNull String name, @NonNull ActionHandler actionHandler) {
    actionHandlerMap.put(name, actionHandler);
  }

  @Override
  public ActionHandler actionHandler(@NonNull String name) {
    return actionHandlerMap.get(name);
  }

  @Override
  public void customActionHandler(@NonNull ActionHandler actionHandler) {
    this.customActionHandler = actionHandler;
  }

  @Override
  public void title(@NonNull String title) {
    this.title = title;
  }

  @Override
  public String title() {
    return Replacer.of(title).replaceAll((Object[]) replacements().get()).replacedMessageJoined();
  }

  @Override
  public void replacements(@NonNull Supplier<String[]> replacements) {
    this.replacements = replacements;
    updateReplacements();
  }

  protected void updateReplacements() {
  }

  protected void nativeInventory(@NonNull Object nativeInventory) {
    this.nativeInventory = nativeInventory;
  }

  @Override
  public void build() {
    if (menuBuilder() == null) {
      return;
    }
    nativeInventory(menuBuilder().build(nativeInventory(), this));
  }

  @Override
  public void open(@NonNull MenuBuilder menuBuilder) {
    this.menuBuilder = menuBuilder;
    build();
    menuBuilder().open(player, nativeInventory());
  }

  protected void set(@NonNull ItemStackModel model) {
    set(Localizer.localize(
        model,
        locale(),
        replacements().get()));
  }

  protected void set(@NonNull LocalizedItemStackModel model) {
    for (int slot : model.slots()) {
      if (slot > topContainer().capacity() - 1) {
        bottomContainer().set(slot - bottomContainer().baseSlot(), model);
      } else {
        topContainer().set(slot, model);
      }
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (this == o) {
      return true;
    }
    return o.equals(nativeInventory());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        actionHandlerMap,
        topContainer,
        bottomContainer,
        inventoryType,
        locale,
        player,
        internalId,
        replacements,
        customActionHandler,
        nativeInventory,
        title);
  }

  @Override
  public void handleException(
      @Nullable ActionHandler actionHandler, Throwable throwable) {
    if (actionHandler == null) {
      player.sendMessage(
          "§cThere was a problem while running your menu. Please take a look at the console.");
      log.error(
          "[Cirrus] Exception occurred while running menu " + getClass().getName(),
          throwable);
    } else {
      player.sendMessage(
          "§cThere was a problem while running your action handler. Please take a look at the console.");
      log.error("[Cirrus] Exception occurred while running action handler for menu "
                + getClass().getName(), throwable);
    }
  }

}
