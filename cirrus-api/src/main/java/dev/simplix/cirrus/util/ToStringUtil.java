package dev.simplix.cirrus.util;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nullable;

/**
 * Copied from Guava because BungeeCord relies on a very old version of it,
 * and we don't want to have to use reflection to access the class.
 */
public final class ToStringUtil {
  private final String className;
  private ValueHolder holderHead = new ValueHolder();
  private ValueHolder holderTail = holderHead;
  private boolean omitNullValues = false;


  public static ToStringUtil of(Object self) {
    return new ToStringUtil(self.getClass().getSimpleName());
  }

  public static ToStringUtil of(String className) {
    return new ToStringUtil(className);
  }

  public static ToStringUtil of(Class<?> clazz) {
    return new ToStringUtil(clazz.getSimpleName());
  }

  private ToStringUtil(String className) {
    this.className = checkNotNull(className);
  }

  /**
   * Configures the {@link ToStringUtil} so {@link #toString()} will ignore
   * properties with null value. The order of calling this method, relative
   * to the {@code add()}/{@code addValue()} methods, is not significant.
   *
   * @since 12.0
   */
  public ToStringUtil omitNullValues() {
    omitNullValues = true;
    return this;
  }

  /**
   * Adds a name/value pair to the formatted output in {@code name=value}
   * format. If {@code value} is {@code null}, the string {@code "null"}
   * is used, unless {@link #omitNullValues()} is called, in which case this
   * name/value pair will not be added.
   */
  public ToStringUtil add(String name, @Nullable Object value) {
    return addHolder(name, value);
  }

  /**
   * Adds a name/value pair to the formatted output in {@code name=value}
   * format.
   *
   * @since 11.0 (source-compatible since 2.0)
   */
  public ToStringUtil add(String name, boolean value) {
    return addHolder(name, String.valueOf(value));
  }

  /**
   * Adds a name/value pair to the formatted output in {@code name=value}
   * format.
   *
   * @since 11.0 (source-compatible since 2.0)
   */
  public ToStringUtil add(String name, char value) {
    return addHolder(name, String.valueOf(value));
  }

  /**
   * Adds a name/value pair to the formatted output in {@code name=value}
   * format.
   *
   * @since 11.0 (source-compatible since 2.0)
   */
  public ToStringUtil add(String name, double value) {
    return addHolder(name, String.valueOf(value));
  }

  /**
   * Adds a name/value pair to the formatted output in {@code name=value}
   * format.
   *
   * @since 11.0 (source-compatible since 2.0)
   */
  public ToStringUtil add(String name, float value) {
    return addHolder(name, String.valueOf(value));
  }

  /**
   * Adds a name/value pair to the formatted output in {@code name=value}
   * format.
   *
   * @since 11.0 (source-compatible since 2.0)
   */
  public ToStringUtil add(String name, int value) {
    return addHolder(name, String.valueOf(value));
  }

  /**
   * Adds a name/value pair to the formatted output in {@code name=value}
   * format.
   *
   * @since 11.0 (source-compatible since 2.0)
   */
  public ToStringUtil add(String name, long value) {
    return addHolder(name, String.valueOf(value));
  }

  /**
   * Adds an unnamed value to the formatted output.
   *
   * <p>It is strongly encouraged to use {@link #add(String, Object)} instead
   * and give value a readable name.
   */
  public ToStringUtil addValue(@Nullable Object value) {
    return addHolder(value);
  }

  /**
   * Adds an unnamed value to the formatted output.
   *
   * <p>It is strongly encouraged to use {@link #add(String, boolean)} instead
   * and give value a readable name.
   *
   * @since 11.0 (source-compatible since 2.0)
   */
  public ToStringUtil addValue(boolean value) {
    return addHolder(String.valueOf(value));
  }

  /**
   * Adds an unnamed value to the formatted output.
   *
   * <p>It is strongly encouraged to use {@link #add(String, char)} instead
   * and give value a readable name.
   *
   * @since 11.0 (source-compatible since 2.0)
   */
  public ToStringUtil addValue(char value) {
    return addHolder(String.valueOf(value));
  }

  /**
   * Adds an unnamed value to the formatted output.
   *
   * <p>It is strongly encouraged to use {@link #add(String, double)} instead
   * and give value a readable name.
   *
   * @since 11.0 (source-compatible since 2.0)
   */
  public ToStringUtil addValue(double value) {
    return addHolder(String.valueOf(value));
  }

  /**
   * Adds an unnamed value to the formatted output.
   *
   * <p>It is strongly encouraged to use {@link #add(String, float)} instead
   * and give value a readable name.
   *
   * @since 11.0 (source-compatible since 2.0)
   */
  public ToStringUtil addValue(float value) {
    return addHolder(String.valueOf(value));
  }

  /**
   * Adds an unnamed value to the formatted output.
   *
   * <p>It is strongly encouraged to use {@link #add(String, int)} instead
   * and give value a readable name.
   *
   * @since 11.0 (source-compatible since 2.0)
   */
  public ToStringUtil addValue(int value) {
    return addHolder(String.valueOf(value));
  }

  /**
   * Adds an unnamed value to the formatted output.
   *
   * <p>It is strongly encouraged to use {@link #add(String, long)} instead
   * and give value a readable name.
   *
   * @since 11.0 (source-compatible since 2.0)
   */
  public ToStringUtil addValue(long value) {
    return addHolder(String.valueOf(value));
  }

  /**
   * Returns a string in the format specified by {@link
   *
   * <p>After calling this method, you can keep adding more properties to later
   * call toString() again and get a more complete representation of the
   * same object; but properties cannot be removed, so this only allows
   * limited reuse of the helper instance. The helper allows duplication of
   * properties (multiple name/value pairs with the same name can be added).
   */
  @Override
  public String toString() {
    // create a copy to keep it consistent in case value changes
    boolean omitNullValuesSnapshot = omitNullValues;
    String nextSeparator = "";
    StringBuilder builder = new StringBuilder(32).append(className)
            .append('{');
    for (ValueHolder valueHolder = holderHead.next; valueHolder!=null;
         valueHolder = valueHolder.next) {
      if (!omitNullValuesSnapshot || valueHolder.value!=null) {
        builder.append(nextSeparator);
        nextSeparator = ", ";

        if (valueHolder.name!=null) {
          builder.append(valueHolder.name).append('=');
        }
        builder.append(valueHolder.value);
      }
    }
    return builder.append('}').toString();
  }

  private ValueHolder addHolder() {
    ValueHolder valueHolder = new ValueHolder();
    holderTail = holderTail.next = valueHolder;
    return valueHolder;
  }

  private ToStringUtil addHolder(@Nullable Object value) {
    ValueHolder valueHolder = addHolder();
    valueHolder.value = value;
    return this;
  }

  private ToStringUtil addHolder(String name, @Nullable Object value) {
    ValueHolder valueHolder = addHolder();
    valueHolder.value = value;
    valueHolder.name = checkNotNull(name);
    return this;
  }

  private static final class ValueHolder {
    String name;
    Object value;
    ValueHolder next;
  }
}