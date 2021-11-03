package dev.simplix.cirrus.common.prefabs.menu;

import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.common.configuration.MultiPageMenuConfiguration;
import dev.simplix.cirrus.common.handler.ActionHandler;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import lombok.NonNull;

public abstract class AbstractSelectorMenu<T> extends MultiPageMenu {
    public AbstractSelectorMenu(
            @NonNull PlayerWrapper player,
            @NonNull MultiPageMenuConfiguration configuration,
            @NonNull Locale locale
    ) {
        super(player, configuration, locale);
    }

    public AbstractSelectorMenu(
            @NonNull PlayerWrapper player,
            @NonNull MultiPageMenuConfiguration configuration,
            @NonNull Locale locale, @NonNull Map<String, ActionHandler> actionHandlerMap
    ) {
        super(player, configuration, locale, actionHandlerMap);
    }

    protected abstract List<T> values();


}
