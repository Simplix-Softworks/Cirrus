package dev.simplix.cirrus.common.menu;

import de.exceptionflug.protocolize.inventory.InventoryType;
import dev.simplix.cirrus.api.i18n.LocalizedItemStackModel;
import dev.simplix.cirrus.api.i18n.Localizer;
import dev.simplix.cirrus.api.model.ItemStackModel;
import dev.simplix.core.common.Replacer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.experimental.Accessors;
import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.menu.ActionHandler;
import dev.simplix.cirrus.api.menu.Container;
import dev.simplix.cirrus.api.menu.Menu;
import dev.simplix.cirrus.api.menu.MenuBuilder;

@Getter
@Accessors(fluent = true)
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
      PlayerWrapper player,
      InventoryType inventoryType,
      Locale locale) {
    this.inventoryType = inventoryType;
    this.locale = locale;
    this.player = player;
    replacements = () -> new String[] {"viewer", player.name()};
    topContainer = new ItemContainer(0, inventoryType.getTypicalSize(player.protocolVersion()));
    bottomContainer = new ItemContainer(inventoryType.getTypicalSize(player.protocolVersion()), 4 * 9);
  }

  @Override
  public void registerActionHandler(String name, ActionHandler actionHandler) {
    actionHandlerMap.put(name, actionHandler);
  }

  @Override
  public ActionHandler actionHandler(String name) {
    return actionHandlerMap.get(name);
  }

  @Override
  public void customActionHandler(ActionHandler actionHandler) {
    this.customActionHandler = actionHandler;
  }

  @Override
  public void title(String title) {
    this.title = Replacer.of(title).replaceAll((Object[]) replacements().get()).replacedMessageJoined();
  }

  public void replacements(Supplier<String[]> replacements) {
    this.replacements = replacements;
    updateReplacements();
  }

  protected void updateReplacements() {
  }

  protected void nativeInventory(Object nativeInventory) {
    this.nativeInventory = nativeInventory;
  }

  @Override
  public void build() {
    if(menuBuilder() == null)
      return;
    nativeInventory(menuBuilder().build(nativeInventory(), this));
  }

  @Override
  public void open(MenuBuilder menuBuilder) {
    this.menuBuilder = menuBuilder;
    build();
    menuBuilder().open(player, nativeInventory());
  }

  protected void set(ItemStackModel model) {
    LocalizedItemStackModel localizedItemStackModel = Localizer.localize(
        model,
        locale(),
        replacements().get());
    for (int slot : localizedItemStackModel.slots()) {
      if (slot > topContainer().capacity() - 1) {
        bottomContainer().set(slot - bottomContainer().baseSlot(), localizedItemStackModel);
      } else {
        topContainer().set(slot, localizedItemStackModel);
      }
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
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
}
