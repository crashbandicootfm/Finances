package dev.crashbandicootfm.service;

import java.util.Scanner;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommandLineBootstrap {

  @NotNull Scanner scanner = new Scanner(System.in);

  @NotNull CommandLineService commandLineService = new CommandLineService();


  public void boostrap() {
    while (true) {
      commandLineService.printCommandLinePrompt();
      String line = scanner.nextLine();
      switch (line.toLowerCase()) {
        case "exit" -> System.exit(0);
        default -> {
          System.out.println("Command not found");
        }
      }
    }
  }

}
