package dev.simplix.cirrus.common.util;

import dev.simplix.cirrus.common.business.InventoryMenuItemWrapper;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InventoryContentMap implements ConcurrentMap<Integer, InventoryMenuItemWrapper> {

    private final Map<Integer, InventoryMenuItemWrapper> value = new ConcurrentHashMap<>();

    // Delegate methods

    @Override
    public int size() {
        return this.value.size();
    }

    @Override
    public boolean isEmpty() {
        return this.value.isEmpty();
    }

    @Override
    public boolean containsKey(@NonNull Object key) {
        return this.value.containsKey(key);
    }

    @Override
    public boolean containsValue(@NonNull Object value) {
        return this.value.containsValue(value);
    }

    @Override
    public InventoryMenuItemWrapper get(@NonNull Object key) {
        return this.value.get(key);
    }

    @Nullable
    @Override
    public InventoryMenuItemWrapper put(@NonNull Integer key, @NonNull InventoryMenuItemWrapper value) {
        return this.value.put(key, value);
    }

    @Override
    public InventoryMenuItemWrapper remove(Object key) {
        return this.value.remove(key);
    }

    @Override
    public void putAll(@NotNull Map<? extends Integer, ? extends InventoryMenuItemWrapper> m) {
        this.value.putAll(m);
    }

    @Override
    public void clear() {
        this.value.clear();
    }

    @NotNull
    @Override
    public Set<Integer> keySet() {
        return this.value.keySet();
    }

    @NotNull
    @Override
    public Collection<InventoryMenuItemWrapper> values() {
        return this.value.values();
    }

    @NotNull
    @Override
    public Set<Entry<Integer, InventoryMenuItemWrapper>> entrySet() {
        return this.value.entrySet();
    }

    @Override
    public boolean equals(Object obj) {
        return this.value.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public InventoryMenuItemWrapper getOrDefault(Object key, InventoryMenuItemWrapper defaultValue) {
        return this.value.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super Integer, ? super InventoryMenuItemWrapper> action) {
        this.value.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super Integer, ? super InventoryMenuItemWrapper, ? extends InventoryMenuItemWrapper> function) {
        this.value.replaceAll(function);
    }

    @Override
    public InventoryMenuItemWrapper putIfAbsent(Integer key, InventoryMenuItemWrapper value) {
        return this.value.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return this.value.remove(key, value);
    }

    @Override
    public boolean replace(
            Integer key,
            InventoryMenuItemWrapper oldValue,
            InventoryMenuItemWrapper newValue) {
        return this.value.replace(key, oldValue, newValue);
    }

    @Override
    public InventoryMenuItemWrapper replace(Integer key, InventoryMenuItemWrapper value) {
        return this.value.replace(key, value);
    }

    @Override
    public InventoryMenuItemWrapper computeIfAbsent(
            Integer key,
            @NotNull Function<? super Integer, ? extends InventoryMenuItemWrapper> mappingFunction) {
        return this.value.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public InventoryMenuItemWrapper computeIfPresent(
            Integer key,
            @NotNull BiFunction<? super Integer, ? super InventoryMenuItemWrapper, ? extends InventoryMenuItemWrapper> remappingFunction) {
        return this.value.computeIfPresent(key, remappingFunction);
    }

    @Override
    public InventoryMenuItemWrapper compute(
            Integer key,
            @NotNull BiFunction<? super Integer, ? super InventoryMenuItemWrapper, ? extends InventoryMenuItemWrapper> remappingFunction) {
        return this.value.compute(key, remappingFunction);
    }

    @Override
    public InventoryMenuItemWrapper merge(
            Integer key,
            @NotNull InventoryMenuItemWrapper value,
            @NotNull BiFunction<? super InventoryMenuItemWrapper, ? super InventoryMenuItemWrapper, ? extends InventoryMenuItemWrapper> remappingFunction) {
        return this.value.merge(key, value, remappingFunction);
    }
}
