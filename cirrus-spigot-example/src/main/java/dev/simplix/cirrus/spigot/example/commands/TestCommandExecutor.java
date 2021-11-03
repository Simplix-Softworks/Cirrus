package dev.simplix.cirrus.spigot.example.commands;

import dev.simplix.cirrus.common.Cirrus;
import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.common.converter.Converters;
import dev.simplix.cirrus.common.configuration.impl.SimpleMultiPageMenuConfiguration;
import dev.simplix.cirrus.spigot.example.menus.ExampleMultiPageMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            new ExampleMultiPageMenu(
                    Converters.convert(player, PlayerWrapper.class),
                    Cirrus.configurationFactory().loadFile(
                            "plugins/Cirrus/example2.json", SimpleMultiPageMenuConfiguration.class)).open();
        }
        return false;
    }
}
