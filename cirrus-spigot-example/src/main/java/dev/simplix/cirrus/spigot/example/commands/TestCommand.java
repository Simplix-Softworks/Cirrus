package dev.simplix.cirrus.spigot.example.commands;

import com.google.inject.Inject;
import dev.simplix.cirrus.api.business.ConfigurationFactory;
import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.menu.MenuBuilder;
import dev.simplix.cirrus.api.model.MultiPageMenuConfiguration;
import dev.simplix.cirrus.spigot.example.menus.ExampleMultiPageMenu;
import dev.simplix.core.common.aop.Component;
import dev.simplix.core.common.converter.Converters;
import dev.simplix.minecraft.spigot.dynamiccommands.DynamicCommandsSimplixModule;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Component(DynamicCommandsSimplixModule.class)
public class TestCommand extends Command {

  private final MenuBuilder menuBuilder;
  private final ConfigurationFactory configurationFactory;

  @Inject
  public TestCommand(
      @NonNull MenuBuilder menuBuilder,
      @NonNull  ConfigurationFactory configurationFactory) {
    super("test2");
    this.menuBuilder = menuBuilder;
    this.configurationFactory = configurationFactory;
  }

  @Override
  public boolean execute(
      CommandSender sender, String commandLabel, String[] strings) {
    if (sender instanceof Player) {
      Player player = (Player) sender;
      new ExampleMultiPageMenu(
          Converters.convert(player, PlayerWrapper.class),
          configurationFactory.loadFile(
              "plugins/Cirrus/example2.json", MultiPageMenuConfiguration.class)).open(menuBuilder);
    }
    return false;
  }


}
