package dev.simplix.cirrus.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.simplix.cirrus.common.business.ConfigurationFactory;
import dev.simplix.cirrus.common.business.InventoryMenuItemWrapper;
import dev.simplix.cirrus.common.business.MenuItemWrapper;
import dev.simplix.cirrus.common.config.JsonConfigurationFactory;
import dev.simplix.cirrus.common.converter.Converter;
import dev.simplix.cirrus.common.converter.Converters;
import dev.simplix.cirrus.common.i18n.*;
import dev.simplix.cirrus.common.mojangson.TagDeserializer;
import dev.simplix.cirrus.common.mojangson.TagSerializer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.querz.nbt.tag.CompoundTag;

public class Cirrus {

    private static final Map<Class<?>, Object> SERVICES = new ConcurrentHashMap<>();

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(CompoundTag.class, new TagSerializer())
            .registerTypeAdapter(CompoundTag.class, new TagDeserializer())
            .registerTypeAdapter(LocalizedString.class, new LocalizedStringSerializer())
            .registerTypeAdapter(LocalizedString.class, new LocalizedStringDeserializer())
            .registerTypeAdapter(LocalizedStringList.class, new LocalizedStringListSerializer())
            .registerTypeAdapter(LocalizedStringList.class, new LocalizedStringListDeserializer())
            .setPrettyPrinting()
            .create();

    static {
        Converters.register(
                InventoryMenuItemWrapper.class, MenuItemWrapper.class,
                (Converter<InventoryMenuItemWrapper, MenuItemWrapper>) InventoryMenuItemWrapper::wrapper);
        registerService(ConfigurationFactory.class, new JsonConfigurationFactory(GSON));
    }

    /**
     * This returns an instance of a registered service.
     *
     * @param type The registration type of the desired service
     * @param <T>  The type of the service
     * @return The instance of T or null if there is no service with this registration type
     * @see Cirrus#registerService(Class, Object)
     */
    public static <T> T getService(Class<T> type) {
        return (T) SERVICES.get(type);
    }

    /**
     * This will map a given service instance to a given registration type.
     *
     * @param type     The registration type of the desired service
     * @param instance An instance of the registration type
     * @param <T>      The registration type
     */
    public static <T> void registerService(Class<T> type, T instance) {
        SERVICES.put(type, instance);
    }

    public static ConfigurationFactory configurationFactory() {
        return getService(ConfigurationFactory.class);
    }

}
