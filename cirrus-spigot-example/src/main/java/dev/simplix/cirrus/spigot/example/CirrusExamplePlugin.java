package dev.simplix.cirrus.spigot.example;

import dev.simplix.cirrus.spigot.CirrusSpigot;
import dev.simplix.cirrus.spigot.example.commands.TestCommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class CirrusExamplePlugin extends JavaPlugin {

  @Override
  public void onEnable() {
    CirrusSpigot.init(this);
    getCommand("test").setExecutor(new TestCommandExecutor());
  }

}
