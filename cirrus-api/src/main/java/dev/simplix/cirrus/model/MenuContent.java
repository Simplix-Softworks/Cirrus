package dev.simplix.cirrus.model;

import dev.simplix.protocolize.api.item.BaseItemStack;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.*;
import javax.annotation.Nullable;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * The MenuContent class is a concurrent map implementation that is used to store items in a menu.
 * It extends the {@link ConcurrentMap} interface and delegates its methods to an internal
 * {@link Map} instance. It has a default no-args constructor and a constructor that takes a map of
 * items as an argument, which it uses to initialize its internal Map instance. It has additional
 * convenience methods, such as {@link #size()} and {@link #isEmpty()}, that provide information
 * about the contents of the map.
 */
@NoArgsConstructor //Constructs a new, empty MenuContent instance
public class MenuContent implements ConcurrentMap<Integer, BaseItemStack> {
  private final Map<Integer, BaseItemStack> value = new ConcurrentHashMap<>();

  /**
   * Constructs a new MenuContent with the specified items.
   *
   * @param items The items to add to the menu.
   */
  public MenuContent(Map<Integer, BaseItemStack> items) {
    this.value.putAll(items);
  }

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
  public BaseItemStack get(@NonNull Object key) {
    return this.value.get(key);
  }

  @Nullable
  @Override
  public BaseItemStack put(@NonNull Integer key, @NonNull BaseItemStack value) {
    return this.value.put(key, value);
  }

  @Override
  public BaseItemStack remove(Object key) {
    return this.value.remove(key);
  }

  @Override
  public void putAll(@NonNull Map<? extends Integer, ? extends BaseItemStack> m) {
    this.value.putAll(m);
  }

  @Override
  public void clear() {
    this.value.clear();
  }

  @NonNull
  @Override
  public Set<Integer> keySet() {
    return this.value.keySet();
  }

  @NonNull
  @Override
  public Collection<BaseItemStack> values() {
    return this.value.values();
  }

  @NonNull
  @Override
  public Set<Entry<Integer, BaseItemStack>> entrySet() {
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
  public BaseItemStack getOrDefault(Object key, BaseItemStack defaultValue) {
    return this.value.getOrDefault(key, defaultValue);
  }

  @Override
  public void forEach(BiConsumer<? super Integer, ? super BaseItemStack> action) {
    this.value.forEach(action);
  }

  @Override
  public void replaceAll(BiFunction<? super Integer, ? super BaseItemStack, ? extends BaseItemStack> function) {
    this.value.replaceAll(function);
  }

  @Override
  public BaseItemStack putIfAbsent(Integer key, BaseItemStack value) {
    return this.value.putIfAbsent(key, value);
  }

  @Override
  public boolean remove(Object key, Object value) {
    return this.value.remove(key, value);
  }

  @Override
  public boolean replace(
      Integer key,
      BaseItemStack oldValue,
      BaseItemStack newValue) {
    return this.value.replace(key, oldValue, newValue);
  }

  @Override
  public BaseItemStack replace(Integer key, BaseItemStack value) {
    return this.value.replace(key, value);
  }

  @Override
  public BaseItemStack computeIfAbsent(
      Integer key,
      @NonNull Function<? super Integer, ? extends BaseItemStack> mappingFunction) {
    return this.value.computeIfAbsent(key, mappingFunction);
  }

  @Override
  public BaseItemStack computeIfPresent(
      Integer key,
      @NonNull BiFunction<? super Integer, ? super BaseItemStack, ? extends BaseItemStack> remappingFunction) {
    return this.value.computeIfPresent(key, remappingFunction);
  }

  @Override
  public BaseItemStack compute(
      Integer key,
      @NonNull BiFunction<? super Integer, ? super BaseItemStack, ? extends BaseItemStack> remappingFunction) {
    return this.value.compute(key, remappingFunction);
  }

  @Override
  public BaseItemStack merge(
      Integer key,
      @NonNull BaseItemStack value,
      @NonNull BiFunction<? super BaseItemStack, ? super BaseItemStack, ? extends BaseItemStack> remappingFunction) {
    return this.value.merge(key, value, remappingFunction);
  }

  @Override
  public String toString() {
    return Objects.toString(this.value);
  }

  public MenuContent copy() {
    return new MenuContent(this);
  }
}
