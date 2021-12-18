package dev.simplix.cirrus.bungeecord.example.commands;

import dev.simplix.cirrus.bungeecord.example.menus.ExampleMenu;
import dev.simplix.cirrus.common.Cirrus;
import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.common.converter.Converters;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class TestCommand extends Command {

    public TestCommand() {
        super("test");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer proxy = (ProxiedPlayer) sender;
            new ExampleMenu(Converters.convert(proxy, PlayerWrapper.class),
                    Cirrus.configurationFactory().loadFile("plugins/Cirrus/example.json")).open();
        }
    }

}
