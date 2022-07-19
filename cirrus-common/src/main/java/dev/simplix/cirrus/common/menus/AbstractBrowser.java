package dev.simplix.cirrus.common.menus;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import dev.simplix.cirrus.common.business.PlayerWrapper;
import dev.simplix.cirrus.common.configuration.MultiPageMenuConfiguration;
import dev.simplix.cirrus.common.handler.ActionHandler;
import dev.simplix.cirrus.common.item.CirrusItem;
import dev.simplix.cirrus.common.model.Click;
import lombok.NonNull;

import java.util.*;
import java.util.Map.Entry;

public abstract class AbstractBrowser<T> extends MultiPageMenu {

    protected final Map<Integer, T> slotValueMap = new HashMap<>();

    protected final BiMap<T, CirrusItem> out = HashBiMap.create();

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
            final String identifier = this.topContainer().get(click.slot()).actionArguments().get(0);
            final T value = this.slotValueMap.get(Integer.parseInt(identifier));
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
        if (!this.out.isEmpty()) {
            return this.out;
        }

        for (T value : values()) {
            this.out.put(value, map(value));
        }

        return this.out;
    }

    protected void insert() {
        for (Entry<T, CirrusItem> entry : mappedValues().entrySet()) {
            final CirrusItem value = entry.getValue();
            final int identifier = entry.getKey().hashCode();
            add(value, "click", new ArrayList<>(Collections.singletonList(identifier + "")));
            this.slotValueMap.put(identifier, entry.getKey());
        }
    }


}
