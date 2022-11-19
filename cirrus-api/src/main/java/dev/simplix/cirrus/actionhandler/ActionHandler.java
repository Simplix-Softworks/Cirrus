package dev.simplix.cirrus.actionhandler;

import dev.simplix.cirrus.model.CallResult;
import dev.simplix.cirrus.model.Click;

public interface ActionHandler {

  CallResult handle(Click click);
}
