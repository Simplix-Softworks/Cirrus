package dev.simplix.cirrus.common.menus;

import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.common.configuration.MultiPageMenuConfiguration;
import dev.simplix.cirrus.common.handler.ActionHandler;
import dev.simplix.cirrus.common.i18n.LocalizedItemStackModel;
import dev.simplix.cirrus.common.model.Click;
import java.util.*;
import java.util.Map.Entry;
import lombok.NonNull;

public abstract class AbstractSelectorMenu<T> extends MultiPageMenu {
    final Map<Integer, T> slotValueMap = new HashMap<>();

    public AbstractSelectorMenu(
            @NonNull PlayerWrapper player,
            @NonNull MultiPageMenuConfiguration configuration,
            @NonNull Locale locale) {
        super(player, configuration, locale);
    }

    public AbstractSelectorMenu(
            @NonNull PlayerWrapper player,
            @NonNull MultiPageMenuConfiguration configuration,
            @NonNull Locale locale, @NonNull Map<String, ActionHandler> actionHandlerMap) {
        super(player, configuration, locale, actionHandlerMap);

        registerActionHandler("click", click -> {
            final T value = this.slotValueMap.get(click.slot());
            if (value==null) {
                return;
            }
            click(click, value);
        });
    }

    protected abstract void click(Click click, T value);

    protected abstract List<T> values();

    protected abstract LocalizedItemStackModel map(T value);

    protected Map<T, LocalizedItemStackModel> mappedValues() {
        final Map<T, LocalizedItemStackModel> out = new HashMap<>();
        for (T value : values()) {
            out.put(value, map(value));
        }

        return out;
    }

    protected void insert() {
        for (Entry<T, LocalizedItemStackModel> entry : mappedValues().entrySet()) {
            final Integer slot = add(wrapItemStack(entry.getValue()), "click", new ArrayList<>());
            this.slotValueMap.put(slot, entry.getKey());
        }
    }


}
