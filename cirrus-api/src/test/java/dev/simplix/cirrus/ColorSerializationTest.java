package dev.simplix.cirrus;

import com.google.gson.Gson;
import java.awt.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(CirrusTestExtension.class)
public class ColorSerializationTest {

  private static final Color TEST_COLOR = new Color(0, 255, 255);

  @Test
  public void testColorSerialization() {
    final Gson gson = Cirrus.gson();
    final String testColorAsJson = gson.toJson(TEST_COLOR);
    final Color testColorSerialized = gson.fromJson(testColorAsJson, Color.class);
    Assertions.assertEquals(
        TEST_COLOR,
        testColorSerialized,
        "Color is not equal to serialized color");
  }

}
