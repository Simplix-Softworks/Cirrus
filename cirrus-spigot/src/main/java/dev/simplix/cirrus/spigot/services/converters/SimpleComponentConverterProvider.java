package dev.simplix.cirrus.spigot.services.converters;

import dev.simplix.protocolize.api.ComponentConverter;
import dev.simplix.protocolize.api.providers.ComponentConverterProvider;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

/**
 * Date: 24.08.2021
 *
 * @author Exceptionflug
 */
public final class SimpleComponentConverterProvider implements ComponentConverterProvider {

  @Override
  public ComponentConverter<?> platformConverter() {
    return SimpleComponentConverter.INSTANCE;
  }

  public static class SimpleComponentConverter implements ComponentConverter<BaseComponent[]> {

    static SimpleComponentConverter INSTANCE = new SimpleComponentConverter();

    private SimpleComponentConverter() {
    }

    @Override
    public String toLegacyText(BaseComponent[] components) {
      return new TextComponent(components).toLegacyText();
    }

    @Override
    public String toJson(BaseComponent[] components) {
      return ComponentSerializer.toString(components);
    }

    @Override
    public BaseComponent[] fromLegacyText(String legacyText) {
      return TextComponent.fromLegacyText("§r" + legacyText);
    }

    @Override
    public BaseComponent[] fromJson(String json) {
      return ComponentSerializer.parse(json);
    }

  }

}
