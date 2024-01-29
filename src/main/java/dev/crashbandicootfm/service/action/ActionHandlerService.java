package dev.crashbandicootfm.service.action;

import dev.crashbandicootfm.service.profile.Profile;
import java.lang.reflect.Method;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public interface ActionHandlerService {

  @NotNull @Unmodifiable List<String> getHelpMessage();

  void handle(@NotNull String action, @NotNull Profile profile, @NotNull List<String> args);

  void registerHandler(
      @NotNull String action,
      @NotNull String description,
      @NotNull Object handler,
      @NotNull Method method
  );
}
