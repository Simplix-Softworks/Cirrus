package dev.simplix.cirrus.common.menu;

import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.common.handler.ActionHandler;
import dev.simplix.cirrus.common.i18n.LocalizedItemStackModel;
import dev.simplix.cirrus.common.configuration.MenuConfiguration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public abstract class AbstractConfigurableMenu<T extends MenuConfiguration> extends AbstractMenu {

    private final T configuration;

    public AbstractConfigurableMenu(
            @NonNull PlayerWrapper player,
            @NonNull T configuration,
            @NonNull Locale locale) {
        this(player, configuration, locale, new HashMap<>());
    }

    public AbstractConfigurableMenu(
            @NonNull PlayerWrapper player,
            @NonNull T configuration,
            @NonNull Locale locale,
            @NonNull Map<String, ActionHandler> actionHandlerMap) {
        super(player, configuration.type(), locale, actionHandlerMap);
        title(configuration.title().translated(locale));
        this.configuration = configuration;
        applyItems();
    }

    @Override
    protected void updateReplacements() {
        super.updateReplacements();
        topContainer().itemMap().clear();
        bottomContainer().itemMap().clear();
        topContainer().reservedSlots().clear();
        bottomContainer().reservedSlots().clear();
        applyItems();
    }

    private void applyItems() {
        for (int slot : this.configuration.reservedSlots()) {
            if (slot > topContainer().capacity() - 1) {
                bottomContainer().reservedSlots().add(slot);
            } else {
                topContainer().reservedSlots().add(slot);
            }
        }

        // Set placeholders
        set(this.configuration.placeholderItem());

        // Set other items
        for (LocalizedItemStackModel model : this.configuration.items()) {
            set(model);
        }
    }

}
