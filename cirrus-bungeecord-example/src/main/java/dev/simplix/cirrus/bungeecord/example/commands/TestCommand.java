package dev.simplix.cirrus.bungeecord.example.commands;

import dev.simplix.cirrus.api.business.PlayerWrapper;
import dev.simplix.cirrus.api.converter.Converters;
import dev.simplix.cirrus.bungeecord.example.menus.ExampleMenu;
import dev.simplix.cirrus.common.Cirrus;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class TestCommand extends Command {

    public TestCommand() {
        super("test");
    }

    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
//      new ExampleMultiPageMenu(
//          Converters.convert(p, PlayerWrapper.class),
//          configurationFactory.loadFile(
//              "plugins/Cirrus/example.json",
//              MultiPageMenuConfiguration.class)).open(menuBuilder);
            new ExampleMenu(Converters.convert(p, PlayerWrapper.class),
                    Cirrus.configurationFactory().loadFile("plugins/Cirrus/example.json")).open();
        }
    }

}
