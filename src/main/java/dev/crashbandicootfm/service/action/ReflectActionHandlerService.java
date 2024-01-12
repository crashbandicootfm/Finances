package dev.crashbandicootfm.service.action;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ReflectActionHandlerService extends ActionHandlerService {

  void discoverHandlerMethods(@NotNull Object object);
}
