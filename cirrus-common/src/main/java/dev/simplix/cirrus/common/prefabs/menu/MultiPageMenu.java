package dev.simplix.cirrus.common.prefabs.menu;

import dev.simplix.cirrus.api.business.InventoryItemWrapper;
import dev.simplix.cirrus.api.business.ItemStackWrapper;
import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.menu.*;
import dev.simplix.cirrus.api.model.MenuConfiguration;
import dev.simplix.cirrus.api.model.MultiPageMenuConfiguration;
import dev.simplix.cirrus.common.menu.AbstractConfigurableMenu;
import dev.simplix.cirrus.common.menu.AbstractMenu;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Accessors(fluent = true)
public class MultiPageMenu extends AbstractMenu {

  @Getter
  private final List<Menu> pages = new LinkedList<>();
  @Getter
  private final MultiPageMenuConfiguration configuration;
  private int currentPage = 1;
  private MenuBuilder menuBuilder;

  public MultiPageMenu(
      @NonNull PlayerWrapper player,
      @NonNull MultiPageMenuConfiguration configuration,
      @NonNull Locale locale) {
    super(player, configuration.type(), locale);
    this.configuration = configuration;
    title(configuration.title().translated(locale));
    pages.add(new PageMenu(player, configuration, locale));
    registerActionHandlers();
    configuration.nextPageItem().actionHandler("nextPage");
    configuration.previousPageItem().actionHandler("previousPage");
    Arrays
        .stream(configuration.nextPageItem().slots())
        .forEach(value -> topContainer().reservedSlots().add(value));
    Arrays
        .stream(configuration.previousPageItem().slots())
        .forEach(value -> topContainer().reservedSlots().add(value));
    replacements(() -> new String[]{
        "viewer", player.name(),
        "page", Integer.toString(currentPage),
        "pageCount", Integer.toString(pages.size())});
  }

  private void registerActionHandlers() {
    registerActionHandler("nextPage", click -> {
      if (currentPage == pages.size()) {
        return CallResult.DENY_GRABBING;
      }
      currentPage++;
      build();
      return CallResult.DENY_GRABBING;
    });
    registerActionHandler("previousPage", click -> {
      if (currentPage == 1) {
        return CallResult.DENY_GRABBING;
      }
      currentPage--;
      build();
      return CallResult.DENY_GRABBING;
    });
  }

  @Override
  public void build() {
    currentPage().build();
  }

  @Override
  public void open(MenuBuilder menuBuilder) {
    this.menuBuilder = menuBuilder;
    currentPage(1);
    currentPage().open(menuBuilder);
  }

  @Override
  public MenuBuilder menuBuilder() {
    return menuBuilder;
  }

  @Override
  public Container bottomContainer() {
    return currentPage().bottomContainer();
  }

  @Override
  public Container topContainer() {
    return currentPage().topContainer();
  }

  public Menu currentPage() {
    return pages.get(currentPage - 1);
  }

  public int currentPageNumber() {
    return currentPage;
  }

  public void currentPage(int page) {
    currentPage = page;
  }

  public void newPage() {
    pages.add(new PageMenu(player(), configuration(), locale()));
    currentPage++;
  }

  public void add(@NonNull InventoryItemWrapper inventoryItemWrapper) {
    int slot = currentPage().topContainer().nextFreeSlot();
    int oldPage = currentPage;
    if (slot == -1) {
      if (pages.size() > currentPage) {
        currentPage = pages.size();
        this.add(inventoryItemWrapper);
        currentPage(oldPage);
        return;
      }
      newPage();
      if (currentPage().topContainer().nextFreeSlot() == -1) {
        log.info("[Cirrus] Cannot add item to "
                 + MultiPageMenu.this.getClass().getSimpleName()
                 + ": No space in new page!");
        currentPage(oldPage);
        return;
      }
      this.add(inventoryItemWrapper);
    } else {
      currentPage().topContainer().set(slot, inventoryItemWrapper);
    }
    currentPage(oldPage);
  }

  public void add(
      @NonNull ItemStackWrapper itemStackWrapper,
      @NonNull String actionHandler,
      @NonNull List<String> actionArgs) {
    int slot = currentPage().topContainer().nextFreeSlot();
    int oldPage = currentPage;
    if (slot == -1) {
      if (pages.size() > currentPage) {
        currentPage = pages.size();
        this.add(itemStackWrapper, actionHandler, actionArgs);
        currentPage(oldPage);
        return;
      }
      newPage();
      if (currentPage().topContainer().nextFreeSlot() == -1) {
        log.info("[Cirrus] Cannot add item to "
                 + MultiPageMenu.this.getClass().getSimpleName()
                 + ": No space in new page!");
        currentPage(oldPage);
        return;
      }
      this.add(itemStackWrapper, actionHandler, actionArgs);
    } else {
      currentPage().topContainer().add(itemStackWrapper, actionHandler, actionArgs);
    }
    currentPage(oldPage);
  }

  class PageMenu extends AbstractConfigurableMenu<MenuConfiguration> {

    public PageMenu(
        @NonNull PlayerWrapper player,
        @NonNull MenuConfiguration configuration,
        @NonNull Locale locale) {
      super(player, configuration, locale);
    }

    @Override
    public Supplier<String[]> replacements() {
      return MultiPageMenu.this.replacements();
    }

    @Override
    public String title() {
      return MultiPageMenu.this.title();
    }

    @Override
    public ActionHandler actionHandler(@NonNull String name) {
      return MultiPageMenu.this.actionHandler(name);
    }

    @Override
    protected void nativeInventory(@NonNull Object nativeInventory) {
      MultiPageMenu.this.nativeInventory(nativeInventory);
    }

    @Override
    public MenuBuilder menuBuilder() {
      return MultiPageMenu.this.menuBuilder();
    }

    @Override
    public void build() {
      if (currentPage > 1) {
        set(MultiPageMenu.this.configuration().previousPageItem());
      }
      if (pages.size() > currentPage) {
        set(MultiPageMenu.this.configuration().nextPageItem());
      }
      if (menuBuilder() == null) {
        return;
      }
      nativeInventory(menuBuilder().build(nativeInventory(), MultiPageMenu.this));
    }

    @Override
    public Object nativeInventory() {
      return MultiPageMenu.this.nativeInventory();
    }

  }

}
