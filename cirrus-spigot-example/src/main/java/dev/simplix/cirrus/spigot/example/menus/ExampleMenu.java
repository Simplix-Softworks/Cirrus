package dev.simplix.cirrus.spigot.example.menus;

import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.common.menu.AbstractConfigurableMenu;
import dev.simplix.cirrus.common.model.CallResult;
import dev.simplix.cirrus.common.configuration.MenuConfiguration;
import java.util.Locale;

public class ExampleMenu extends AbstractConfigurableMenu {

    public ExampleMenu(PlayerWrapper player, MenuConfiguration configuration) {
        super(player, configuration, Locale.ENGLISH);

        registerActionHandler("tnt", click -> {
            topContainer().itemMap().remove(click.slot());
            title("Hello, {viewer}");
            build();
            player().sendMessage("It simply works :)");
            return CallResult.DENY_GRABBING;
        });

        set(configuration.businessItems().get("test"));
    }

}
