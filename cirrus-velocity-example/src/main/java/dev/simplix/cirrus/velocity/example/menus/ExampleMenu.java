package dev.simplix.cirrus.velocity.example.menus;

import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.common.configuration.MenuConfiguration;
import dev.simplix.cirrus.common.menus.SimpleMenu;
import dev.simplix.cirrus.common.model.CallResult;

import java.util.Locale;

public class ExampleMenu extends SimpleMenu {

    public ExampleMenu(PlayerWrapper player, MenuConfiguration configuration) {
        super(player, configuration, Locale.ENGLISH);

        registerActionHandler("tnt", click -> {
            topContainer().itemMap().remove(click.slot());
            title("Hello, {viewer}");
            build();
            player().sendMessage("It simply works :)");
            return CallResult.DENY_GRABBING;
        });
    }

}
