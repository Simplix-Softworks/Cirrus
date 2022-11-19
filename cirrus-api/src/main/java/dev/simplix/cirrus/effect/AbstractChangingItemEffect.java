package dev.simplix.cirrus.effect;

import dev.simplix.cirrus.item.CirrusItem;

/**
 * Represents a buttom that changes the item in a given slot 
 */
public abstract class AbstractChangingItemEffect extends AbstractMenuEffect<CirrusItem> {

  protected AbstractChangingItemEffect(CirrusItem input, int effectLength) {
    super(input, effectLength);
  }

}
