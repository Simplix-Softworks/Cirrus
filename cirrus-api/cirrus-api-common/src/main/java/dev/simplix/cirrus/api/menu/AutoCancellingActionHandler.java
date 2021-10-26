package dev.simplix.cirrus.api.menu;

@FunctionalInterface
public interface AutoCancellingActionHandler extends ActionHandler {
    @Override
    default CallResult handle(Click click) {
        onHandle(click);
        return CallResult.DENY_GRABBING;
    }

    void onHandle(Click click);
}
