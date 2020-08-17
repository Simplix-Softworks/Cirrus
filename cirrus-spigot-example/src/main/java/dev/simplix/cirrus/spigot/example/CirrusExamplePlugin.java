package dev.simplix.cirrus.spigot.example;

import dev.simplix.core.common.aop.SimplixApplication;
import dev.simplix.core.common.inject.SimplixInstaller;
import dev.simplix.core.minecraft.spigot.dynamiclisteners.DynamicListenersSimplixModule;
import dev.simplix.minecraft.spigot.dynamiccommands.DynamicCommandsSimplixModule;
import org.bukkit.plugin.java.JavaPlugin;

@SimplixApplication(name = "CirrusExample", version = "1.0", authors = "Exceptionflug", dependencies = {
    "SimplixCore",
    "Cirrus"})
public class CirrusExamplePlugin extends JavaPlugin {

  @Override
  public void onEnable() {
    SimplixInstaller
        .instance()
        .register(CirrusExamplePlugin.class, new DynamicCommandsSimplixModule(),
            new DynamicListenersSimplixModule(this));
  }

}
