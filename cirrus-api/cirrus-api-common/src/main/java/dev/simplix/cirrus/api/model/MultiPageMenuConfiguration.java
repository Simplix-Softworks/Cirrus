package dev.simplix.cirrus.api.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(fluent = true)
public class MultiPageMenuConfiguration extends MenuConfiguration {

  private ItemStackModel nextPageItem;
  private ItemStackModel previousPageItem;

}
