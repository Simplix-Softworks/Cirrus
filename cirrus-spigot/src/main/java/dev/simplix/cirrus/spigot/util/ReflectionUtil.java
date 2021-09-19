package dev.simplix.cirrus.spigot.util;

import com.mojang.authlib.GameProfile;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReflectionUtil {

    private static final String EXCEPTION_OCCURRED = "Exception occurred";
    private static final String GET_HANDLE = "getHandle";
    private static final String PLAYER_CONNECTION = "playerConnection";
    private static final String SEND_PACKET = "sendPacket";
    private static final String NMS_PACKET = "{nms}.Packet";

    private static final Map<Map.Entry<Class<?>, String>, Field> CACHED_FIELDS = new HashMap<>();
    private static final Map<String, Class<?>> CACHED_CLASSES = new HashMap<>();

    public static Class<?> getClass(String classname) throws ClassNotFoundException {
        String path = classname
                .replace(
                        "{nm}",
                        "net.minecraft" + (
                                Bukkit.getBukkitVersion().startsWith("1.17")
                                        ? ""
                                        : "." + serverVersion()))
                .replace(
                        "{nms}",
                        "net.minecraft.server" + (
                                Bukkit.getBukkitVersion().startsWith("1.17")
                                        ? ""
                                        : "." + serverVersion()))
                .replace("{obc}", "org.bukkit.craftbukkit." + serverVersion());
        Class<?> out = CACHED_CLASSES.get(path);

        if (out == null) {
            out = Class.forName(path);
            CACHED_CLASSES.put(path, out);
        }
        return out;
    }

    public static String serverVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public static GameProfile gameProfile(@NonNull Player player)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object obcPlayer = obcPlayer(player);
        Class<?> obcPlayerClass = obcPlayer.getClass();
        return (GameProfile) obcPlayerClass.getMethod("getProfile").invoke(player);
    }

    public static Object nmsPlayer(@NonNull Player player)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method getHandle = player.getClass().getMethod(GET_HANDLE);
        return getHandle.invoke(player);
    }

    public static Object obcPlayer(@NonNull Player player)
            throws ClassNotFoundException {
        return getClass("{obc}.entity.CraftPlayer").cast(player);
    }

    public static Object nmsWorld(@NonNull World world)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method getHandle = world.getClass().getMethod(GET_HANDLE);
        return getHandle.invoke(world);
    }

    public static Object nmsScoreboard(@NonNull Scoreboard scoreboard)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method getHandle = scoreboard.getClass().getMethod(GET_HANDLE);
        return getHandle.invoke(scoreboard);
    }

    public static Object fieldValue(@NonNull Object instance, @NonNull String fieldName)
            throws IllegalAccessException {
        final Map.Entry<Class<?>, String> key = new AbstractMap.SimpleEntry<>(
                instance.getClass(),
                fieldName);
        final Field field = CACHED_FIELDS.computeIfAbsent(key, i -> {
            try {
                return instance.getClass().getDeclaredField(fieldName);
            } catch (final NoSuchFieldException e) {
                log.error(EXCEPTION_OCCURRED, e);
            }
            return null;
        });
        if (field == null) {
            return null;
        }
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        return field.get(instance);
    }

    public static <T> T fieldValue(@NonNull Field field, @NonNull Object obj) {
        try {
            return (T) field.get(obj);
        } catch (Exception exception) {
            log.error(EXCEPTION_OCCURRED, exception);
            return null;
        }
    }

    public static Field field(@NonNull Class<?> clazz, @NonNull String fieldName)
            throws NoSuchFieldException {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }

    public static void value(Object instance, String field, Object value) {
        try {
            Field f = instance.getClass().getDeclaredField(field);
            f.setAccessible(true);
            f.set(instance, value);
        } catch (Exception exception) {
            log.error(EXCEPTION_OCCURRED, exception);
        }
    }

    public static void value(
            @NonNull Class<?> clazz,
            @NonNull Object instance,
            @NonNull String field,
            @NonNull Object value) {
        try {
            Field declaredField = clazz.getDeclaredField(field);
            declaredField.setAccessible(true);
            declaredField.set(instance, value);
        } catch (Exception e) {
            log.error(EXCEPTION_OCCURRED, e);
        }
    }

    public static void valueSubclass(
            @NonNull Class<?> clazz,
            @NonNull Object instance,
            @NonNull String field,
            @NonNull Object value) {
        try {
            Field declaredField = clazz.getDeclaredField(field);
            declaredField.setAccessible(true);
            declaredField.set(instance, value);
        } catch (Exception e) {
            log.error(EXCEPTION_OCCURRED, e);
        }
    }

    public static void sendAllPacket(@NonNull Object packet) throws ReflectiveOperationException {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Object nmsPlayer = nmsPlayer(p);
            Object connection = nmsPlayer.getClass().getField(PLAYER_CONNECTION).get(nmsPlayer);
            connection
                    .getClass()
                    .getMethod(SEND_PACKET, ReflectionUtil.getClass(NMS_PACKET))
                    .invoke(connection, packet);
        }
    }

    public static void sendListPacket(@NonNull List<String> players, @NonNull Object packet) {
        try {
            for (String name : players) {
                Object nmsPlayer = nmsPlayer(Bukkit.getPlayer(name));
                Object connection = nmsPlayer.getClass().getField(PLAYER_CONNECTION).get(nmsPlayer);
                connection
                        .getClass()
                        .getMethod(SEND_PACKET, ReflectionUtil.getClass(NMS_PACKET))
                        .invoke(connection, packet);
            }
        } catch (Exception exception) {
            log.error(EXCEPTION_OCCURRED, exception);
        }
    }

    public static void sendPlayerPacket(@NonNull Player player, @NonNull Object packet)
            throws ReflectiveOperationException {
        Object nmsPlayer = nmsPlayer(player);
        Object connection = nmsPlayer.getClass().getField(PLAYER_CONNECTION).get(nmsPlayer);
        connection
                .getClass()
                .getMethod(SEND_PACKET, ReflectionUtil.getClass(NMS_PACKET))
                .invoke(connection, packet);
    }

    public static void listFields(@NonNull Object object) {
        log.info(object.getClass().getName() + " contains " + object
                .getClass()
                .getDeclaredFields().length + " declared fields.");
        log.info(object.getClass().getName() + " contains " + object
                .getClass()
                .getDeclaredClasses().length + " declared classes.");
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                log.info(field.getName() + " -> " + field.get(object));
            } catch (IllegalArgumentException | IllegalAccessException exception) {
                log.error(EXCEPTION_OCCURRED, exception);
            }
        }
    }

    public static Object fieldValue(
            @NonNull Class<?> superclass,
            @NonNull Object instance,
            @NonNull String fieldName)
            throws IllegalAccessException, NoSuchFieldException {
        Field field = superclass.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(instance);
    }
}
