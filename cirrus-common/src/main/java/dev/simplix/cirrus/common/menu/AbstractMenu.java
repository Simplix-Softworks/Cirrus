package dev.simplix.cirrus.common.menu;

import dev.simplix.cirrus.common.Cirrus;
import dev.simplix.cirrus.common.business.InventoryMenuItemWrapper;
import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.common.container.Container;
import dev.simplix.cirrus.common.container.impl.ItemContainer;
import dev.simplix.cirrus.common.handler.ActionHandler;
import dev.simplix.cirrus.common.handler.AutoCancellingActionHandler;
import dev.simplix.cirrus.common.i18n.LocalizedItemStackModel;
import dev.simplix.cirrus.common.i18n.Localizer;
import dev.simplix.cirrus.common.i18n.Replacer;
import dev.simplix.cirrus.common.item.MenuItem;
import dev.simplix.protocolize.data.inventory.InventoryType;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Getter
@Accessors(fluent = true)
@Slf4j
public abstract class AbstractMenu implements Menu {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger();

    private final Map<String, ActionHandler> actionHandlerMap;
    private final MenuBuilder menuBuilder = Cirrus.getService(MenuBuilder.class);
    private final Container topContainer;
    private final Container bottomContainer;
    private final InventoryType inventoryType;
    private final Locale locale;
    private final PlayerWrapper player;
    private final int internalId = ID_GENERATOR.incrementAndGet();
    private Supplier<String[]> replacements;
    private ActionHandler customActionHandler;
    private Object nativeInventory;
    private String title;

    public AbstractMenu(
            @NonNull PlayerWrapper player,
            @NonNull InventoryType inventoryType,
            @NonNull Locale locale) {
        this(player, inventoryType, locale, new HashMap<>());
    }

    public AbstractMenu(
            @NonNull PlayerWrapper player,
            @NonNull InventoryType inventoryType,
            @NonNull Locale locale,
            @NonNull Map<String, ActionHandler> actionHandlerMap) {
        this.inventoryType = inventoryType;
        this.locale = locale;
        this.player = player;
        this.replacements = () -> new String[]{"viewer", player.name()};
        this.topContainer = new ItemContainer(0, inventoryType.getTypicalSize(player.protocolVersion()));
        this.bottomContainer = new ItemContainer(
                inventoryType.getTypicalSize(player.protocolVersion()),
                4 * 9);
        this.actionHandlerMap = actionHandlerMap;
    }

    @Override
    public void registerActionHandler(@NonNull String name, @NonNull AutoCancellingActionHandler actionHandler) {
        this.actionHandlerMap.put(name, actionHandler);
    }

    @Override
    public void registerActionHandler(@NonNull String name, @NonNull ActionHandler actionHandler) {
        this.actionHandlerMap.put(name, actionHandler);
    }

    @Override
    @Nullable
    public ActionHandler actionHandler(@NonNull String name) {
        return this.actionHandlerMap.get(name);
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
        return Replacer.of(this.title).replaceAll((Object[]) replacements().get()).replacedMessageJoined();
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
    public void open() {
        build();
        menuBuilder().open(this.player, nativeInventory());
    }


    protected int add(@NonNull LocalizedItemStackModel localizedItemStackModel) {
        return add(localizedItemStackModel.localize(locale(), this.replacements.get()));
    }

    protected int add(@NonNull MenuItem menuItem) {
        int slot = topContainer().nextFreeSlot();
        this.topContainer.set(slot, menuItem);
        return slot;
    }

    protected int add(@NonNull InventoryMenuItemWrapper inventoryMenuItemWrapper) {
        int slot = topContainer().nextFreeSlot();
        this.topContainer.set(slot, inventoryMenuItemWrapper);
        return slot;

    }

    protected void set(@NonNull LocalizedItemStackModel model) {
        set(Localizer.localize(
                model,
                locale(),
                replacements().get()));
    }

    protected void set(@NonNull MenuItem model) {
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
        if (o instanceof AbstractMenu) {
            final AbstractMenu abstractMenu = (AbstractMenu) o;
            return abstractMenu.internalId == this.internalId();
        }

        if (this == o) {
            return true;
        }
        return o.equals(nativeInventory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.actionHandlerMap,
                this.topContainer,
                this.bottomContainer,
                this.inventoryType,
                this.locale,
                this.player,
                this.internalId,
                this.replacements,
                this.customActionHandler,
                this.nativeInventory,
                this.title);
    }

    @Override
    public void handleException(
            @Nullable ActionHandler actionHandler, Throwable throwable) {
        if (actionHandler == null) {
            this.player.sendMessage(
                    "§cThere was a problem while running your menu. Please take a look at the console.");
            log.error(
                    "[Cirrus] Exception occurred while running menu " + getClass().getName(),
                    throwable);
        } else {
            this.player.sendMessage(
                    "§cThere was a problem while running your action handler. Please take a look at the console.");
            log.error("[Cirrus] Exception occurred while running action handler for menu "
                    + getClass().getName(), throwable);
        }
    }

}
