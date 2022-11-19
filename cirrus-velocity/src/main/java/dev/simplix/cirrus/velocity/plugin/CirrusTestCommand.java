package dev.simplix.cirrus.velocity.plugin;


import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import dev.simplix.cirrus.menus.example.SelectMenu;
import dev.simplix.cirrus.velocity.wrapper.VelocityPlayerWrapper;

public class CirrusTestCommand implements SimpleCommand {

  @Override
  public void execute(Invocation invocation) {

    if (invocation.source() instanceof Player player) {

      final VelocityPlayerWrapper velocityPlayerWrapper = new VelocityPlayerWrapper(player);
      new SelectMenu().display(velocityPlayerWrapper);
    }
  }
}
