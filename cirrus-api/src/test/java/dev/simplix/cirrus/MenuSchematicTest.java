package dev.simplix.cirrus;

import com.google.gson.Gson;
import dev.simplix.cirrus.model.ExampleMenuSchematic;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(CirrusTestExtension.class)
public class MenuSchematicTest {

  @Test
  public void testMenuSerialization() {
    final Gson gson = Cirrus.gson();
    final ExampleMenuSchematic exampleMenuConfiguration = new ExampleMenuSchematic();

    final String toJsonString = gson.toJson(exampleMenuConfiguration);

    final ExampleMenuSchematic deserialized = gson.fromJson(
        toJsonString,
        ExampleMenuSchematic.class);

    Assertions.assertEquals(
        exampleMenuConfiguration,
        deserialized,
        "MenuConfiguration is not equal to serialized MenuConfiguration");
  }
}
