package dev.simplix.cirrus.spigot.menubuilder;

import dev.simplix.cirrus.common.business.InventoryMenuItemWrapper;
import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.common.container.Container;
import dev.simplix.cirrus.common.converter.Converters;
import dev.simplix.cirrus.common.menu.Menu;
import dev.simplix.cirrus.common.menu.MenuBuilder;
import dev.simplix.cirrus.common.menus.MultiPageMenu;
import dev.simplix.cirrus.spigot.util.ProtocolVersionUtil;
import dev.simplix.cirrus.spigot.util.ReflectionUtil;
import dev.simplix.protocolize.api.util.ProtocolVersions;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.Map.Entry;

@Slf4j
public final class SpigotMenuBuilder implements MenuBuilder {

    private static Constructor<?> modernInventoryViewConstructor;

    static {
        try {
            if (ProtocolVersionUtil.serverProtocolVersion() > ProtocolVersions.MINECRAFT_1_13) {
                Class<?> modernInventoryViewClass = SpigotMenuBuilder.class.getClassLoader()
                        .loadClass("dev.simplix.cirrus.spigot.modern.ModernInventoryView");
                modernInventoryViewConstructor =
                        modernInventoryViewClass.getConstructor(Menu.class, Inventory.class, Inventory.class);
            }
        } catch (ReflectiveOperationException exception) {
            log.error("SpigotMenuBuilder won't work", exception);
        }
    }

    private final Map<UUID, Map.Entry<Menu, Long>> buildMap = new LinkedHashMap<>();
    private final List<Menu> menus = new LinkedList<>();

    @Override
    public <T> T build(@Nullable T prebuild, @NonNull Menu menu) {
        boolean reopen = false;
        boolean register = prebuild == null;
        if (prebuild instanceof InventoryView) {
            InventoryView inventoryView = (InventoryView) prebuild;
            if (!inventoryView.getTitle().equals(menu.title())
                    || inventoryView.getTopInventory().getSize() != menu.inventoryType()
                    .getTypicalSize(ProtocolVersionUtil.serverProtocolVersion())
                    || inventoryView.getTopInventory().getType() != Converters
                    .convert(menu.inventoryType(), org.bukkit.event.inventory.InventoryType.class)) {
                prebuild = (T) makeView(menu);
                reopen = true;
            }
        } else {
            prebuild = (T) makeView(menu);
        }
        InventoryView view = (InventoryView) prebuild;
        buildContainer(view.getTopInventory(), menu.topContainer(), false);
//    buildContainer(view.getBottomInventory(), menu.bottomContainer(), true);
        this.buildMap.put(
                menu.player().uniqueId(),
                new AbstractMap.SimpleEntry<>(menu, System.currentTimeMillis()));
        if (register) {
            this.menus.add(menu);
        }
        if (reopen || menu instanceof MultiPageMenu) {
            open(menu.player(), prebuild);
        }
        return prebuild;
    }

    private void buildContainer(Inventory inventory, Container container, boolean bottom) {
        for (int i = 0; i < container.capacity(); i++) {
            InventoryMenuItemWrapper item = container.itemMap().get(i + container.baseSlot());
            ItemStack currentStack = inventory.getItem(i);
            if (item == null) {
                if (currentStack != null) {
                    if (bottom) {
                        inventory.setItem(
                                container.baseSlot() + container.capacity() - 1 - (
                                        i
                                                + container.baseSlot()),
                                null);
                    } else {
                        inventory.setItem(i, null);
                    }
                }
            }
            if (item != null) {
                ItemStack bukkitItemStack = Converters.convert(item.handle(), ItemStack.class);
                if (currentStack == null) {
                    if (item.handle() == null) {
                        Bukkit.getLogger()
                                .severe("InventoryItem's ItemStackWrapper is null @ slot " + i);
                        continue;
                    }
                    if (bottom) {
                        inventory.setItem(
                                container.baseSlot() + container.capacity() - 1 - (
                                        i
                                                + container.baseSlot()),
                                bukkitItemStack);
                    } else {
                        inventory.setItem(i, bukkitItemStack);
                    }
                } else {
                    if (!currentStack.equals(bukkitItemStack)) {
                        if (bottom) {
                            inventory.setItem(
                                    container.baseSlot() + container.capacity() - 1 - (
                                            i
                                                    + container.baseSlot()),
                                    bukkitItemStack);
                        } else {
                            inventory.setItem(i, bukkitItemStack);
                        }
                    }
                }
            }
        }
    }

