package dev.simplix.cirrus.actionhandlers;

import dev.simplix.cirrus.Cirrus;
import dev.simplix.cirrus.actionhandler.ActionHandler;
import dev.simplix.cirrus.menu.Menu;
import dev.simplix.cirrus.model.CallResult;
import dev.simplix.cirrus.model.Click;
import dev.simplix.cirrus.service.RunSyncService;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AsyncOpenMenuActionHandler implements ActionHandler {

  private Function<Click, Menu> menuFunction;

  public static AsyncOpenMenuActionHandler of(Function<Click, Menu> menuToOpen) {
    return new AsyncOpenMenuActionHandler(menuToOpen);
  }

  @Override
  public CallResult handle(Click click) {

    try {
      Cirrus.executor().execute(() -> {
        try {
          final Menu apply = this.menuFunction.apply(click);

          if (Cirrus.canDisplayAsync()) {
            apply.display(click.player());
          } else {
            Cirrus.service(RunSyncService.class).runSync(() -> {
              apply.display(click.player());
            });
          }
        } catch (Exception exception) {
          throw new RuntimeException("Exception displaying menu", exception);
        }
      });
    } catch (Exception exception) {
      throw new RuntimeException("Exception executing task", exception);
    }

    return CallResult.DENY_GRABBING;
  }
}
