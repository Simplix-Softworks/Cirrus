package dev.simplix.cirrus;

import com.google.gson.Gson;
import dev.simplix.cirrus.item.CirrusItem;
import dev.simplix.cirrus.item.Items;
import dev.simplix.protocolize.data.ItemType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(CirrusTestExtension.class)
public class ItemSerializationTest {

  @Test
  public void testSerialization() {

    final Gson gson = Cirrus.gson();
    // Gradual change
    var item = Items.withWaveEffect(ItemType.ITEM_FRAME, "test", "test123");
    var toJson = gson.toJson(item);
    final CirrusItem serializedItem = gson.fromJson(toJson, CirrusItem.class);
    Assertions.assertEquals(item, serializedItem, "Item is not equal to serialized item");
  }

  @Test
  public void testSerialization2() {

    final Gson gson = Cirrus.gson();
    // RGB change
    var item = Items.withSpectrumEffect(ItemType.ITEM_FRAME, "test", "test123");
    var toJson = gson.toJson(item);
    final CirrusItem serializedItem = gson.fromJson(toJson, CirrusItem.class);
    Assertions.assertEquals(item, serializedItem, "Item is not equal to serialized item");
  }

}