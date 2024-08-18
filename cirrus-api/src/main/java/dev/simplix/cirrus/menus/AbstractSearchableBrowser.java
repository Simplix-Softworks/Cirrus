package dev.simplix.cirrus.menus;

import dev.simplix.cirrus.Cirrus;
import dev.simplix.cirrus.item.CirrusItem;
import dev.simplix.cirrus.menu.MenuRow;
import dev.simplix.cirrus.model.SearchConversation;
import dev.simplix.cirrus.service.SearchConversationHandleService;
import dev.simplix.protocolize.api.chat.ChatElement;
import dev.simplix.protocolize.api.item.BaseItemStack;
import dev.simplix.protocolize.data.ItemType;
import java.util.Collection;

public abstract class AbstractSearchableBrowser<T> extends AbstractBrowser<T> {

  private CirrusItem item = CirrusItem
      .of(ItemType.STONE);

  protected AbstractSearchableBrowser() {
    registerActionHandler("Search", (
        apply -> {
          Cirrus
              .service(SearchConversationHandleService.class)
              .handle(apply.player(), this, searchConversation());
        }));
  }

  // ----------------------------------------------------------------------------------------------------
  // Implementation
  // ----------------------------------------------------------------------------------------------------

  @Override
  protected final void interceptBottomRow(MenuRow bottomRow) {
    super.interceptBottomRow(bottomRow);
    interceptBottomRow0(bottomRow);
    bottomRow.get(8).set(searchItemStack());
  }

  // ----------------------------------------------------------------------------------------------------
  // Abstract methods
  // ----------------------------------------------------------------------------------------------------

  public abstract void redisplay(Collection<T> content);

  public abstract Collection<T> searchByPartialString(String partial);

  protected abstract SearchConversation searchConversation();

  // ----------------------------------------------------------------------------------------------------
  // Methods that might be overridden
  // ----------------------------------------------------------------------------------------------------

  protected void interceptBottomRow0(MenuRow bottomRow) {
  }

  protected BaseItemStack searchItemStack() {
    return CirrusItem
        .of(ItemType.COMPASS)
        .displayName(ChatElement.ofLegacyText("&6Search"))
        .lore(compassLore())
        .actionHandler("Search");
  }

  /**
   * Defines the lore our compass should have
   */
  protected ChatElement<?>[] compassLore() {
    return new ChatElement[]{
        ChatElement.ofLegacyText("&7Search for a value"),
    };
  }

}
