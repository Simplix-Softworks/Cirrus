package dev.simplix.cirrus.bungeecord.example;

import dev.simplix.core.common.aop.SimplixApplication;
import dev.simplix.core.common.inject.SimplixInstaller;
import dev.simplix.core.minecraft.bungeecord.dynamiccommands.DynamicCommandsSimplixModule;
import dev.simplix.core.minecraft.bungeecord.dynamiclisteners.DynamicListenersSimplixModule;
import net.md_5.bungee.api.plugin.Plugin;

@SimplixApplication(name = "CirrusExample", version = "1.0", authors = "Exceptionflug", dependencies = {"SimplixCore", "Cirrus"})
public class CirrusExamplePlugin extends Plugin {

  @Override
  public void onEnable() {
    SimplixInstaller
        .instance()
        .register(CirrusExamplePlugin.class, new DynamicCommandsSimplixModule(this),
            new DynamicListenersSimplixModule(this));
  }

}
