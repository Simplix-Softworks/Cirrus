package dev.simplix.cirrus.api.business;

import lombok.NonNull;

import java.util.UUID;

public interface PlayerWrapper {

    void sendMessage(@NonNull String msg);

    void closeInventory();

    boolean hasPermission(@NonNull String permission);

    UUID uniqueId();

    String name();

    int protocolVersion();

    <T> T handle();

}
