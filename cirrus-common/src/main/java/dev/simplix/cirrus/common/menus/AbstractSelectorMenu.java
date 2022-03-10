package dev.simplix.cirrus.common.menus;

import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.common.configuration.MultiPageMenuConfiguration;
import dev.simplix.cirrus.common.handler.ActionHandler;
import dev.simplix.cirrus.common.item.MenuItem;
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

    protected abstract MenuItem map(T value);

    protected Map<T, MenuItem> mappedValues() {
        final Map<T, MenuItem> out = new HashMap<>();
        if (this.out != null && !this.out.isEmpty()) {
            return this.out;
        }
        this.out = new HashMap<>();
        for (T value : values()) {
            this.out.put(value, map(value));
        }

        return this.out;
    }

    protected void insert() {
        for (Entry<T, MenuItem> entry : mappedValues().entrySet()) {
            final MenuItem value = entry.getValue();
            final Integer slot = add(value, "click", new ArrayList<>());
            this.slotValueMap.put(slot, entry.getKey());
        }
    }


}
