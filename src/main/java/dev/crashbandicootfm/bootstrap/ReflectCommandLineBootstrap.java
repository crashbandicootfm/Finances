package dev.crashbandicootfm.bootstrap;

import dev.crashbandicootfm.annotation.ActionHandler;
import dev.crashbandicootfm.service.CommandLineService;
import dev.crashbandicootfm.service.ProfileService;
import dev.crashbandicootfm.service.action.ReflectActionHandlerService;
import dev.crashbandicootfm.service.action.ReflectActionHandlerServiceImpl;
import java.util.Scanner;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;


@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public final class ReflectCommandLineBootstrap implements CommandLineBootstrap {

  @NotNull ReflectActionHandlerService actionHandler = new ReflectActionHandlerServiceImpl();

  @NotNull Scanner scanner = new Scanner(System.in);

  @NotNull CommandLineService commandLineService = new CommandLineService();

  @NotNull ProfileService profileService = new ProfileService();

  @ActionHandler(
      value = "exit",
      description = "Shutdowns application"
  )
  private void exit() {
    System.exit(0);
  }

  @Override
  @SuppressWarnings("InfiniteLoopStatement")
  public void bootstrap() {
    actionHandler.discoverHandlerMethods(this);
    while (true) {
      commandLineService.printCommandLinePrompt();
      String action = scanner.nextLine();
      actionHandler.handle(action);
    }
  }
}
