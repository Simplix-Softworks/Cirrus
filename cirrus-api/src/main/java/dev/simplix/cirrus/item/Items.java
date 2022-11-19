package dev.simplix.cirrus.item;

import dev.simplix.cirrus.color.StandardColorConfiguration;
import dev.simplix.cirrus.effects.SpectrumEffect;
import dev.simplix.cirrus.effects.WaveEffect;
import dev.simplix.cirrus.menu.MenuRow;
import dev.simplix.cirrus.menus.AbstractBrowser;
import dev.simplix.protocolize.data.ItemType;
import java.awt.Color;
import java.util.function.BiConsumer;
import lombok.experimental.UtilityClass;

/**
 * Makes use of {@link dev.simplix.cirrus.color.StandardColorConfiguration} to create Items making
 * use of the defined accent-colors.
 *
 * @see dev.simplix.cirrus.effect
 * @see dev.simplix.cirrus.effects
 * @see dev.simplix.cirrus.color.StandardColorConfiguration
 */
@UtilityClass
public class Items {

  public BiConsumer<AbstractBrowser<?>, MenuRow> defaultBottomRowProvider = (abstractBrowser, bottomRow) -> {


    // Previous page
    final int totalPages = abstractBrowser.totalPages();
    final int previous = abstractBrowser.previousPageNumber();
    final int nextPageNumber = abstractBrowser.nextPageNumber();


    if (abstractBrowser.hasPreviousPage()) {
      bottomRow.get(0).set(CirrusItem.of(
              ItemType.LIME_DYE,
              "§aPrevious page",
              "§7Click to go to the previous page",
              "§7Goto page §8" + previous + " §7of §8" + totalPages
      ).actionHandler(AbstractBrowser.PREVIOUS_PAGE_ACTION_HANDLER));
    } else {
      bottomRow.get(0).set(CirrusItem.of(
              ItemType.GRAY_DYE,
              "§aPrevious page",
              "§7There is no previous page"));
    }

    // Next page
    if (abstractBrowser.hasNextPage()) {
      bottomRow.get(1).set(CirrusItem.of(
              ItemType.LIME_DYE,
              "§aNext page",
              "§7Click to go to the next page",
              "§7Goto page §8" + nextPageNumber + " §7of §8" + totalPages
      ).actionHandler(AbstractBrowser.NEXT_PAGE_ACTION_HANDLER));
    } else {
      bottomRow.get(1).set(CirrusItem.of(
              ItemType.GRAY_DYE,
              "§aNext page",
              "§7There is no next page"));
    }
  };

  public CirrusItem withSpectrumEffect(ItemType itemType, String name, String... lores) {
    SpectrumEffect animation = SpectrumEffect.fat(name,
            StandardColorConfiguration.firstColor,
            StandardColorConfiguration.accentColor);
    return CirrusItem.of(itemType, animation, lores);
  }

  public CirrusItem withWaveEffect(ItemType itemType, String name, String... lores) {
    WaveEffect animation = WaveEffect
            .fat(name, Color.WHITE, StandardColorConfiguration.accentColor);
    return CirrusItem.of(itemType, animation, lores);
  }
}
