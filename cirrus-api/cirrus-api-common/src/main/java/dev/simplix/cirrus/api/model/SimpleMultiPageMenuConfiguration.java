package dev.simplix.cirrus.api.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(fluent = true)
public class SimpleMultiPageMenuConfiguration extends SimpleMenuConfiguration implements MultiPageMenuConfiguration {

    private ItemStackModel nextPageItem;
    private ItemStackModel previousPageItem;

}
