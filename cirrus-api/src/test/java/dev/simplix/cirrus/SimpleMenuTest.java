package dev.simplix.cirrus;


import dev.simplix.cirrus.menu.MenuElement;
import dev.simplix.cirrus.schematic.MenuSchematic;
import dev.simplix.cirrus.schematic.impl.SimpleMenuSchematic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsKt;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(CirrusTestExtension.class)
public class SimpleMenuTest {

  private static MenuSchematic testSchematic;


  @BeforeAll
  public static void testInitialization() {
    testSchematic = new SimpleMenuSchematic();
  }


  @Test
  public void testMenuRow() {
    for (MenuElement menuElement : testSchematic.row(1)) {
      // We don't have any items in this menu
      Assertions.assertTrue(menuElement.item().isEmpty(), "There should not be any item an empty row");
      Assertions.assertTrue(menuElement.actionHandlerString().isEmpty(), "Actionhandlerstring is not present in an empty slot");
    }

  }

}


