package dev.simplix.cirrus.spigot;

import dev.simplix.cirrus.spigot.CirrusLibrary.ModuleSupplier;
import dev.simplix.core.common.aop.AbstractSimplixModule;
import dev.simplix.core.common.aop.RequireModules;
import dev.simplix.core.common.aop.ScanComponents;
import dev.simplix.core.common.aop.SimplixApplication;
import dev.simplix.core.minecraft.spigot.dynamiclisteners.DynamicListenersSimplixModule;
import java.util.function.Supplier;
import org.bukkit.Bukkit;

@SimplixApplication(name = "Cirrus", version = "1.0", authors = "SimplixSoftworks", dependencies = "SimplixCore")
@ScanComponents("dev.simplix.cirrus")
@RequireModules(ModuleSupplier.class)
public class CirrusLibrary {

  public static class ModuleSupplier implements Supplier<AbstractSimplixModule[]> {

    @Override
    public AbstractSimplixModule[] get() {
      return new AbstractSimplixModule[]{
          new DynamicListenersSimplixModule(Bukkit
              .getPluginManager()
              .getPlugin("SimplixCore"))};
    }

  }

}
