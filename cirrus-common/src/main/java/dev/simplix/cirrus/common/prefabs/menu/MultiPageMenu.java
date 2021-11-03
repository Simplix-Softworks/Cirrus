package dev.simplix.cirrus.common.prefabs.menu;

import dev.simplix.cirrus.common.Cirrus;
import dev.simplix.cirrus.common.business.InventoryItemWrapper;
import dev.simplix.cirrus.common.business.ItemStackWrapper;
import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.common.configuration.MenuConfiguration;
import dev.simplix.cirrus.common.configuration.MultiPageMenuConfiguration;
import dev.simplix.cirrus.common.handler.ActionHandler;
import dev.simplix.cirrus.common.menu.*;
import dev.simplix.cirrus.common.model.CallResult;
import java.util.*;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Accessors(fluent = true)
public class MultiPageMenu extends AbstractMenu {

    private final MenuBuilder menuBuilder = Cirrus.getService(MenuBuilder.class);
    @Getter
    private final List<Menu> pages = new LinkedList<>();
    @Getter
    private final MultiPageMenuConfiguration configuration;
    private int currentPage = 1;

    public MultiPageMenu(
            @NonNull PlayerWrapper player,
            @NonNull MultiPageMenuConfiguration configuration,
            @NonNull Locale locale) {
        this(player, configuration, locale, new HashMap<>());
    }

    public MultiPageMenu(
            @NonNull PlayerWrapper player,
            @NonNull MultiPageMenuConfiguration configuration,
            @NonNull Locale locale,
            @NonNull Map<String, ActionHandler> actionHandlerMap) {
        super(player, configuration.type(), locale, actionHandlerMap);
        this.configuration = configuration;
        title(configuration.title().translated(locale));
        this.pages.add(new PageMenu(player, configuration, locale));
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
                "page", Integer.toString(this.currentPage),
                "pageCount", Integer.toString(this.pages.size())});
    }

    private void registerActionHandlers() {
        registerActionHandler("nextPage", click -> {
            if (this.currentPage==this.pages.size()) {
                return CallResult.DENY_GRABBING;
            }
            this.currentPage++;
            build();
            return CallResult.DENY_GRABBING;
        });
        registerActionHandler("previousPage", click -> {
            if (this.currentPage==1) {
                return CallResult.DENY_GRABBING;
            }
            this.currentPage--;
            build();
            return CallResult.DENY_GRABBING;
        });
    }

    @Override
    public void build() {
        currentPage().build();
    }

    @Override
    public void open() {
        currentPage(1);
        currentPage().open();
    }

    @Override
    public MenuBuilder menuBuilder() {
        return this.menuBuilder;
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
        return this.pages.get(this.currentPage - 1);
    }

    public List<Menu> pages() {
        return this.pages;
    }

    public int currentPageNumber() {
        return this.currentPage;
    }

    public void currentPage(int page) {
        this.currentPage = page;
    }

    public void newPage() {
        this.pages.add(new PageMenu(player(), configuration(), locale()));
        this.currentPage++;
    }

    public void add(@NonNull InventoryItemWrapper inventoryItemWrapper) {
        int slot = currentPage().topContainer().nextFreeSlot();
        int oldPage = this.currentPage;
        if (slot==-1) {
            if (this.pages.size() > this.currentPage) {
                this.currentPage = this.pages.size();
                this.add(inventoryItemWrapper);
                currentPage(oldPage);
                return;
            }
            newPage();
            if (currentPage().topContainer().nextFreeSlot()==-1) {
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
        int oldPage = this.currentPage;
        if (slot==-1) {
            if (this.pages.size() > this.currentPage) {
                this.currentPage = this.pages.size();
                this.add(itemStackWrapper, actionHandler, actionArgs);
                currentPage(oldPage);
                return;
            }
            newPage();
            if (currentPage().topContainer().nextFreeSlot()==-1) {
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
            if (MultiPageMenu.this.currentPage > 1) {
                set(MultiPageMenu.this.configuration().previousPageItem());
            }
            if (MultiPageMenu.this.pages.size() > MultiPageMenu.this.currentPage) {
                set(MultiPageMenu.this.configuration().nextPageItem());
            }
            if (menuBuilder()==null) {
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
