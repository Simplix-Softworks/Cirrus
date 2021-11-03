package dev.simplix.cirrus.common.handler;

import dev.simplix.cirrus.common.model.CallResult;
import dev.simplix.cirrus.common.model.Click;

@FunctionalInterface
public interface AutoCancellingActionHandler extends ActionHandler {
    @Override
    default CallResult handle(Click click) {
        onHandle(click);
        return CallResult.DENY_GRABBING;
    }

    void onHandle(Click click);
}
