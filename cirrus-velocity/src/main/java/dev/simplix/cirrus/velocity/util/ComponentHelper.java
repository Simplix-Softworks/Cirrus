package dev.simplix.cirrus.velocity.util;


import dev.simplix.protocolize.api.item.BaseItemStack;
import java.util.List;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.format.TextDecoration.State;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

@UtilityClass
public class ComponentHelper {
  private static final String STEVE_TEXTURE = "ewogICJ0aW1lc3RhbXAiIDogMTU5MTU3NDcyMzc4MywKICAicHJvZmlsZUlkIiA6ICI4NjY3YmE3MWI4NWE0MDA0YWY1NDQ1N2E5NzM0ZWVkNyIsCiAgInByb2ZpbGVOYW1lIiA6ICJTdGV2ZSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82ZDNiMDZjMzg1MDRmZmMwMjI5Yjk0OTIxNDdjNjlmY2Y1OWZkMmVkNzg4NWY3ODUwMjE1MmY3N2I0ZDUwZGUxIgogICAgfSwKICAgICJDQVBFIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS85NTNjYWM4Yjc3OWZlNDEzODNlNjc1ZWUyYjg2MDcxYTcxNjU4ZjIxODBmNTZmYmNlOGFhMzE1ZWE3MGUyZWQ2IgogICAgfQogIH0KfQ==";


  public Component removeItalic(Component component) {
    if (!component.hasDecoration(TextDecoration.ITALIC)) {
      return component.decoration(TextDecoration.ITALIC, State.FALSE);
    }

    return component;
  }

  public Component removeItalic(String title) {
    final Component deserialize = GsonComponentSerializer.gson().deserialize(title);
    return removeItalic(deserialize);
  }

  public void fixItalic(BaseItemStack itemStack) {
    final List<Component> loreRaw = itemStack.lore(false);
    final List<Component> fixedLore = loreRaw
            .stream()
            .map(ComponentHelper::removeItalic)
            .toList();
    itemStack.lore(fixedLore, false);
    Component displayName = itemStack.displayName(false);
    itemStack.displayName(removeItalic(displayName));
  }
}