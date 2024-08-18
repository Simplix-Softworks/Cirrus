package dev.simplix.cirrus;

import dev.simplix.cirrus.actionhandler.ActionHandler;
import dev.simplix.cirrus.item.CirrusItem;
import dev.simplix.cirrus.menu.MenuRow;
import dev.simplix.cirrus.menus.AbstractBrowser;
import dev.simplix.cirrus.model.CallResult;
import dev.simplix.cirrus.model.Click;
import dev.simplix.protocolize.api.chat.ChatElement;
import dev.simplix.protocolize.api.item.BaseItemStack;
import dev.simplix.protocolize.data.ItemType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.Collection;
import java.util.LinkedList;

@ExtendWith(CirrusTestExtension.class)
public class AbstractBrowserTest {

  private static AbstractBrowser<String> testBrowser;

  @BeforeAll
  public static void testBuilding() {

    testBrowser = new AbstractBrowser<>() {

      @Override
      protected void interceptBottomRow(MenuRow bottomRow) {
        bottomRow.get(0).set(CirrusItem.of(ItemType.ACACIA_BOAT, ChatElement.ofLegacyText("next page")));
      }

      @Override
      protected void handleClick(Click click, String value) {

      }

      @Override
      protected Collection<String> elements() {
        LinkedList<String> list = new LinkedList<>();

        for (int i = 0; i < 200; i++) {
          list.add(Integer.toString(i));
        }

        return list;
      }

      @Override
      protected CirrusItem map(String element) {
        return CirrusItem.of(ItemType.STONE, ChatElement.ofLegacyText(element), ChatElement.ofLegacyText("this is a test"));
      }
    };

  }

  @Test
  public void testBuild() {
//    testBrowser.build()
  }
}