package dev.simplix.cirrus.velocity.example.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import dev.simplix.cirrus.common.Cirrus;
import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.common.converter.Converters;
import dev.simplix.cirrus.velocity.example.menus.ExampleMenu;

public class TestCommand implements SimpleCommand {

    @Override
    public void execute(Invocation invocation) {
        if (invocation.source() instanceof Player) {
            Player player = (Player) invocation.source();
            new ExampleMenu(Converters.convert(player, PlayerWrapper.class),
                    Cirrus.configurationFactory().loadFile("plugins/Cirrus/example.json")).open();
        }
    }
}
