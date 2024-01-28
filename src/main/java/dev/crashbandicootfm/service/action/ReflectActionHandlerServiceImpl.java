package dev.crashbandicootfm.service.action;

import dev.crashbandicootfm.annotation.ActionHandler;
import dev.crashbandicootfm.profile.Profile;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

@Log4j2
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ReflectActionHandlerServiceImpl implements ReflectActionHandlerService {

  @NotNull List<RegisteredHandler> registeredHandlers = new ArrayList<>();

  @Override
  public @NotNull @Unmodifiable List<String> getHelpMessage() {
    return registeredHandlers
        .stream()
        .map(h -> String.format("%s - %s", h.getAction(), h.getDescription()))
        .toList();
  }

  @Override
  @SneakyThrows
  public void handle(@NotNull String action, @NotNull Profile profile, @NotNull List<String> args) {
    RegisteredHandler handler = registeredHandlers
        .stream()
        .filter(h -> h.getAction().equals(action))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("No handler found for action " + action));

    Method handlerMethod = handler.getHandlerMethod(); // находится метод с зарегестрированной коммандой, которая была вписана в консоль

    handlerMethod.invoke(handler.getHandler(), profile, args); // выполняется команда
  }

  @Override
  public void registerHandler(@NotNull String action, @NotNull String description,
                              @NotNull Object handler, @NotNull Method method) {
    RegisteredHandler registeredHandler = new RegisteredHandler(
        action,
        description,
        handler,
        method
    );
    registeredHandlers.add(registeredHandler);
    log.info(
        "Registered action handler for action: {} with method: {}",
        action,
        method.getName()
    );
  }

  @Override
  public void discoverHandlerMethods(@NotNull Object object) {
    Arrays.stream(object.getClass()
            .getDeclaredMethods())
        .filter(method -> method.isAnnotationPresent(ActionHandler.class))
        .forEach(method -> {
          ActionHandler actionHandlerAnnotation = method.getAnnotation(ActionHandler.class);
          method.setAccessible(true);
          registerHandler(actionHandlerAnnotation.value(),
              actionHandlerAnnotation.description(),
              object,
              method
          );
        });
  }

  @Getter
  @RequiredArgsConstructor
  @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
  private static class RegisteredHandler {

    @NotNull String action;

    @NotNull String description;

    @NotNull Object handler;

    @NotNull Method handlerMethod;

  }
}
