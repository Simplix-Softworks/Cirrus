package dev.simplix.cirrus.common.menus;

import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.common.configuration.MultiPageMenuConfiguration;
import dev.simplix.cirrus.common.handler.ActionHandler;
import dev.simplix.cirrus.common.item.CirrusItem;
import dev.simplix.cirrus.common.model.Click;
import lombok.NonNull;

import java.util.*;
import java.util.Map.Entry;

public abstract class AbstractBrowser<T> extends MultiPageMenu {

    protected Map<Integer, T> slotValueMap = new HashMap<>();

    protected Map<T, CirrusItem> out = new HashMap<>();

    public AbstractBrowser(
            @NonNull PlayerWrapper player,
            @NonNull MultiPageMenuConfiguration configuration,
            @NonNull Locale locale) {
        this(player, configuration, locale, new HashMap<>());
    }

    public AbstractBrowser(
            @NonNull PlayerWrapper player,
            @NonNull MultiPageMenuConfiguration configuration,
            @NonNull Locale locale, @NonNull Map<String, ActionHandler> actionHandlerMap) {
        super(player, configuration, locale, actionHandlerMap);

        registerActionHandler("click", click -> {
            final T value = this.slotValueMap.get(click.slot());
            if (value == null) {
                return;
            }
            click(click, value);
        });
    }

    protected abstract void click(Click click, T value);

    protected abstract List<T> values();

    protected abstract CirrusItem map(T value);

    protected Map<T, CirrusItem> mappedValues() {
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
        for (Entry<T, CirrusItem> entry : mappedValues().entrySet()) {
            final CirrusItem value = entry.getValue();
            final Integer slot = add(value, "click", new ArrayList<>());
            this.slotValueMap.put(slot, entry.getKey());
        }
    }


}
