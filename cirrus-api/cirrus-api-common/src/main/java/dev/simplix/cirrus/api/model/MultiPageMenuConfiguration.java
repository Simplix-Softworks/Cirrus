package dev.simplix.cirrus.api.model;

public interface MultiPageMenuConfiguration extends MenuConfiguration {
    ItemStackModel nextPageItem();

    ItemStackModel previousPageItem();
}
