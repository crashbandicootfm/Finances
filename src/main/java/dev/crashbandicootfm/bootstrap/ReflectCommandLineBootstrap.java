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


  @ActionHandler(
      value = "exit",
      description = "Shutdowns application"
  )
  private void exit(@NotNull Profile profile) {
    System.exit(0);
  }

  @ActionHandler(
      value = "balance",
      description = "Shows your current balance"
  )
  private void balance(@NotNull Profile profile) {
    System.out.println("Your balance: " + profile.getBalanceFormatted());
  }

  @ActionHandler(
      value = "send",
      description = "Send money"
  )
  private void send(@NotNull Profile profile) {
    System.out.print("Enter user name: ");
    String recipientName = scanner.nextLine();
    Profile recipient = profileService.getProfile(recipientName);
    if (recipient == null) {
      System.out.println("No such users");
    } else if (recipient == profile) {
      System.out.println("Incorrect");
    } else {
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
  private void help(@NotNull Profile profile) {
    actionHandler.getHelpMessage()
        .forEach(System.out::println);
  }

  @ActionHandler(
      value = "put",
      description = "Put money on your account"
  )
  private void put(@NotNull Profile profile, String @NotNull... args) {
    int amount = Integer.parseInt(args[0]);
    scanner.nextLine();
    profile.deposit(amount);
  }

  @ActionHandler(
      value = "withdraw",
      description = "Take money from your account"
  )
  private void withdraw(@NotNull Profile profile) {
    System.out.print("Enter the amount: ");
    float amount = scanner.nextFloat();
    scanner.nextLine();
    service.withdraw(amount, profile);
  }

  @ActionHandler(
      value = "uuid",
      description = "Shows your uuid"
  )
  private void uuid(@NotNull Profile profile) {
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
    Profile profile = profileService.getProfile(name);
    while (true) {
      commandLineService.printCommandLinePrompt();
      String action = scanner.nextLine();
      actionHandler.handle(action, Objects.requireNonNull(profile));
    }
  }
}
