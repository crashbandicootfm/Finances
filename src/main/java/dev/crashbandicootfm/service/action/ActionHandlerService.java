package dev.crashbandicootfm.service.action;

import java.lang.reflect.Method;
import org.jetbrains.annotations.NotNull;

public interface ActionHandlerService {

  void handle(@NotNull String action);

  void registerHandler(
      @NotNull String action,
      @NotNull String description,
      @NotNull Object handler,
      @NotNull Method method
  );
}