    private static boolean isChest(dev.simplix.protocolize.data.inventory.InventoryType type) {
        if (type == dev.simplix.protocolize.data.inventory.InventoryType.GENERIC_3X3) {
            return false;
        } else {
            return type.name().startsWith("GENERIC") || type.name().contains("CHEST");
        }
    }

    private InventoryView makeView(Menu menu) {
        Inventory top;
        if (isChest(menu.inventoryType())) {
            top = Bukkit.createInventory(
                    menu.player().handle(),
                    menu.inventoryType().getTypicalSize(ProtocolVersionUtil.serverProtocolVersion()),
                    menu.title());
        } else {
            top = Bukkit.createInventory(
                    menu.player().handle(),
                    Converters
                            .convert(menu.inventoryType(), org.bukkit.event.inventory.InventoryType.class),
                    menu.title());
        }
        Inventory bottom;
        if (menu.bottomContainer().itemMap().isEmpty()) {
            bottom = ((Player) menu.player().handle()).getInventory();
        } else {
            bottom = createPlayerInventory(menu.player().handle());
        }
        if (ProtocolVersionUtil.serverProtocolVersion() > ProtocolVersions.MINECRAFT_1_13) {
            return modernView(menu, top, bottom);
        } else {
            return legacyView(menu, top, bottom);
        }
    }

    private InventoryView modernView(Menu menu, Inventory top, Inventory bottom) {
        // We need to construct this class using reflection since referencing this class in code
        // would cause a VerifyError while loading the class on non-modern spigot versions.
        try {
            return (InventoryView) modernInventoryViewConstructor
                    .newInstance(menu, top, bottom);
        } catch (Exception e) {
            log.error("Unable to construct ModernInventoryView", e);
        }
        return null;
    }

    private InventoryView legacyView(Menu menu, Inventory top, Inventory bottom) {
        return new InventoryView() {

            private final Inventory topInventory = top;
            private final Inventory bottomInventory = bottom;

            @Override
            public Inventory getTopInventory() {
                return this.topInventory;
            }

            @Override
            public Inventory getBottomInventory() {
                return this.bottomInventory;
            }

            @Override
            public HumanEntity getPlayer() {
                return menu.player().handle();
            }

            @Override
            public InventoryType getType() {
                return Converters.convert(
                        menu.inventoryType(),
                        org.bukkit.event.inventory.InventoryType.class);
            }
        };
    }

    private Inventory createPlayerInventory(Player player) {
        try {
            Class<?> craftPlayerInventoryClass =
                    ReflectionUtil.getClass("{obc}.inventory.CraftInventoryPlayer");
            Class<?> nmsPlayerInventoryClass =
                    ReflectionUtil.getClass("{nms}.PlayerInventory");
            Class<?> nmsEntityHumanClass =
                    ReflectionUtil.getClass("{nms}.EntityHuman");
            Constructor<?> nmsPlayerInventoryConstructor =
                    nmsPlayerInventoryClass.getConstructor(nmsEntityHumanClass);
            Object nmsPlayerInventory = nmsPlayerInventoryConstructor.newInstance(
                    nmsEntityHumanClass.cast(ReflectionUtil.nmsPlayer(player)));
            Constructor<?> craftPlayerInventoryConstructor =
                    craftPlayerInventoryClass.getConstructor(nmsPlayerInventoryClass);
            return (Inventory) craftPlayerInventoryConstructor.newInstance(nmsPlayerInventory);
        } catch (ReflectiveOperationException exception) {
            log.error("Cannot create player inventory", exception);
        }
        return null;
    }

    @Override
    public <T> void open(@NonNull PlayerWrapper playerWrapper, @NonNull T inventoryImpl) {
        ((Player) playerWrapper.handle()).openInventory((InventoryView) inventoryImpl);
    }

    @Override
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
                wrapper -> ((Player) wrapper.player().handle()).getUniqueId().equals(uniqueId));
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
