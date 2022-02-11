package dev.simplix.cirrus.bungeecord.protocolize;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import dev.simplix.cirrus.bungeecord.BungeeCordPlayerWrapper;
import dev.simplix.cirrus.common.Utils;
import dev.simplix.cirrus.common.business.InventoryMenuItemWrapper;
import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.common.container.Container;
import dev.simplix.cirrus.common.handler.ActionHandler;
import dev.simplix.cirrus.common.i18n.Replacer;
import dev.simplix.cirrus.common.menu.AbstractMenu;
import dev.simplix.cirrus.common.menu.Menu;
import dev.simplix.cirrus.common.menu.MenuBuilder;
import dev.simplix.cirrus.common.model.CallResult;
import dev.simplix.cirrus.common.model.Click;
import dev.simplix.protocolize.api.ClickType;
import dev.simplix.protocolize.api.inventory.Inventory;
import dev.simplix.protocolize.api.item.ItemStack;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import dev.simplix.protocolize.data.packets.OpenWindow;
import dev.simplix.protocolize.data.packets.WindowItems;
import lombok.NonNull;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.chat.ComponentSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.Map.Entry;

public class ProtocolizeMenuBuilder implements MenuBuilder {

    private final Map<UUID, Map.Entry<Menu, Long>> buildMap = new LinkedHashMap<>();
    private final Set<Menu> menus = Sets.newConcurrentHashSet();

    @Override
    public <T> T build(@Nullable T prebuild, @NonNull Menu menu) {
        final Entry<Menu, Long> menuLongEntry = this.buildMap.get(menu.player().uniqueId());
        if (menuLongEntry != null && !menuLongEntry.getKey().equals(menu)) {
            menuLongEntry.getKey().handleClose(true);
        }
        if (!(menu instanceof AbstractMenu)) {
            throw new IllegalArgumentException("This implementation can only build cirrus menus!");
        }
        final boolean register = prebuild == null;
        if (prebuild instanceof Inventory) {
            String title = Replacer.of(menu.title()).replaceAll((Object[]) menu.replacements().get())
                    .replacedMessageJoined();
            final Inventory inventory = (Inventory) prebuild;
            final BaseComponent[] component = TextComponent.fromLegacyText(title);
            for (BaseComponent baseComponent : component) {
                baseComponent.setItalic(false); // Wir kommen nicht aus Italien
            }
            if (!ComponentSerializer.toString((BaseComponent[]) inventory.title())
                    .equals(ComponentSerializer.toString(component)) ||
                    inventory.type().getTypicalSize(menu.player().protocolVersion()) != menu
                            .topContainer()
                            .capacity()
                    || inventory.type() != menu.inventoryType()) {
                prebuild = (T) makeInv(menu);
            }
        } else {
            prebuild = (T) makeInv(menu);
        }
        final Inventory inventory = (Inventory) prebuild;
        buildContainer(inventory, menu.topContainer());
        buildContainer(inventory, menu.bottomContainer());

        this.buildMap.put(
                menu.player().uniqueId(),
                new AbstractMap.SimpleEntry<>(menu, System.currentTimeMillis()));
        if (register) {
            this.menus.add(menu);
        }
        open(menu.player(), inventory);
        return prebuild;
    }

    private void buildContainer(@NonNull Inventory inventory, @NonNull Container container) {
        for (int i = container.baseSlot(); i < container.baseSlot() + container.capacity(); i++) {
            InventoryMenuItemWrapper item = container.itemMap().get(i);
            ItemStack currentStack = inventory.item(i);
            if (item == null) {
                if (currentStack != null) {
                    inventory.item(i, ItemStack.NO_DATA);
                }
            }

            if (item != null) {
                for (BaseComponent[] loreComponent : item.loreComponents()) {
                    for (BaseComponent baseComponent : loreComponent) {
//                        if (!baseComponent.isItalic()) {
//                            baseComponent.setItalic(false);
//                        }
                    }
                }

                if (currentStack == null) {
                    if (item.handle() == null) {
                        ProxyServer.getInstance().getLogger()
                                .severe("InventoryItem's ItemStackWrapper is null @ slot " + i);
                        continue;
                    }
                    inventory.item(i, item.handle());
                } else {
                    if (!currentStack.equals(item.handle())) {
                        inventory.item(i, item.handle());
                    }
                }
            }
        }
    }

