package dev.simplix.cirrus.effect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import java.util.Iterator;

/**
 * Menus should have effects/animation. For instance a RGB-effect for the displayname of an
 * {@link dev.simplix.cirrus.item.CirrusItem}
 */
public abstract class AbstractMenuEffect<T> implements MenuEffect<T> {

  protected final T input;
  protected final int effectLength;

  private transient Iterator<T> iterator;

  protected AbstractMenuEffect(
      T input,
      int effectLength) {
    this.input = Preconditions.checkNotNull(input, "input must not be null");
    Preconditions.checkState(effectLength > 0, "effectLength is not larger than 0");
    this.effectLength = effectLength;
  }

  @Override
  public int effectLength() {
    return this.effectLength;
  }

  @Override
  public final T input() {
    return this.input;
  }

  @Override
  public final T nextFrame() {
    if (this.iterator == null) {
      this.iterator = Iterators.cycle(calculate());
    }

    return this.iterator.next();
  }
}
