package dev.simplix.cirrus.menus;

import dev.simplix.cirrus.Cirrus;
import dev.simplix.cirrus.actionhandler.ActionHandler;
import dev.simplix.cirrus.actionhandler.RegisteredActionHandler;
import dev.simplix.cirrus.menu.AbstractMenu;
import dev.simplix.cirrus.menu.DisplayedMenu;
import dev.simplix.cirrus.model.CallResult;
import dev.simplix.cirrus.model.Click;
import dev.simplix.cirrus.player.CirrusPlayerWrapper;
import dev.simplix.cirrus.schematic.MenuSchematic;
import dev.simplix.cirrus.service.MenuBuildService;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

@NoArgsConstructor
public class SimpleMenu extends AbstractMenu {

  public SimpleMenu(MenuSchematic schematic) {
    super(schematic);
  }

  @Override
  public void loadFrom(MenuSchematic menuSchematic) {

  }

  public void registerActionHandler(String actionHandlerString, Consumer<Click> clickConsumer) {
    this.actionHandlers.add(new RegisteredActionHandler(actionHandlerString, click -> {
      clickConsumer.accept(click);
      return CallResult.DENY_GRABBING;
    }));
  }

  public void registerActionHandler(String actionHandlerString, ActionHandler actionHandler) {
    this.actionHandlers.add(new RegisteredActionHandler(actionHandlerString, actionHandler));
  }

  @Override
  public DisplayedMenu display(CirrusPlayerWrapper player) {
    return Cirrus.service(MenuBuildService.class).buildAndOpenMenu(this, player);
  }

  @Override
  public void handleClose() {

  }

  @Override
  public SimpleMenu copy() {
//    return new SimpleMenu(this.schematic.copy(), new LinkedList<>(this.actionHandlers));
    throw new AbstractMethodError("Not implemented yet");
  }
}