    private Inventory makeInv(@NonNull Menu menu) {
        Inventory inventory = new Inventory(menu.inventoryType());
        BaseComponent[] textComponent = TextComponent.fromLegacyText(Replacer.of(menu.title())
                .replaceAll((Object[]) menu.replacements().get()).replacedMessageJoined());
        for (BaseComponent component : textComponent) {
            if (!component.isItalic()) {
                component.setItalic(false); // Resolve client side behavior
            }
        }
        inventory.title(textComponent);

        inventory.onClose(inventoryClose -> {
            Map.Entry<Menu, Long> lastBuild = lastBuildOfPlayer(inventoryClose.player().uniqueId());
            if (((AbstractMenu) lastBuild.getKey()).internalId() == ((AbstractMenu) menu).internalId()
                    && (System.currentTimeMillis() - lastBuild.getValue()) <= 55) {
                return;
            }
            menu.handleClose((System.currentTimeMillis() - lastBuild.getValue()) <= 55);
            invalidate(menu);
        });

        inventory.onClick(inventoryClick -> {
            if (inventoryClick.clickType() == null) {
                return;
            }
            if (inventoryClick.player() == null) {
                return;
            }
            Inventory i = inventoryClick.inventory();
            if (i == null) {
                return;
            }
//            ProxyServer.getInstance().broadcast("Clicked inventory");
//            ProxyServer.getInstance().broadcast("Clicked menu: " + menu.getClass().getSimpleName() + " @ slot " + inventoryClick.slot());
            Container container;
            if (inventoryClick.slot() > menu.topContainer().capacity() - 1) {
                container = menu.bottomContainer();
//                ProxyServer.getInstance().broadcast("Clicked bottom container");
            } else {
                container = menu.topContainer();
//                ProxyServer.getInstance().broadcast("Clicked top container");
            }
            InventoryMenuItemWrapper item = container.get(inventoryClick.slot());
            ClickType type = inventoryClick.clickType();
            if (item == null) {
//                ProxyServer.getInstance().broadcast("Clicked nothing");
                if (menu.customActionHandler() != null) {
                    try {
                        CallResult callResult = menu
                                .customActionHandler()
                                .handle(new Click(type, menu, null, inventoryClick.slot()));
                        inventoryClick.cancelled(callResult == null || callResult == CallResult.DENY_GRABBING);
                    } catch (Exception ex) {
                        inventoryClick.cancelled(true);
                        menu.handleException(null, ex);
                    }
                }
                return;
            }
//            ProxyServer.getInstance().broadcast("Clicked " + item.displayName());
            ActionHandler actionHandler = menu.actionHandler(item.actionHandler());
            if (actionHandler == null) {
                inventoryClick.cancelled(true);
                return;
            }
            try {
                final CallResult callResult = actionHandler.handle(new Click(
                        type,
                        menu,
                        item,
                        inventoryClick.slot()));
                inventoryClick.cancelled(callResult == null || callResult == CallResult.DENY_GRABBING);
            } catch (final Exception ex) {
                inventoryClick.cancelled(true);
                menu.handleException(actionHandler, ex);
            }
        });

        return inventory;
    }

    @Override
    public <T> void open(
            PlayerWrapper playerWrapper, T inventoryImpl) {
        if (playerWrapper instanceof BungeeCordPlayerWrapper) {
            final ProtocolizePlayer protocolizePlayer = ((BungeeCordPlayerWrapper) playerWrapper).protocolizePlayer();
            final Inventory inventory = (Inventory) inventoryImpl;

            boolean alreadyOpen = false;
            int windowId = -1;
            for (Integer id : protocolizePlayer.registeredInventories().keySet()) {
                Inventory val = protocolizePlayer.registeredInventories().get(id);
                if (val.type() == inventory.type() && val.title().equals(inventory.title())) {
                    alreadyOpen = true;
                    break;
                }
            }

            // Close all inventories if not opened
            if (!alreadyOpen) {
                protocolizePlayer.closeInventory();
            }

            if (protocolizePlayer.registeredInventories().containsValue(inventory)) {
                for (Integer id : protocolizePlayer.registeredInventories().keySet()) {
                    Inventory val = protocolizePlayer.registeredInventories().get(id);
                    if (val==inventory) {
                        windowId = id;
                        break;
                    }
                }
                if (windowId == -1) {
                    windowId = protocolizePlayer.generateWindowId();
                    protocolizePlayer.registerInventory(windowId, inventory);
                }
            } else {
                windowId = protocolizePlayer.generateWindowId();
                protocolizePlayer.registerInventory(windowId, inventory);
            }

            if (!alreadyOpen) {
                protocolizePlayer.sendPacket(new OpenWindow(windowId, inventory.type(), inventory.titleJson()));
            }
            int protocolVersion;
            try {
                protocolVersion = protocolizePlayer.protocolVersion();
            } catch (Throwable t) {
                protocolVersion = 47;
            }
            List<ItemStack> items = Lists.newArrayList(inventory.itemsIndexed(protocolVersion));
            for (ItemStack item : items) {
                if (item==null) {
                    continue;
                }
                List<BaseComponent[]> lore = item.lore();
                for (BaseComponent[] baseComponents : lore) {
                    Utils.removeItalic(baseComponents);
                }
                item.lore(lore, false);
                BaseComponent[] display = item.displayName();
                Utils.removeItalic(display);
                item.displayName(display);
            }
            protocolizePlayer.sendPacket(new WindowItems((short) windowId, items, 0));
        }
    }

    private void removeItalic(BaseComponent[] components) {
        for (BaseComponent component : components) {
            component.setItalic(false);
        }
    }

    @Override
    @Nullable
    public Menu menuByHandle(Object handle) {
        if (handle == null) {
            return null;
        }
        for (final Menu menu : this.menus) {
            if (menu.equals(handle)) {
                return menu;
            }
        }
        return null;
    }

    @Override
    public void destroyMenusOfPlayer(@NonNull UUID uniqueId) {
        this.menus.removeIf(
                wrapper -> ((ProxiedPlayer) wrapper.player().handle()).getUniqueId().equals(uniqueId));
        this.buildMap.remove(uniqueId);
    }

    @Override
    public Entry<Menu, Long> lastBuildOfPlayer(@NonNull UUID uniqueId) {
        return this.buildMap.get(uniqueId);
    }

    @Override
    public void invalidate(@NonNull Menu menu) {
        this.menus.remove(menu);
    }

}
