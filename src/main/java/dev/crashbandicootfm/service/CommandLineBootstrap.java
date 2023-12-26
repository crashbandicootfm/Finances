package dev.crashbandicootfm.service;

import static java.lang.System.exit;

import dev.crashbandicootfm.profile.Profile;
import java.util.Scanner;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

@Log4j2
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommandLineBootstrap {

  @NotNull Scanner scanner = new Scanner(System.in);

  @NotNull CommandLineService commandLineService = new CommandLineService();

  @NotNull ProfileService profileService = new ProfileService();

  @SneakyThrows(InterruptedException.class)
  public void boostrap() {
    profileService.addProfile(new Profile("Andrej", "Ivanov", 862.8F));
    profileService.addProfile(new Profile("Dima", "Ivanov", 762.0F));

    System.out.println("Enter your name: ");
    String name = scanner.nextLine();
    Profile profile = profileService.getProfile(name);

    if (profile == null) {
      System.out.println("No such users");
      exit(0);
    }

    TransactionService service = new TransactionService();
    while (true) {
      commandLineService.printCommandLinePrompt();
      String line = scanner.nextLine();
      switch (line.toLowerCase()) {
        case "exit" -> exit(0);
        case "balance" -> {
          System.out.println("Your balance is: " + profile.getBalanceFormatted());
        }
        case "send" -> {
          System.out.println("Enter user name");
          String recipientName = scanner.nextLine();
          Profile recipient = profileService.getProfile(recipientName);
          if (recipient == null) {
            System.out.println("No such users");
          } else if (recipient == profile) {
            System.out.println("Incorrect");
          } else {
            System.out.println("Enter the amount");
            float amount = scanner.nextFloat();
            scanner.nextLine();
            System.out.println("Sending money...");
            Thread.sleep(3000);
            service.sendMoney(amount, profile, recipient);
          }
        }
        case "put" -> {
          System.out.println("Enter the amount: ");
          float sum = scanner.nextFloat();
          scanner.nextLine();
          profile.deposit(sum);
        }
        case "withdraw" -> {
          System.out.println("Enter the amount: ");
          float amount = scanner.nextFloat();
          scanner.nextLine();
          service.withdraw(amount, profile);
        }
        case "my uuid" -> System.out.println(profile.getUuid());
        default -> System.out.println("Command not found");
      }
    }
  }

}
