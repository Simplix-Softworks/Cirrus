package dev.simplix.cirrus;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.simplix.cirrus.mojang.TextureService;
import dev.simplix.cirrus.mojang.UUIDNameService;
import dev.simplix.cirrus.service.ColorConvertService;
import dev.simplix.protocolize.api.Protocolize;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(CirrusTestExtension.class)
public class InitializationTest {

  @Test
  public void testInitialization() {
    Cirrus.init();
    assertNotNull(Cirrus.service(ColorConvertService.class));
    assertNotNull(Cirrus.service(TextureService.class));
    assertNotNull(Cirrus.service(UUIDNameService.class));
  }
}
