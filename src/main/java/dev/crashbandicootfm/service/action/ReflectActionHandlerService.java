package dev.crashbandicootfm.service.action;

import org.jetbrains.annotations.NotNull;

public interface ReflectActionHandlerService extends ActionHandlerService {

  void discoverHandlerMethods(@NotNull Object object);
}
