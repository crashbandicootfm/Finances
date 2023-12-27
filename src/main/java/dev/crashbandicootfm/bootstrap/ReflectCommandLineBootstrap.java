package dev.crashbandicootfm.bootstrap;

import dev.crashbandicootfm.annotation.ActionHandler;
import dev.crashbandicootfm.profile.Profile;
import dev.crashbandicootfm.service.CommandLineService;
import dev.crashbandicootfm.service.ProfileService;
import dev.crashbandicootfm.service.TransactionService;
import dev.crashbandicootfm.service.action.ReflectActionHandlerService;
import dev.crashbandicootfm.service.action.ReflectActionHandlerServiceImpl;

import java.util.Objects;
import java.util.Scanner;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;


@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
public final class ReflectCommandLineBootstrap implements CommandLineBootstrap {

  @NotNull
  final ReflectActionHandlerService actionHandler = new ReflectActionHandlerServiceImpl();

  @NotNull
  final Scanner scanner = new Scanner(System.in);

  @NotNull
  final CommandLineService commandLineService = new CommandLineService();

  @NotNull
  final ProfileService profileService = new ProfileService();

  final TransactionService service = new TransactionService();


  Profile profile;

  @ActionHandler(
          value = "exit",
          description = "Shutdowns application"
  )
  private void exit() {
    System.exit(0);
  }

  @ActionHandler(
          value = "balance",
          description = "Shows your current balance"
  )
  private void balance() {
    System.out.println("Your balance: " + profile.getBalanceFormatted());
  }

  @ActionHandler(
          value = "send",
          description = "Send money"
  )
  private void send() {
    System.out.print("Enter user name: ");
    String recipientName = scanner.nextLine();
    Profile recipient = profileService.getProfile(recipientName);
    if (recipient == null)
      System.out.println("No such users");
    else if(recipient == profile)
      System.out.println("Incorrect");
    else {
      System.out.print("Enter the amount: ");
      float amount = scanner.nextFloat();
      scanner.nextLine();
      System.out.println("Sending money...");
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      service.sendMoney(amount, profile, recipient);
    }
  }

  @ActionHandler(
          value = "help",
          description = "Commands you can use"
  )
  private void help() {
    System.out.println("\"balance\" - shows your current balance");
    System.out.println("\"send\" - send money");
    System.out.println("\"put\" - put money on your account");
    System.out.println("\"withdraw\" - take money from your account");
    System.out.println("\"uuid\" - shows your uuid");
  }

  @ActionHandler(
          value = "put",
          description = "Put money on your account"
  )
  private void put() {
    System.out.print("Enter the amount: ");
    float sum = scanner.nextFloat();
    scanner.nextLine();
    profile.deposit(sum);
  }

  @ActionHandler(
          value = "withdraw",
          description = "Take money from your account"
  )
  private void withdraw() {
    System.out.print("Enter the amount: ");
    float amount = scanner.nextFloat();
    scanner.nextLine();
    service.withdraw(amount, profile);
  }

  @ActionHandler(
          value = "my uuid",
          description = "Shows your uuid"
  )
  private void uuid() {
    System.out.println(profile.getUuid());
  }

  @Override
  @SuppressWarnings("InfiniteLoopStatement")
  public void bootstrap() {
    profileService.addProfile(new Profile("Andrej", "Ivanov", 862.8F));
    profileService.addProfile(new Profile("Dima", "Ivanov", 762.0F));
    System.out.print("Enter your name: ");
    actionHandler.discoverHandlerMethods(this);
    String name = scanner.nextLine();
    profile = Objects.requireNonNull(profileService.getProfile(name));

    while (true) {
      commandLineService.printCommandLinePrompt();
      String action = scanner.nextLine();
        switch (action) {
            case "balance" -> balance();
            case "send" -> send();
            case "help" -> help();
            case "put" -> put();
            case "withdraw" -> withdraw();
            case "uuid" -> uuid();
            default -> actionHandler.handle(action);
        }
    }
  }
}
