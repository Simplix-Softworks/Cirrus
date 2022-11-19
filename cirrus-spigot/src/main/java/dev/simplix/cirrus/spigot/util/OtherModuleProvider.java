package dev.simplix.cirrus.spigot.util;

import dev.simplix.protocolize.api.Platform;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.module.ProtocolizeModule;
import dev.simplix.protocolize.api.providers.ModuleProvider;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Date: 23.08.2021
 *
 * @author Exceptionflug
 */
@Slf4j(topic = "Protocolize")
@Getter
@Accessors(fluent = true)
public final class OtherModuleProvider implements ModuleProvider {

  private final List<ProtocolizeModule> modules = new ArrayList<>();

  @Override
  public void registerModule(ProtocolizeModule module) {
    if (!supportedPlatform(module.supportedPlatforms())) {
      log.warn("Won't register module "
               + module.getClass().getName()
               + ": Only supports "
               + Arrays.toString(module.supportedPlatforms()));
      return;
    }
    modules.add(module);
    enableModule(module);
  }

  @Override
  public boolean moduleInstalled(String s) {
    return this.modules
        .stream()
        .map(module -> module.getClass().getSimpleName())
        .collect(Collectors.toList())
        .contains(s);
  }

  @Override
  public ProtocolizeModule module(String s) {
    return this.modules
        .stream()
        .filter(module -> module.getClass().getSimpleName().equalsIgnoreCase(s))
        .findFirst()
        .orElse(null);
  }

  private boolean supportedPlatform(Platform[] supportedPlatforms) {
    for (Platform platform : supportedPlatforms) {
      if (Protocolize.platform() == platform) {
        return true;
      }
    }
    return false;
  }

  private void enableModule(ProtocolizeModule module) {
    module.registerMappings(Protocolize.mappingProvider());
    module.registerPackets(Protocolize.protocolRegistration());
    log.info("Enabled module " + module.getClass().getName());
  }

}
