package dev.simplix.cirrus.effects;

import dev.simplix.cirrus.effect.AbstractChangingItemEffect;
import dev.simplix.cirrus.item.CirrusItem;
import java.util.List;

public class SimpleChangingItemEffect extends AbstractChangingItemEffect {

  private final List<CirrusItem> values;

  protected SimpleChangingItemEffect(CirrusItem basis, List<CirrusItem> values, int effectLength) {
    super(basis, effectLength);
    this.values = values;
  }

  @Override
  public List<CirrusItem> calculate() {
    return this.values;
  }
}
