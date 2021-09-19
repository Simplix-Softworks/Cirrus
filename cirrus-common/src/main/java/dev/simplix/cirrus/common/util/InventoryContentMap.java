package dev.simplix.cirrus.common.util;

import dev.simplix.cirrus.api.business.InventoryItemWrapper;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class InventoryContentMap implements ConcurrentMap<Integer, InventoryItemWrapper> {

    private Map<Integer, InventoryItemWrapper> value = new ConcurrentHashMap<>();

    // Delegate methods

    @Override
    public int size() {
        return value.size();
    }

    @Override
    public boolean isEmpty() {
        return value.isEmpty();
    }

    @Override
    public boolean containsKey(@NonNull Object key) {
        return value.containsKey(key);
    }

    @Override
    public boolean containsValue(@NonNull Object value) {
        return this.value.containsValue(value);
    }

    @Override
    public InventoryItemWrapper get(@NonNull Object key) {
        return value.get(key);
    }

    @Nullable
    @Override
    public InventoryItemWrapper put(@NonNull Integer key, @NonNull InventoryItemWrapper value) {
        return this.value.put(key, value);
    }

    @Override
    public InventoryItemWrapper remove(Object key) {
        return value.remove(key);
    }

    @Override
    public void putAll(@NotNull Map<? extends Integer, ? extends InventoryItemWrapper> m) {
        value.putAll(m);
    }

    @Override
    public void clear() {
        value.clear();
    }

    @NotNull
    @Override
    public Set<Integer> keySet() {
        return value.keySet();
    }

    @NotNull
    @Override
    public Collection<InventoryItemWrapper> values() {
        return value.values();
    }

    @NotNull
    @Override
    public Set<Entry<Integer, InventoryItemWrapper>> entrySet() {
        return value.entrySet();
    }

    @Override
    public boolean equals(Object obj) {
        return value.equals(obj);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public InventoryItemWrapper getOrDefault(Object key, InventoryItemWrapper defaultValue) {
        return value.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super Integer, ? super InventoryItemWrapper> action) {
        value.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super Integer, ? super InventoryItemWrapper, ? extends InventoryItemWrapper> function) {
        value.replaceAll(function);
    }

    @Override
    public InventoryItemWrapper putIfAbsent(Integer key, InventoryItemWrapper value) {
        return this.value.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return this.value.remove(key, value);
    }

    @Override
    public boolean replace(
            Integer key,
            InventoryItemWrapper oldValue,
            InventoryItemWrapper newValue) {
        return value.replace(key, oldValue, newValue);
    }

    @Override
    public InventoryItemWrapper replace(Integer key, InventoryItemWrapper value) {
        return this.value.replace(key, value);
    }

    @Override
    public InventoryItemWrapper computeIfAbsent(
            Integer key,
            @NotNull Function<? super Integer, ? extends InventoryItemWrapper> mappingFunction) {
        return value.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public InventoryItemWrapper computeIfPresent(
            Integer key,
            @NotNull BiFunction<? super Integer, ? super InventoryItemWrapper, ? extends InventoryItemWrapper> remappingFunction) {
        return value.computeIfPresent(key, remappingFunction);
    }

    @Override
    public InventoryItemWrapper compute(
            Integer key,
            @NotNull BiFunction<? super Integer, ? super InventoryItemWrapper, ? extends InventoryItemWrapper> remappingFunction) {
        return value.compute(key, remappingFunction);
    }

    @Override
    public InventoryItemWrapper merge(
            Integer key,
            @NotNull InventoryItemWrapper value,
            @NotNull BiFunction<? super InventoryItemWrapper, ? super InventoryItemWrapper, ? extends InventoryItemWrapper> remappingFunction) {
        return this.value.merge(key, value, remappingFunction);
    }
}
