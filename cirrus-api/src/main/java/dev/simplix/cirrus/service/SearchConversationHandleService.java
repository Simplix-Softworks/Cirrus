package dev.simplix.cirrus.service;

import dev.simplix.cirrus.menus.AbstractBrowser;
import dev.simplix.cirrus.menus.AbstractSearchableBrowser;
import dev.simplix.cirrus.model.SearchConversation;
import dev.simplix.cirrus.player.CirrusPlayerWrapper;

public interface SearchConversationHandleService {

  <T> void handle(
      CirrusPlayerWrapper playerWrapper,
      AbstractSearchableBrowser<T> browser,
      SearchConversation conversation);


}
