package dev.simplix.cirrus.actionhandlers;


import dev.simplix.cirrus.actionhandler.ActionHandler;
import dev.simplix.cirrus.menu.Menu;
import dev.simplix.cirrus.model.CallResult;
import dev.simplix.cirrus.model.Click;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OpenMenuActionHandler implements ActionHandler {
  private Menu menuToOpen;

  public static OpenMenuActionHandler of(Menu menuToOpen) {
    return new OpenMenuActionHandler(menuToOpen);
  }

  @Override
  public CallResult handle(Click click) {
    menuToOpen.display(click.player());
    return CallResult.DENY_GRABBING;
  }
}
