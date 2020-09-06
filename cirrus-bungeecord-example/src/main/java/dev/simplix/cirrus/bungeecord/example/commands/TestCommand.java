package dev.simplix.cirrus.bungeecord.example.commands;

import com.google.inject.Inject;
import de.exceptionflug.protocolize.items.ItemType;
import dev.simplix.cirrus.api.business.ConfigurationFactory;
import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.menu.MenuBuilder;
import dev.simplix.cirrus.api.model.MultiPageMenuConfiguration;
import dev.simplix.cirrus.bungeecord.example.menus.ExampleMultiPageMenu;
import dev.simplix.core.common.aop.Component;
import dev.simplix.core.common.converter.Converters;
import dev.simplix.core.minecraft.bungeecord.dynamiccommands.DynamicCommandsSimplixModule;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

@Component(DynamicCommandsSimplixModule.class)
public class TestCommand extends Command {

  private final MenuBuilder menuBuilder;
  private final ConfigurationFactory configurationFactory;

  @Inject
  public TestCommand(MenuBuilder menuBuilder, ConfigurationFactory configurationFactory) {
    super("test");
    this.menuBuilder = menuBuilder;
    this.configurationFactory = configurationFactory;
  }

  public void execute(CommandSender sender, String[] args) {
    if (sender instanceof ProxiedPlayer) {
      ProxiedPlayer p = (ProxiedPlayer) sender;
      new ExampleMultiPageMenu(
          Converters.convert(p, PlayerWrapper.class),
          configurationFactory.loadFile(
              "plugins/Cirrus/example.json",
              MultiPageMenuConfiguration.class)).open(menuBuilder);
    }
    https://github.com/Exceptionflug/protocolize/releases/download/v1.6.3/protocolize-plugin.jar
  }

}
