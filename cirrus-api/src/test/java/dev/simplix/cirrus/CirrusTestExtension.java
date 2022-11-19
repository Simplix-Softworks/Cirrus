package dev.simplix.cirrus;

import dev.simplix.protocolize.api.ComponentConverter;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.providers.ComponentConverterProvider;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class CirrusTestExtension implements BeforeAllCallback {

  @Override
  public void beforeAll(ExtensionContext context) throws Exception {
    Cirrus.init();

    Cirrus.registerService(ComponentConverterProvider.class, () -> new ComponentConverter<Object>() {
      @Override
      public String toLegacyText(Object o) {
        return o.toString();
      }

      @Override
      public String toJson(Object o) {
        return o.toString();
      }

      @Override
      public Object fromLegacyText(String s) {
        return s;
      }

      @Override
      public Object fromJson(String s) {
        return s;
      }
    });
  }
}
