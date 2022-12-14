package dev.simplix.cirrus.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.MapMaker;
import dev.simplix.cirrus.menus.AbstractBrowser;
import dev.simplix.cirrus.menus.AbstractSearchableBrowser;
import dev.simplix.cirrus.model.SearchConversation;
import dev.simplix.cirrus.player.CirrusPlayerWrapper;
import dev.simplix.cirrus.service.SearchConversationHandleService;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"UnstableApiUsage", "rawtypes", "unchecked"})
public abstract class AbstractSearchConversationHandlerService implements
    SearchConversationHandleService {

  protected static final Cache<UUID, AbstractSearchableBrowser> cache = CacheBuilder.newBuilder()
      .maximumSize(10000)
      .expireAfterWrite(10, TimeUnit.MINUTES).build();

  protected boolean handleInput(
      CirrusPlayerWrapper playerWrapper,
      String playerMessage,
      AbstractSearchableBrowser browser) {



    browser.redisplay(browser.searchByPartialString(playerMessage));
    return true;
  }


  @Override
  public <T> void handle(
      CirrusPlayerWrapper playerWrapper,
      AbstractSearchableBrowser<T> browser,
      SearchConversation conversation) {



  }
}
