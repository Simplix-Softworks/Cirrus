package dev.simplix.cirrus.spigot.util;

import de.exceptionflug.protocolize.api.util.ProtocolVersions;
import dev.simplix.core.minecraft.spigot.util.ReflectionUtil;
import java.lang.reflect.Field;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ProtocolVersionUtil {

  private ProtocolVersionUtil() {
  }

  private static int protocolVersion;

  public static int serverProtocolVersion() {
    if (protocolVersion == 0) {
      protocolVersion = detectVersion();
    }
    return protocolVersion;
  }

  private static int detectVersion() {
    String majorVersion = ReflectionUtil
        .serverVersion()
        .substring(1, ReflectionUtil.serverVersion().indexOf('_', 3));
    try {
      Field field = ProtocolVersions.class.getField("MINECRAFT_"+majorVersion);
      return field.getInt(null);
    } catch (NoSuchFieldException e) {
      throw new RuntimeException("Cirrus is not compatible with server version "+ReflectionUtil.serverVersion());
    } catch (IllegalAccessException e) {
      log.error("[Cirrus] Unable to detect protocol version", e);
      return ProtocolVersions.MINECRAFT_1_8;
    }
  }

}
