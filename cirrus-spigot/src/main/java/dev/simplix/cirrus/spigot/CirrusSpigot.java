package dev.simplix.cirrus.spigot;

import dev.simplix.cirrus.api.business.InventoryItemWrapper;
import dev.simplix.cirrus.api.business.ItemStackWrapper;
import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.converter.Converters;
import dev.simplix.cirrus.api.i18n.LocalizedItemStackModel;
import dev.simplix.cirrus.api.menu.MenuBuilder;
import dev.simplix.cirrus.common.Cirrus;
import dev.simplix.cirrus.common.item.ProtocolizeItemStackWrapper;
import dev.simplix.cirrus.spigot.converters.*;
import dev.simplix.cirrus.spigot.listener.InventoryListener;
import dev.simplix.cirrus.spigot.menubuilder.SpigotMenuBuilder;
import dev.simplix.cirrus.spigot.util.BungeeCordComponentConverterProvider;
import dev.simplix.cirrus.spigot.util.OtherModuleProvider;
import dev.simplix.cirrus.spigot.util.ReflectionClasses;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.item.ItemStack;
import dev.simplix.protocolize.api.providers.ComponentConverterProvider;
import dev.simplix.protocolize.api.providers.ModuleProvider;
import dev.simplix.protocolize.data.DataModule;
import dev.simplix.protocolize.data.ItemType;
import dev.simplix.protocolize.data.inventory.InventoryType;
import lombok.extern.slf4j.Slf4j;
import net.querz.nbt.tag.CompoundTag;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

/**
 * Date: 18.09.2021
 *
 * @author Exceptionflug
 */
@Slf4j
public class CirrusSpigot {

    private static JavaPlugin plugin;

    public static void init(JavaPlugin plugin) {
        if (CirrusSpigot.plugin != null) {
            return;
        }
        CirrusSpigot.plugin = plugin;
        Cirrus.registerService(MenuBuilder.class, new SpigotMenuBuilder());
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), plugin);
        Protocolize.registerService(ComponentConverterProvider.class, new BungeeCordComponentConverterProvider());
        Protocolize.registerService(ModuleProvider.class, new OtherModuleProvider());
        registerConverters();
    }

    private static void registerConverters() {
        try {
            // Players
            Converters.register(
                    Player.class,
                    PlayerWrapper.class,
                    o -> new SpigotPlayerWrapper((Player) o));
            Converters.register(UUID.class, PlayerWrapper.class, o -> Bukkit.getPlayer((UUID) o));

            // Items
            Converters.register( // Protocolize ----> Bukkit
                    ItemStack.class,
                    org.bukkit.inventory.ItemStack.class,
                    new ProtocolizeItemStackConverter());
            Converters.register( // Bukkit ----> Protocolize
                    org.bukkit.inventory.ItemStack.class,
                    ItemStack.class,
                    new BukkitItemStackConverter());
            Converters.register(
                    ItemStack.class,
                    ItemStackWrapper.class,
                    o -> new ProtocolizeItemStackWrapper(
                            (dev.simplix.protocolize.api.item.ItemStack) o));
            Converters.register(ItemType.class, MaterialData.class, new ItemTypeMaterialDataConverter());
            Converters.register(MaterialData.class, ItemType.class, new MaterialDataItemTypeConverter());
            Converters.register(
                    LocalizedItemStackModel.class,
                    InventoryItemWrapper.class,
                    new ItemModelConverter());
            Converters.register(
                    InventoryType.class,
                    org.bukkit.event.inventory.InventoryType.class,
                    new SpigotInventoryTypeConverter());

            Converters.register(
                    ClickType.class,
                    dev.simplix.protocolize.api.ClickType.class,
                    new SpigotClickTypeConverter());

            // NBT
            Converters.register( // Querz ----> NMS
                    CompoundTag.class,
                    ReflectionClasses.nbtTagCompound(),
                    new QuerzNbtNmsNbtConverter());
            Converters.register( // NMS ----> Querz
                    ReflectionClasses.nbtTagCompound(),
                    CompoundTag.class,
                    new NmsNbtQuerzNbtConverter());
        } catch (Exception exception) {
            log.error("Cannot register cirrus converters", exception);
        }
    }

    public static JavaPlugin plugin() {
        if (plugin == null) {
            throw new IllegalStateException("Cirrus is not initialized. Please call CirrusSpigot#init during onEnable.");
        }
        return plugin;
    }

}
