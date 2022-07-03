package dev.simplix.cirrus.velocity.example.menus;

import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.common.configuration.MultiPageMenuConfiguration;
import dev.simplix.cirrus.common.menus.MultiPageMenu;
import dev.simplix.cirrus.common.model.CallResult;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.item.ItemStack;
import dev.simplix.protocolize.api.providers.MappingProvider;
import dev.simplix.protocolize.data.ItemType;

import java.util.Collections;
import java.util.Locale;

public class ExampleMultiPageMenu extends MultiPageMenu {

    private final MappingProvider mappingProvider = Protocolize.mappingProvider();

    public ExampleMultiPageMenu(
            PlayerWrapper player,
            MultiPageMenuConfiguration configuration) {
        super(player, configuration, Locale.ENGLISH);
        registerMyActionHandlers();
        addItems();
    }

    private void registerMyActionHandlers() {
        registerActionHandler("test", click -> {
            player().sendMessage("This is " + click.clickedItem().type().name());
            return CallResult.DENY_GRABBING;
        });
    }

    private void addItems() {
        for (ItemType type : ItemType.values()) {
            if (this.mappingProvider.mapping(type, player().protocolVersion()) != null) {
                add(wrapItemStack(new ItemStack(type)), "test", Collections.emptyList());
            }
        }
    }

}
