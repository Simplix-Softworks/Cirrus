package dev.simplix.cirrus.spigot.util;

import dev.simplix.protocolize.api.util.ProtocolVersions;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
@UtilityClass
public final class ProtocolVersionUtil {

  private int protocolVersion;
  private String versionString;

  /**
   * Returns the protocol-version int of the server. E.G: 755 for 1.17.1
   *
   * @return the protocol-version int.
   */
  public int serverProtocolVersion() {
    if (protocolVersion == 0) {
      protocolVersion = detectVersion();
    }
    return protocolVersion;

  }

  /**
   * Returns the major server version E.G: "1.18.1"
   *
   * @return the major server version.
   */
  public String versionString() {
    if (versionString != null) {
      return versionString;
    }

    return versionString = ReflectionUtil
        .serverVersion()
        .substring(1, ReflectionUtil.serverVersion().indexOf('_', 3));
  }

  private int detectVersion() {
    String majorVersion = ReflectionUtil
        .serverVersion()
        .substring(1, ReflectionUtil.serverVersion().indexOf('_', 3));
    try {
      Field field = ProtocolVersions.class.getField("MINECRAFT_" + majorVersion);
      return field.getInt(null);
    } catch (IllegalAccessException exception) {
      log.error("Could not access field MINECRAFT_" + majorVersion, exception);
    } catch (NoSuchFieldException exception) {
      log.warn("[Cirrus] Cirrus is not compatible with this version ", exception);
      log.warn("[Cirrus] [Compatibility Mode] Proceeding as if in Minecraft 1.18");
    }
    return ProtocolVersions.MINECRAFT_1_18_1;
  }

}
