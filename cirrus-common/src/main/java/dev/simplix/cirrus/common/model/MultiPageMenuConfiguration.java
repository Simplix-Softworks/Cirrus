package dev.simplix.cirrus.common.model;

public interface MultiPageMenuConfiguration extends MenuConfiguration {
    ItemStackModel nextPageItem();

    ItemStackModel previousPageItem();
}
