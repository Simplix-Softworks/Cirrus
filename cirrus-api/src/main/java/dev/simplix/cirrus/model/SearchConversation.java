package dev.simplix.cirrus.model;

import dev.simplix.cirrus.menu.Menu;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Builder
@Data
@AllArgsConstructor
@Accessors(fluent = true)
public class SearchConversation {

  private final String prompt;

  @Nullable
  @Builder.Default
  private String timeoutMessage = null;

  @Builder.Default
  @Nullable
  private Supplier<Menu> menuToOpen = null;

}
